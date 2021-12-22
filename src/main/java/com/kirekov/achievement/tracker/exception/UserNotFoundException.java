package com.kirekov.achievement.tracker.exception;

import static java.lang.String.format;

import com.kirekov.achievement.tracker.domain.User;

/**
 * Exception to throw if {@linkplain User} is not found.
 */
public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(long userId) {
    super(getExceptionMessage(userId));
  }

  public UserNotFoundException(long userId, Throwable cause) {
    super(getExceptionMessage(userId), cause);
  }

  public UserNotFoundException(
      long userId,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace
  ) {
    super(getExceptionMessage(userId), cause, enableSuppression, writableStackTrace);
  }

  private static String getExceptionMessage(long userId) {
    return format("User with ID=%s is not found", userId);
  }
}
