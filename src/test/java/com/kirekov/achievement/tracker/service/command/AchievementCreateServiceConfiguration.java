package com.kirekov.achievement.tracker.service.command;

import com.kirekov.achievement.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class AchievementCreateServiceConfiguration {

  @Autowired
  private UserRepository userRepository;

  @Bean
  public AchievementCreateService achievementCreateService() {
    return new AchievementCreateService(userRepository);
  }
}