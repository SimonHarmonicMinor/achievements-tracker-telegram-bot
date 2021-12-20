package com.kirekov.achievement.tracker.domain.base;

import com.kirekov.achievement.tracker.domain.id.LongId;
import com.kirekov.achievement.tracker.util.SelfValidating;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public abstract class BaseEntity<E> extends SelfValidating {

  @NotNull(message = "Entity id cannot be null")
  private final LongId<E> id;

  protected BaseEntity(LongId<E> id) {
    this.id = id;
    validateSelf();
  }

  public LongId<E> getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseEntity<?> that = (BaseEntity<?>) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
