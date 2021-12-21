CREATE TABLE achievement
(
    achievement_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(300) NOT NULL,
    description         VARCHAR(300) NOT NULL,
    user_id_who_created BIGINT       NOT NULL REFERENCES user (user_id),
    date_created        TIMESTAMP    NOT NULL
)