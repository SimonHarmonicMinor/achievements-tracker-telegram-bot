package com.kirekov.achievement.tracker.testutil;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

import com.kirekov.achievement.tracker.api.telegram.Bot;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockserver.client.MockServerClient;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestPropertySource(properties = {
    "bot.token=token",
    "bot.username=username"
})
@Testcontainers
public class SystemTestSuite extends IntegrationTestSuite {

  @Container
  private static final MockServerContainer mockServer = new MockServerContainer(
      DockerImageName.parse("jamesdbloom/mockserver:mockserver-5.11.2")
  );

  protected static MockServerClient mockClient() {
    return new MockServerClient(mockServer.getHost(), mockServer.getServerPort());
  }

  protected static void registerUpdate(Update update) {
    mockClient()
        .when(request()
            .withPath("/getupdates"))
        .respond(response()
            .withBody(json(List.of(update))));
  }

  @TestConfiguration
  static class BotTestConfiguration {

    @Bean
    public Bot bot() {
      final var logConsumer =
          new Slf4jLogConsumer(LoggerFactory.getLogger("Bot")).withSeparateOutputStreams();
      mockServer.followOutput(logConsumer);

      mockClient()
          .when(
              request().withPath("/deleteWebhook")
          )
          .respond(
              response().withBody(json("{\"ok\": \"true\", \"result\": \"true\"}"))
          );
      final var endpoint = mockServer.getEndpoint();
      final var bot = new Bot() {

        @Override
        public String getBaseUrl() {
          return endpoint + "/";
        }

        @Override
        public String getBotToken() {
          return endpoint.substring(1);
        }
      };

      try {
        ReflectionUtils.readFieldValues()
        Field field = bot.getClass().getDeclaredField("options");
        field.setAccessible(true);
        DefaultBotOptions options = (DefaultBotOptions) field.get(bot);
        options.setBaseUrl(endpoint.substring(0, 1));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      return bot;
    }
  }
}
