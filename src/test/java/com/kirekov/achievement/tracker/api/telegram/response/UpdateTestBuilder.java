package com.kirekov.achievement.tracker.api.telegram.response;

import com.kirekov.achievement.tracker.testutil.TestBuilder;
import java.util.function.Consumer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

public class UpdateTestBuilder implements TestBuilder<Update> {

  private int updateId = 1;
  private TestBuilder<? extends Message> message = () -> null;
  private TestBuilder<? extends InlineQuery> inlineQuery = () -> null;
  private TestBuilder<? extends ChosenInlineQuery> chosenInlineQuery = () -> null;
  private TestBuilder<? extends CallbackQuery> callbackQuery = () -> null;
  private TestBuilder<? extends Message> editedMessage = () -> null;
  private TestBuilder<? extends Message> editedChannelPost = () -> null;
  private TestBuilder<? extends ShippingQuery> shippingQuery = () -> null;
  private TestBuilder<? extends PreCheckoutQuery> preCheckoutQuery = () -> null;
  private TestBuilder<? extends Poll> poll = () -> null;

  private UpdateTestBuilder() {
  }

  private UpdateTestBuilder(UpdateTestBuilder builder) {
    this.updateId = builder.updateId;
    this.message = builder.message;
    this.inlineQuery = builder.inlineQuery;
    this.chosenInlineQuery = builder.chosenInlineQuery;
    this.callbackQuery = builder.callbackQuery;
    this.editedMessage = builder.editedMessage;
    this.editedChannelPost = builder.editedChannelPost;
    this.shippingQuery = builder.shippingQuery;
    this.preCheckoutQuery = builder.preCheckoutQuery;
    this.poll = builder.poll;
  }

  public static UpdateTestBuilder anUpdate() {
    return new UpdateTestBuilder();
  }

  public UpdateTestBuilder withUpdateId(int updateId) {
    return copyWith(b -> b.updateId = updateId);
  }

  public UpdateTestBuilder withMessage(TestBuilder<? extends Message> message) {
    return copyWith(b -> b.message = message);
  }

  public UpdateTestBuilder withInlineQuery(TestBuilder<? extends InlineQuery> inlineQuery) {
    return copyWith(b -> b.inlineQuery = inlineQuery);
  }

  public UpdateTestBuilder withChosenInlineQuery(
      TestBuilder<? extends ChosenInlineQuery> chosenInlineQuery) {
    return copyWith(b -> b.chosenInlineQuery = chosenInlineQuery);
  }

  public UpdateTestBuilder withCallbackQuery(TestBuilder<? extends CallbackQuery> callbackQuery) {
    return copyWith(b -> b.callbackQuery = callbackQuery);
  }

  public UpdateTestBuilder withEditedMessage(TestBuilder<? extends Message> editedMessage) {
    return copyWith(b -> b.editedMessage = editedMessage);
  }

  public UpdateTestBuilder withEditedChannelPost(TestBuilder<? extends Message> editedChannelPost) {
    return copyWith(b -> b.editedChannelPost = editedChannelPost);
  }

  public UpdateTestBuilder withShippingQuery(TestBuilder<? extends ShippingQuery> shippingQuery) {
    return copyWith(b -> b.shippingQuery = shippingQuery);
  }

  public UpdateTestBuilder withPreCheckoutQuery(
      TestBuilder<? extends PreCheckoutQuery> preCheckoutQuery) {
    return copyWith(b -> b.preCheckoutQuery = preCheckoutQuery);
  }

  public UpdateTestBuilder withPoll(TestBuilder<? extends Poll> poll) {
    return copyWith(b -> b.poll = poll);
  }

  private UpdateTestBuilder copyWith(Consumer<UpdateTestBuilder> consumer) {
    final var copy = new UpdateTestBuilder(this);
    consumer.accept(copy);
    return copy;
  }

  @Override
  public Update build() {
    final var update = new Update();
    update.setUpdateId(updateId);
    update.setMessage(message.build());
    update.setInlineQuery(inlineQuery.build());
    update.setChosenInlineQuery(chosenInlineQuery.build());
    update.setCallbackQuery(callbackQuery.build());
    update.setEditedMessage(editedMessage.build());
    update.setEditedChannelPost(editedChannelPost.build());
    update.setShippingQuery(shippingQuery.build());
    update.setPreCheckoutQuery(preCheckoutQuery.build());
    update.setPoll(poll.build());
    return update;
  }
}
