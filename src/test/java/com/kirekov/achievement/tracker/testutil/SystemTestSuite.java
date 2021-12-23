package com.kirekov.achievement.tracker.testutil;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

import com.kirekov.achievement.tracker.api.telegram.Bot;
import java.util.List;
import java.util.Map;
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
            .withBody(json(
                Map.of(
                    "ok", "true",
                    "result", List.of(update)
                )
            )));
  }

  @TestConfiguration
  static class BotTestConfiguration {

    @Bean
    public DefaultBotOptions defaultBotOptions() {
      final var options = new DefaultBotOptions();
      options.setBaseUrl(mockServer.getEndpoint().substring(0, 1));
      return options;
    }

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

      return new Bot(defaultBotOptions()) {
        @Override
        public String getBotToken() {
          return mockServer.getEndpoint().substring(1);
        }
      };
    }
  }
}
