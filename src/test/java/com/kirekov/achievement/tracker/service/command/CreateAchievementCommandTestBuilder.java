package com.kirekov.achievement.tracker.service.command;

import com.kirekov.achievement.tracker.testutil.TestBuilder;
import java.util.function.Consumer;

public class CreateAchievementCommandTestBuilder implements TestBuilder<CreateAchievementCommand> {

  private long userId = -1;
  private String achievementName = "";
  private String achievementDescription = "";

  private CreateAchievementCommandTestBuilder() {
  }

  private CreateAchievementCommandTestBuilder(CreateAchievementCommandTestBuilder builder) {
    this.userId = builder.userId;
    this.achievementName = builder.achievementName;
    this.achievementDescription = builder.achievementDescription;
  }

  public static CreateAchievementCommandTestBuilder aCreateAchievementCommand() {
    return new CreateAchievementCommandTestBuilder();
  }

  public CreateAchievementCommandTestBuilder withUserId(long userId) {
    return copyWith(b -> b.userId = userId);
  }

  public CreateAchievementCommandTestBuilder withAchievementName(String name) {
    return copyWith(b -> b.achievementName = name);
  }

  public CreateAchievementCommandTestBuilder withAchievementDescription(String desc) {
    return copyWith(b -> b.achievementDescription = desc);
  }

  private CreateAchievementCommandTestBuilder copyWith(
      Consumer<CreateAchievementCommandTestBuilder> consumer
  ) {
    final var copy = new CreateAchievementCommandTestBuilder(this);
    consumer.accept(copy);
    return copy;
  }

  @Override
  public CreateAchievementCommand build() {
    return new CreateAchievementCommand(userId, achievementName, achievementDescription);
  }
}