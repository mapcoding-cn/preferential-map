#密码:root 12345
#创建用户表
drop table if exists comment_info;
drop table if exists merchant_info;
drop table if exists user_info;
drop table if exists merchant;
drop table if exists user;
CREATE TABLE IF NOT EXISTS user
(
    user_id     BIGINT AUTO_INCREMENT,
    open_id     VARCHAR(100) NOT NULL,
    session_id  VARCHAR(500),
    wechat_name VARCHAR(100) NOT NULL,
    super_user  TINYINT      NOT NULL,
    create_time DATETIME     NOT NULL,
    update_time DATETIME     NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX idx_user_open_id ON user (open_id);


CREATE TABLE IF NOT EXISTS user_info
(
    id              BIGINT AUTO_INCREMENT,
    user_id         BIGINT   NOT NULL,
    credential_type TINYINT  NOT NULL,
    config          JSON,
    create_time     DATETIME NOT NULL,
    update_time     DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id_user_info FOREIGN KEY (user_id) REFERENCES user (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX idx_user_info_credential_type ON user_info (credential_type);
CREATE INDEX idx_user_info_user_id ON user_info (user_id);


CREATE TABLE IF NOT EXISTS merchant
(
    merchant_id       BIGINT AUTO_INCREMENT,
    merchant_name     VARCHAR(500) NOT NULL,
    merchant_address  TEXT,
    merchant_type     TINYINT      NOT NULL,
    profile           TEXT         NOT NULL,
    images            JSON         NOT NULL,
    merchant_location geometry     NOT NULL,
    create_time       DATETIME     NOT NULL,
    update_time       DATETIME     NOT NULL,
    PRIMARY KEY (merchant_id),
    SPATIAL KEY merchant_location (merchant_location)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE INDEX idx_merchant_merchant_type ON merchant (merchant_type);
CREATE FULLTEXT INDEX idx_merchant_merchant_name ON merchant (merchant_name) WITH PARSER ngram;

CREATE TABLE IF NOT EXISTS merchant_info
(
    id              BIGINT AUTO_INCREMENT,
    merchant_id     BIGINT        NOT NULL,
    merchant_area   TINYINT       NOT NULL,
    credential_type JSON          NOT NULL,
    config          JSON,
    source          VARCHAR(1000) NOT NULL,
    create_time     DATETIME      NOT NULL,
    update_time     DATETIME      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_merchant_id_merchant_info FOREIGN KEY (merchant_id) REFERENCES merchant (merchant_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE INDEX idx_merchant_info_merchant_id ON merchant_info (merchant_id);
CREATE INDEX idx_merchant_info_merchant_area ON merchant_info (merchant_area);

CREATE TABLE IF NOT EXISTS comment_info
(
    id          BIGINT AUTO_INCREMENT,
    merchant_id BIGINT   NOT NULL,
    user_id     BIGINT   NOT NULL,
    comment     TEXT     NOT NULL,
    image       TEXT,
    score       TINYINT,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_merchant_id_comment_info FOREIGN KEY (merchant_id) REFERENCES merchant (merchant_id),
    CONSTRAINT fk_user_id_comment_info FOREIGN KEY (user_id) REFERENCES user (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE INDEX idx_comment_info_user_id ON comment_info (user_id);
CREATE INDEX idx_comment_info_merchant_id ON comment_info (merchant_id);
