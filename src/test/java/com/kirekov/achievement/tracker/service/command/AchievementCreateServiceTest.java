package com.kirekov.achievement.tracker.service.command;

import static com.kirekov.achievement.tracker.domain.UserTestBuilder.aUser;
import static com.kirekov.achievement.tracker.service.command.CreateAchievementCommandTestBuilder.aCreateAchievementCommand;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kirekov.achievement.tracker.domain.Achievement;
import com.kirekov.achievement.tracker.domain.User;
import com.kirekov.achievement.tracker.exception.UserNotFoundException;
import com.kirekov.achievement.tracker.testutil.DBTest;
import com.kirekov.achievement.tracker.testutil.IntegrationTestSuite;
import com.kirekov.achievement.tracker.testutil.TestDbFacade;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.support.TransactionTemplate;

@DBTest
@ContextConfiguration(classes = AchievementCreateServiceConfiguration.class)
@DisplayName("AchievementCreateService: test cases")
class AchievementCreateServiceTest extends IntegrationTestSuite {

  @Autowired
  private TestDbFacade db;
  @Autowired
  private TransactionTemplate transactionTemplate;
  @Autowired
  private AchievementCreateService service;

  @BeforeEach
  void beforeEach() {
    db.cleanDatabase();
  }

  @Test
  @DisplayName("Should throw exception if user is not found")
  void shouldThrowExceptionIfUserIsNotFound() {
    assertThrows(
        UserNotFoundException.class,
        () -> service.createAchievement(aCreateAchievementCommand().build()),
        "Should throw exception due to user absence"
    );
  }

  @ParameterizedTest
  @DisplayName("Should create new achievement")
  @CsvSource({
      "1, some_name, some_desc",
      "2, another_name, another_desc"
  })
  void shouldCreateNewAchievement(long userUniqueIdentifier, String name, String desc) {
    final var userId = db.save(
        aUser().withUniqueIdentifier(userUniqueIdentifier)
    ).getId();

    service.createAchievement(
        aCreateAchievementCommand()
            .withUserId(userId)
            .withAchievementName(name)
            .withAchievementDescription(desc)
            .build()
    );

    transactionTemplate.execute(status -> {
      final var user = db.find(User.class, userId);
      assertThat(user.getAchievements(), hasSize(1));
      assertThat(user.getAchievements(), hasAchievement(user.getId(), name, desc));
      return null;
    });
  }

  private static Matcher<Collection<Achievement>> hasAchievement(long userId, String name,
      String desc) {
    return new TypeSafeMatcher<>() {
      @Override
      protected boolean matchesSafely(Collection<Achievement> achievements) {
        return achievements.stream()
            .anyMatch(achievement ->
                achievement.getUserWhoCreated().getId().equals(userId)
                    && achievement.getName().equals(name)
                    && achievement.getDescription().equals(desc)
            );
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(
            achievementDescription(userId, name, desc)
        );
      }

      @Override
      protected void describeMismatchSafely(
          Collection<Achievement> achievement,
          Description mismatchDescription
      ) {
        mismatchDescription.appendText("no achievement");
      }

      private String achievementDescription(long userId, String name, String description) {
        return format("Achievement[userId=%s, name=%s, desc=%s]", userId, name, description);
      }
    };
  }
}