package com.kirekov.achievement.tracker.testutil;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Retention(RUNTIME)
@SpringBootTest(classes = SystemTestSuite.BotTestConfiguration.class)
@Import(TestDbFacade.class)
@AutoConfigureTestEntityManager
@ActiveProfiles("test")
public @interface SystemTest {
}
