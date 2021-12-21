package com.kirekov.achievement.tracker.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "achievement")
public class Achievement {

  public static Achievement newAchievement(String name, String description) {
    return newAchievementWithUser(name, description, null);
  }

  public static Achievement newAchievementWithUser(String name, String description, User userWhoCreated) {
    final var achievement = new Achievement();
    achievement.setName(name);
    achievement.setDescription(description);
    achievement.setUserWhoCreated(userWhoCreated);
    return achievement;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "achievement_id")
  private Long id;

  @NotNull(message = "Achievement name cannot be null")
  private String name = "";

  @NotNull(message = "Achievement description cannot be null")
  private String description = "";

  @Column(name = "user_id_who_created", updatable = false)
  @ManyToOne(fetch = LAZY)
  @NotNull(message = "User who created achievement cannot be null")
  private User userWhoCreated;

  @Column(name = "date_created", updatable = false)
  @NotNull(message = "Achievement date created cannot be null")
  private OffsetDateTime dateCreated = OffsetDateTime.now();

  protected Achievement() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public User getUserWhoCreated() {
    return userWhoCreated;
  }

  public OffsetDateTime getDateCreated() {
    return dateCreated;
  }

  void setName(String name) {
    this.name = name;
  }

  void setDescription(String description) {
    this.description = description;
  }

  void setUserWhoCreated(User userWhoCreated) {
    this.userWhoCreated = userWhoCreated;
  }

  void setDateCreated(OffsetDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }
}
