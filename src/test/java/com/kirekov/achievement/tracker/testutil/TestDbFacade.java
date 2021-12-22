package com.kirekov.achievement.tracker.testutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

public class TestDbFacade {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private TestEntityManager testEntityManager;
  @Autowired
  private TransactionTemplate transactionTemplate;

  public void cleanDatabase() {
    JdbcTestUtils.deleteFromTables(
        jdbcTemplate,
        "achievement", "user"
    );
  }

  public <T> TestBuilder<T> persistedOnce(TestBuilder<T> builder) {
    return new TestBuilder<>() {
      private T entity;

      @Override
      public T build() {
        if (entity == null) {
          entity = persisted(builder).build();
        }
        return entity;
      }
    };
  }

  public <T> TestBuilder<T> persisted(TestBuilder<T> builder) {
    return () -> save(builder);
  }

  public void saveAll(TestBuilder<?>... builders) {
    transactionTemplate.execute(status -> {
      for (TestBuilder<?> builder : builders) {
        save(builder);
      }
      return null;
    });
  }

  public <T> T save(TestBuilder<T> builder) {
    return transactionTemplate.execute(
        status -> testEntityManager.persistAndFlush(builder.build())
    );
  }

  @TestConfiguration
  public static class Config {

    @Bean
    public TestDbFacade testDbFacade() {
      return new TestDbFacade();
    }
  }
}
