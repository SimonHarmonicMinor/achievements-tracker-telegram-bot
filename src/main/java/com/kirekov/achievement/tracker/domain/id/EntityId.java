package com.kirekov.achievement.tracker.domain.id;

/**
 * Id of the entity.
 *
 * @param <E> entity type
 * @param <I> id type
 */
public interface EntityId<E, I> {

  I getId();
}
