package com.kirekov.achievement.tracker.api.telegram.response;

import com.kirekov.achievement.tracker.testutil.TestBuilder;
import java.util.function.Consumer;
import org.telegram.telegrambots.meta.api.objects.Chat;

public class ChatTestBuilder implements TestBuilder<Chat> {
  private long id = 1;

  private ChatTestBuilder() {
  }

  private ChatTestBuilder(ChatTestBuilder builder) {
    this.id = builder.id;
  }

  public static ChatTestBuilder aChat() {
    return new ChatTestBuilder();
  }

  public ChatTestBuilder withId(long id) {
    return copyWith(b -> b.id = id);
  }

  private ChatTestBuilder copyWith(Consumer<ChatTestBuilder> consumer) {
    final var copy = new ChatTestBuilder(this);
    consumer.accept(copy);
    return copy;
  }

  @Override
  public Chat build() {
    final var chat = new Chat();
    chat.setId(id);
    return chat;
  }
}
