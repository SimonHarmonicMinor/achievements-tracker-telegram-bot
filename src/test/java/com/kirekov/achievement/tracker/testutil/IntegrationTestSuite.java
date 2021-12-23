package com.kirekov.achievement.tracker.testutil;

import java.util.Map;
import java.util.stream.Stream;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(
    initializers = IntegrationTestSuite.Initializer.class
)
public class IntegrationTestSuite {

  private static final String IMAGE_VERSION = "mysql:8.0";

  static class Initializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    static MySQLContainer<?> mysql = new MySQLContainer<>(IMAGE_VERSION);

    private static void startContainers() {
      Startables.deepStart(Stream.of(mysql)).join();
      // we can add further containers
      // here like rabbitmq or other databases
    }

    private static Map<String, String> createConnectionConfiguration() {
      return Map.of(
          "spring.datasource.url", mysql.getJdbcUrl(),
          "spring.datasource.username", mysql.getUsername(),
          "spring.datasource.password", mysql.getPassword()
      );
    }


    @Override
    public void initialize(
        ConfigurableApplicationContext applicationContext) {

      startContainers();

      ConfigurableEnvironment environment =
          applicationContext.getEnvironment();

      MapPropertySource testcontainers = new MapPropertySource(
          "testcontainers",
          (Map) createConnectionConfiguration()
      );

      environment.getPropertySources().addFirst(testcontainers);
    }
  }
}