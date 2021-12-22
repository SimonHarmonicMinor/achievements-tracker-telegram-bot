package com.kirekov.achievement.tracker.domain;


import static java.util.Objects.requireNonNullElse;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @NaturalId
  @Column(name = "unique_identifier", updatable = false, unique = true)
  @NotNull(message = "User unique identifier cannot be null")
  private long uniqueIdentifier;

  @Column(name = "is_bot", updatable = false)
  @NotNull(message = "User bot flag cannot be null")
  private boolean isBot;

  @Column(name = "date_registered", updatable = false)
  @NotNull(message = "User date registered cannot be null")
  private OffsetDateTime dateRegistered = OffsetDateTime.now();

  @Column(name = "first_name")
  @NotNull(message = "User first name cannot be null")
  private String firstName;

  @Column(name = "last_name")
  private String lastName = "";

  @Column(name = "username", updatable = false)
  private String username = "";

  @OneToMany(fetch = LAZY, mappedBy = "userWhoCreated", cascade = {PERSIST, MERGE})
  private List<@Valid Achievement> achievements = new ArrayList<>();

  public static User newRealUser(long uniqueIdentifier, String firstName) {
    return newRealUser(uniqueIdentifier, firstName, "", "");
  }

  protected User() {
  }

  public static User newRealUser(
      long uniqueIdentifier,
      String firstName,
      String lastName,
      String username
  ) {
    final var user = new User();
    user.setUniqueIdentifier(uniqueIdentifier);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setUsername(username);
    user.setBot(false);
    return user;
  }

  public static User newBot(long uniqueIdentifier, String firstName) {
    final var user = new User();
    user.setBot(true);
    user.setUniqueIdentifier(uniqueIdentifier);
    user.setFirstName(firstName);
    return user;
  }

  public void addAchievement(Achievement achievement) {
    achievement.setUserWhoCreated(this);
    achievements.add(achievement);
  }

  public Long getId() {
    return id;
  }

  public long getUniqueIdentifier() {
    return uniqueIdentifier;
  }

  public String getFirstName() {
    return firstName;
  }

  public boolean isBot() {
    return isBot;
  }

  public OffsetDateTime getDateRegistered() {
    return dateRegistered;
  }

  public String getLastName() {
    return requireNonNullElse(lastName, "");
  }

  public String getUsername() {
    return requireNonNullElse(username, "");
  }

  public List<Achievement> getAchievements() {
    return achievements;
  }

  void setUniqueIdentifier(long uniqueIdentifier) {
    this.uniqueIdentifier = uniqueIdentifier;
  }

  void setBot(boolean bot) {
    isBot = bot;
  }

  void setDateRegistered(OffsetDateTime dateRegistered) {
    this.dateRegistered = dateRegistered;
  }

  void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  void setLastName(String lastName) {
    this.lastName = lastName;
  }

  void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return uniqueIdentifier == user.uniqueIdentifier;
  }

  @Override
  public int hashCode() {
    return Objects.hash(uniqueIdentifier);
  }
}
