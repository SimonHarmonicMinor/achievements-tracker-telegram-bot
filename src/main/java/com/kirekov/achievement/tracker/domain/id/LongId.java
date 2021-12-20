package com.kirekov.achievement.tracker.domain.id;


import com.kirekov.achievement.tracker.domain.base.BaseValueObject;
import java.util.Objects;

public final class LongId<E> extends BaseValueObject implements EntityId<E, Long> {

  private final Long id;

  public static <E> LongId<E> nullId() {
    return new LongId<>(null);
  }

  public LongId(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
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
    LongId<?> longId = (LongId<?>) o;
    return Objects.equals(id, longId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return id == null ? "NullLongId" : "LongId[" + id + "]";
  }
}
