package com.kirekov.achievement.tracker.domain;

import static com.kirekov.achievement.tracker.domain.UserTestBuilder.aUser;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;

import com.kirekov.achievement.tracker.testutil.DBTest;
import com.kirekov.achievement.tracker.testutil.IntegrationTest;
import com.kirekov.achievement.tracker.testutil.TestDbFacade;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.support.TransactionTemplate;

@DBTest
class AchievementTest extends IntegrationTest {

  @Autowired
  private TestDbFacade db;
  @Autowired
  private TestEntityManager em;
  @Autowired
  private TransactionTemplate transactionTemplate;

  @BeforeEach
  void cleanDatabase() {
    db.cleanDatabase();
  }

  @ParameterizedTest
  @DisplayName("Should create new achievement")
  @CsvSource({
      "some_name, some_description",
      "anotherName, anotherDesc",
      "q1, ty1"
  })
  void shouldCreateNewAchievement(String name, String description) {
    final var user = db.save(aUser());
    final var achievement = db.save(
        () -> Achievement.newAchievementWithUser(name, description, user)
    );

    transactionTemplate.execute(status -> {
      final var foundAchievement = em.find(Achievement.class, achievement.getId());

      assertThat(foundAchievement, ofAchievement(name, description, user.getId()));
      return null;
    });
  }

  private static Matcher<Achievement> ofAchievement(String name, String desc, Long userId) {
    return new TypeSafeMatcher<>() {
      @Override
      protected boolean matchesSafely(Achievement item) {
        return item.getUserWhoCreated().getId().equals(userId)
            && item.getName().equals(name)
            && item.getDescription().equals(desc);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(achievementDescription(name, desc, userId));
      }

      @Override
      protected void describeMismatchSafely(Achievement item, Description mismatchDescription) {
        mismatchDescription.appendText(
            achievementDescription(item.getName(), item.getDescription(),
                item.getUserWhoCreated().getId())
        );
      }

      private String achievementDescription(String name, String desc, Long userId) {
        return format(
            "Achievement[name=%s,description=%s,userId=%s]",
            name,
            desc,
            userId
        );
      }
    };
  }
}