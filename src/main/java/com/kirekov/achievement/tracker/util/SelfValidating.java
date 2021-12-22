package com.kirekov.achievement.tracker.util;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Self validating class. Every class that extends {@linkplain SelfValidating} should call
 * {@linkplain SelfValidating#validateSelf()} in the constructor.
 */
public class SelfValidating {

  private final Validator validator;

  protected SelfValidating() {
    final var factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Evaluates all Bean Validations on the attributes of this instance.
   */
  protected void validateSelf() {
    final var violations = validator.validate(this);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
