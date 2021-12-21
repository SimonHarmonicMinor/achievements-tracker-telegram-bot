CREATE TABLE user
(
    user_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    unique_identifier BIGINT       NOT NULL UNIQUE,
    is_bot            BOOLEAN      NOT NULL,
    date_registered   TIMESTAMP    NOT NULL,
    first_name        VARCHAR(200) NOT NULL,
    last_name         VARCHAR(300) NOT NULL,
    username          VARCHAR(200) NOT NULL
)