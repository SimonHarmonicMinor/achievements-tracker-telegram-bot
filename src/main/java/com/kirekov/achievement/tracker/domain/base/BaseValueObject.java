package com.kirekov.achievement.tracker.domain.base;

import com.kirekov.achievement.tracker.util.SelfValidating;

/**
 * Base value object. All children should be immutable and implement {@linkplain
 * Object#equals(Object)}, {@linkplain Object#hashCode()}, {@linkplain Object#toString()} methods.
 */
public abstract class BaseValueObject extends SelfValidating {

  protected BaseValueObject() {
    validateSelf();
  }
}
