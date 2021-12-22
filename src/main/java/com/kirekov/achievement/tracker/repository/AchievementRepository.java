package com.kirekov.achievement.tracker.repository;

import com.kirekov.achievement.tracker.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@linkplain Achievement} JPA-repository.
 */
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

}
