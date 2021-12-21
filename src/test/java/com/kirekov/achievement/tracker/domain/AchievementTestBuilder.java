package com.kirekov.achievement.tracker.domain;

import com.kirekov.achievement.tracker.test_util.TestBuilder;
import java.time.OffsetDateTime;
import java.util.function.Consumer;

public class AchievementTestBuilder implements TestBuilder<Achievement> {

  private String name = "";
  private String description = "";
  private TestBuilder<? extends User> userWhoCreated = () -> null;
  private OffsetDateTime dateCreated = OffsetDateTime.now();

  public static AchievementTestBuilder anAchievement() {
    return new AchievementTestBuilder();
  }

  private AchievementTestBuilder() {
  }

  private AchievementTestBuilder(AchievementTestBuilder builder) {
    this.name = builder.name;
    this.description = builder.description;
    this.userWhoCreated = builder.userWhoCreated;
    this.dateCreated = builder.dateCreated;
  }

  public AchievementTestBuilder withName(String name) {
    return copyWith(b -> b.name = name);
  }

  public AchievementTestBuilder withDescription(String description) {
    return copyWith(b -> b.description = description);
  }

  public AchievementTestBuilder withUserWhoCreated(TestBuilder<? extends User> userWhoCreated) {
    return copyWith(b -> b.userWhoCreated = userWhoCreated);
  }

  public AchievementTestBuilder withDateCreated(OffsetDateTime dateCreated) {
    return copyWith(b -> b.dateCreated = dateCreated);
  }

  private AchievementTestBuilder copyWith(Consumer<AchievementTestBuilder> consumer) {
    final var copy = new AchievementTestBuilder(this);
    consumer.accept(copy);
    return copy;
  }

  @Override
  public Achievement build() {
    final var achievement = new Achievement();
    achievement.setName(name);
    achievement.setDescription(description);
    achievement.setUserWhoCreated(userWhoCreated.build());
    achievement.setDateCreated(dateCreated);
    return achievement;
  }
}