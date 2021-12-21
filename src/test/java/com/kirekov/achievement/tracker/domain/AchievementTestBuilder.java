package com.kirekov.achievement.tracker.domain;

import com.kirekov.achievement.tracker.test_util.TestBuilder;
import java.time.OffsetDateTime;

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
    final var copy = new AchievementTestBuilder(this);
    copy.name = name;
    return copy;
  }

  public AchievementTestBuilder withDescription(String description) {
    final var copy = new AchievementTestBuilder(this);
    copy.description = description;
    return copy;
  }

  public AchievementTestBuilder withUserWhoCreated(TestBuilder<? extends User> userWhoCreated) {
    final var copy = new AchievementTestBuilder(this);
    copy.userWhoCreated = userWhoCreated;
    return copy;
  }

  public AchievementTestBuilder withDateCreated(OffsetDateTime dateCreated) {
    final var copy = new AchievementTestBuilder(this);
    copy.dateCreated = dateCreated;
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