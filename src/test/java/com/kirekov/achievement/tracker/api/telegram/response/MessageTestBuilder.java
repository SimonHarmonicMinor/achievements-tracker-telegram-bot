package com.kirekov.achievement.tracker.api.telegram.response;

import com.kirekov.achievement.tracker.testutil.TestBuilder;
import java.util.function.Consumer;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageTestBuilder implements TestBuilder<Message> {

  private TestBuilder<? extends Chat> chat = () -> null;
  private String text = "";

  private MessageTestBuilder() {
  }

  private MessageTestBuilder(MessageTestBuilder builder) {
    this.chat = builder.chat;
    this.text = builder.text;
  }

  public static MessageTestBuilder aMessage() {
    return new MessageTestBuilder();
  }

  public MessageTestBuilder withChat(TestBuilder<? extends Chat> chat) {
    return copyWith(b -> b.chat = chat);
  }

  public MessageTestBuilder withText(String text) {
    return copyWith(b -> b.text = text);
  }

  private MessageTestBuilder copyWith(Consumer<MessageTestBuilder> consumer) {
    final var copy = new MessageTestBuilder(this);
    consumer.accept(copy);
    return copy;
  }

  @Override
  public Message build() {
    final var message = new Message();
    message.setText(text);
    message.setChat(chat.build());
    return message;
  }
}
