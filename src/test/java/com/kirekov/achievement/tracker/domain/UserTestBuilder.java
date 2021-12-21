package com.kirekov.achievement.tracker.domain;

import com.kirekov.achievement.tracker.test_util.TestBuilder;
import java.time.OffsetDateTime;
import java.util.function.Consumer;

public class UserTestBuilder implements TestBuilder<User> {

  private long uniqueIdentifier = 1;
  private boolean isBot = false;
  private OffsetDateTime dateRegistered = OffsetDateTime.now();
  private String firstName = "";
  private String lastName = "";
  private String username = "";

  public static UserTestBuilder aUser() {
    return new UserTestBuilder();
  }

  private UserTestBuilder() {
  }

  private UserTestBuilder(UserTestBuilder builder) {
    this.uniqueIdentifier = builder.uniqueIdentifier;
    this.isBot = builder.isBot;
    this.dateRegistered = builder.dateRegistered;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.username = builder.username;
  }

  public UserTestBuilder withUniqueIdentifier(long uniqueIdentifier) {
    return copyWith(b -> b.uniqueIdentifier = uniqueIdentifier);
  }

  public UserTestBuilder withBot(boolean isBot) {
    return copyWith(b -> b.isBot = isBot);
  }

  public UserTestBuilder withDateRegistered(OffsetDateTime dateRegistered) {
    return copyWith(b -> b.dateRegistered = dateRegistered);
  }

  public UserTestBuilder withFirstName(String firstName) {
    return copyWith(b -> b.firstName = firstName);
  }

  public UserTestBuilder withUsername(String username) {
    return copyWith(b -> b.username = username);
  }

  private UserTestBuilder copyWith(Consumer<UserTestBuilder> consumer) {
    final var copy = new UserTestBuilder(this);
    consumer.accept(copy);
    return copy;
  }

  @Override
  public User build() {
    return null;
  }
}