package com.kirekov.achievement.tracker.util;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Extend this class to support JSR 303 Bean Validation . Typically call
 * <code>validateSelf</code> in derived constructor.
 */
public abstract class SelfValidating {

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
