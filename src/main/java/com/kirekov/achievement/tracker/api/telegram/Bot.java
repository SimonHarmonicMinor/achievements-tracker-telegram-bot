package com.kirekov.achievement.tracker.api.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Telegram bot entry point.
 */
@Component
public class Bot extends TelegramLongPollingBot {

  private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

  @Value("${bot.token}")
  private String token;
  @Value("${bot.username}")
  private String username;


  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onRegister() {
    LOG.info("Telegram bot '{}' has been registered", username);
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (!update.hasMessage()) {
      return;
    }
    final var message = update.getMessage();
    try {
      execute(new SendMessage(String.valueOf(message.getChatId()), "Stub message"));
    } catch (TelegramApiException e) {
      LOG.error("Failed to send message to " + message.getChat(), e);
    }
  }
}
