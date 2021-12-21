package com.kirekov.achievement.tracker.test_util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import java.lang.annotation.Retention;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Retention(RUNTIME)
@DataJpaTest(showSql = false)
@Transactional(propagation = NOT_SUPPORTED)
@Import(TestDbFacade.class)
public @interface DBTest {

}
