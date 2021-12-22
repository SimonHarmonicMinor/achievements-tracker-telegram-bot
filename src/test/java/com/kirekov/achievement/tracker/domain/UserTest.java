package com.kirekov.achievement.tracker.domain;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import com.kirekov.achievement.tracker.test_util.AbstractIntegrationTest;
import com.kirekov.achievement.tracker.test_util.DBTest;
import com.kirekov.achievement.tracker.test_util.TestDbFacade;
import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.support.TransactionTemplate;

@DBTest
class UserTest extends AbstractIntegrationTest {

  @Autowired
  private TestDbFacade db;
  @Autowired
  private TestEntityManager em;
  @Autowired
  private TransactionTemplate transactionTemplate;

  @BeforeEach
  void beforeEach() {
    db.cleanDatabase();
  }

  @ParameterizedTest
  @DisplayName("Should persist bot user")
  @CsvSource({
      "2, some_name",
      "331, anotherName"
  })
  void shouldPersistBotUser(long uniqueIdentifier, String firstName) {
    final var bot = db.save(() -> User.newBot(uniqueIdentifier, firstName));

    final var foundBot = transactionTemplate.execute(status ->
        em.find(User.class, bot.getId())
    );

    assertThat(foundBot, ofBot(uniqueIdentifier));
  }

  @ParameterizedTest
  @DisplayName("Should persist real user")
  @CsvSource({
      "2234, name1",
      "3341241, name2"
  })
  void shouldPersistRealUser(long uniqueIdentifier, String firstName) {
    final var realUser = db.save(() -> User.newRealUser(uniqueIdentifier, firstName));

    final var foundRealUser = transactionTemplate.execute(status ->
        em.find(User.class, realUser.getId())
    );

    assertThat(foundRealUser, ofRealUser(uniqueIdentifier, firstName));
  }

  @Test
  @DisplayName("Should add achievement to user")
  void shouldAddAchievementToUser() {
    final var realUser = db.save(() -> User.newRealUser(1L, "name"));

    transactionTemplate.execute(status -> {
      realUser.addAchievement(Achievement.newAchievement("aName", "aDescription"));
      return em.merge(realUser);
    });

    transactionTemplate.execute(status -> {
      final var foundUser = em.find(User.class, realUser.getId());
      assertThat(foundUser.getAchievements(), hasSize(1));
      assertThat(foundUser.getAchievements(), hasAchievement("aName", "aDescription"));
      return null;
    });
  }

  private static Matcher<User> ofBot(long uniqueIdentifier) {
    return new TypeSafeMatcher<>() {
      @Override
      protected boolean matchesSafely(User item) {
        return item.getUniqueIdentifier() == uniqueIdentifier && item.isBot();
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(
            "Bot with uniqueIdentifier=" + uniqueIdentifier
        );
      }

      @Override
      protected void describeMismatchSafely(User item, Description mismatchDescription) {
        mismatchDescription.appendText(
            item.isBot()
                ? "Bot with uniqueIdentifier = " + item.getUniqueIdentifier()
                : "User with uniqueIdentifier = " + item.getUniqueIdentifier()
        );
      }
    };
  }

  private static Matcher<User> ofRealUser(long uniqueIdentifier, String someName) {
    return new TypeSafeMatcher<>() {
      @Override
      protected boolean matchesSafely(User user) {
        return !user.isBot()
            && user.getUniqueIdentifier() == uniqueIdentifier
            && user.getFirstName().equals(someName)
            && user.getDateRegistered() != null
            && user.getLastName() != null
            && user.getUsername() != null;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(
            format("User[%s, %s]", uniqueIdentifier, someName)
        );
      }

      @Override
      protected void describeMismatchSafely(User item, Description mismatchDescription) {
        mismatchDescription.appendText(
            format(
                "%s[%s, %s]",
                item.isBot() ? "Bot" : "User",
                item.getUniqueIdentifier(),
                item.getFirstName()
            )
        );
      }
    };
  }

  private static Matcher<Collection<Achievement>> hasAchievement(String name, String description) {
    return new TypeSafeMatcher<>() {
      @Override
      protected boolean matchesSafely(Collection<Achievement> item) {
        return item.stream()
            .anyMatch(
                achievement -> achievement.getName().equals(name)
                    && achievement.getDescription().equals(description)
            );
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(
            format("has achievement[name=%s, description=%s]", name, description)
        );
      }

      @Override
      protected void describeMismatchSafely(
          Collection<Achievement> item,
          Description mismatchDescription
      ) {
        mismatchDescription.appendText("does not have one");
      }
    };
  }
}