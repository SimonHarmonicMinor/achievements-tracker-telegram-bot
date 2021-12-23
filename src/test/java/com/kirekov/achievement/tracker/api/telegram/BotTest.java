package com.kirekov.achievement.tracker.api.telegram;

import static com.kirekov.achievement.tracker.api.telegram.response.ChatTestBuilder.aChat;
import static com.kirekov.achievement.tracker.api.telegram.response.MessageTestBuilder.aMessage;
import static com.kirekov.achievement.tracker.api.telegram.response.UpdateTestBuilder.anUpdate;
import static java.lang.String.format;
import static org.mockserver.matchers.MatchType.STRICT;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.verify.VerificationTimes.atLeast;
import static org.mockserver.verify.VerificationTimes.atMost;

import com.kirekov.achievement.tracker.testutil.SystemTest;
import com.kirekov.achievement.tracker.testutil.SystemTestSuite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SystemTest
@DisplayName("Bot: system test suites")
public class BotTest extends SystemTestSuite {

  @Test
  @DisplayName("Should return the same message that has been sent")
  void shouldReturnTheSameMessageThatHasBeenSent() {
    final var chatId = 28L;
    final var messageText = "some_text";

    registerUpdate(
        anUpdate()
            .withMessage(
                aMessage()
                    .withText(messageText + "fdsfsd")
                    .withChat(
                        aChat().withId(chatId)
                    )
            )
            .build()
    );

    mockClient().verify(
        request().withPath("/getupdates"),
        atLeast(1)
    );
    mockClient().verify(
        request()
            .withPath("/sendmessage")
            .withMethod("POST")
            .withBody(
                json(
                    format(
                        "{\"chatId\": \"%s\", \"text\": \"%s\"}",
                        chatId,
                        messageText
                    ),
                    STRICT
                )
            ),
        atMost(1)
    );
  }
}