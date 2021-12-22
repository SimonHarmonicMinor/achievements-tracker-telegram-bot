package com.kirekov.achievement.tracker.service.command;

import com.kirekov.achievement.tracker.domain.Achievement;
import com.kirekov.achievement.tracker.exception.UserNotFoundException;
import com.kirekov.achievement.tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case to create new {@linkplain Achievement}.
 */
@Service
public class AchievementCreateService {

  private final UserRepository userRepository;

  public AchievementCreateService(
      UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  /**
   * Create new achievement.
   *
   * @param command achievement info
   * @throws UserNotFoundException is user is not found
   */
  @Transactional
  public void createAchievement(CreateAchievementCommand command) {
    final var user =
        userRepository.findById(command.getUserId())
            .orElseThrow(() -> new UserNotFoundException(command.getUserId()));
    final var achievement = Achievement.newAchievement(
        command.getAchievementName(),
        command.getAchievementDescription()
    );
    user.addAchievement(achievement);
    userRepository.save(user);
  }
}
