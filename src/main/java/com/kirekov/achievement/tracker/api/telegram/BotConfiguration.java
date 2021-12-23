package com.kirekov.achievement.tracker.api.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@Profile("prod")
public class BotConfiguration {

  @Bean
  public DefaultBotOptions defaultBotOptions() {
    return new DefaultBotOptions();
  }

  @Bean
  public Bot bot() {
    return new Bot(defaultBotOptions());
  }
}
