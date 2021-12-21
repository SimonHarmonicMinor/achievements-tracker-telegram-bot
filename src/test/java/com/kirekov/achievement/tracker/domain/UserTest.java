package com.kirekov.achievement.tracker.domain;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;

import com.kirekov.achievement.tracker.test_util.AbstractIntegrationTest;
import com.kirekov.achievement.tracker.test_util.DBTest;
import com.kirekov.achievement.tracker.test_util.TestDbFacade;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

  @Test
  @DisplayName("Should persist bot user")
  void shouldPersistBotUser() {
    final var bot = db.persist(() -> User.newBot(2L, "some_name"));

    final var foundBot = transactionTemplate.execute(status ->
        em.find(User.class, bot.getId())
    );

    assertThat(foundBot, ofBot(2L));
  }

  @Test
  @DisplayName("Should persist real user")
  void shouldPersistRealUser() {
    final var realUser = db.persist(() -> User.newRealUser(7L, "some_name"));

    final var foundRealUser = transactionTemplate.execute(status ->
        em.find(User.class, realUser.getId())
    );

    assertThat(foundRealUser, ofRealUser(7L, "some_name"));
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
      protected boolean matchesSafely(User item) {
        return !item.isBot() && item.getUniqueIdentifier() == uniqueIdentifier
            && item.getFirstName().equals(someName);
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
}