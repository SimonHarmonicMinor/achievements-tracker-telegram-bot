package com.kirekov.achievement.tracker.repository;

import com.kirekov.achievement.tracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@linkplain User} JPA-repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
