package com.kirekov.achievement.tracker.service.command;

import com.kirekov.achievement.tracker.domain.Achievement;
import com.kirekov.achievement.tracker.domain.User;
import com.kirekov.achievement.tracker.util.SelfValidating;
import javax.validation.constraints.NotNull;

/**
 * Command to assign new {@linkplain Achievement} to {@linkplain User}.
 */
public class CreateAchievementCommand extends SelfValidating {

  private final long userId;
  @NotNull
  private final String achievementName;
  @NotNull
  private final String achievementDescription;

  public CreateAchievementCommand(
      long userId,
      String achievementName,
      String achievementDescription
  ) {
    this.userId = userId;
    this.achievementName = achievementName;
    this.achievementDescription = achievementDescription;
    validateSelf();
  }

  public long getUserId() {
    return userId;
  }

  public String getAchievementName() {
    return achievementName;
  }

  public String getAchievementDescription() {
    return achievementDescription;
  }
}
