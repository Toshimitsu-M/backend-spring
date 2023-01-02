DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS login_user;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS teacher;

CREATE TABLE teacher (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(60) NOT NULL,
 email VARCHAR(254) NOT NULL
);



--　ロール
CREATE TABLE roles(
    id INTEGER PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

-- ユーザー
CREATE TABLE login_user(
    id INTEGER PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(256) NOT NULL,
    password VARCHAR(128) NOT NULL
);

-- ユーザとロールの対応付け
CREATE TABLE user_role(
    user_id INTEGER,
    role_id INTEGER,
    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES login_user(id),
    CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);