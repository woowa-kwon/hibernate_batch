DROP TABLE IF EXISTS identity;
CREATE TABLE identity
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);

DROP TABLE IF EXISTS non_identity;
CREATE TABLE non_identity
(
    id      BIGINT PRIMARY KEY,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);

DROP TABLE IF EXISTS parent;
CREATE TABLE parent
(
    id      BIGINT PRIMARY KEY,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);

DROP TABLE IF EXISTS child;
CREATE TABLE child
(
    id      BIGINT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);DROP TABLE IF EXISTS identity;
CREATE TABLE identity
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);

DROP TABLE IF EXISTS non_identity;
CREATE TABLE non_identity
(
    id      BIGINT PRIMARY KEY,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);

DROP TABLE IF EXISTS parent;
CREATE TABLE parent
(
    id      BIGINT PRIMARY KEY,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);

DROP TABLE IF EXISTS child;
CREATE TABLE child
(
    id      BIGINT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(50) NOT NULL,
    c3      VARCHAR(50) NOT NULL,
    c4      VARCHAR(50) NOT NULL,
    c5      VARCHAR(50) NOT NULL,
    version INTEGER     NOT NULL
);

DROP TABLE IF EXISTS uuid;
CREATE TABLE uuid
(
    id      BINARY(16) PRIMARY KEY,
    c1      INTEGER     NOT NULL,
    c2      VARCHAR(255) NOT NULL,
    c3      VARCHAR(255) NOT NULL,
    c4      VARCHAR(255) NOT NULL,
    c5      VARCHAR(255) NOT NULL,
    c6      VARCHAR(255) NOT NULL,
    c7      VARCHAR(255) NOT NULL,
    c8      VARCHAR(255) NOT NULL,
    c9      VARCHAR(255) NOT NULL,
    c10      VARCHAR(255) NOT NULL,
    c11      VARCHAR(255) NOT NULL,
    c12      VARCHAR(255) NOT NULL,
    c13      VARCHAR(255) NOT NULL,
    c14      VARCHAR(255) NOT NULL,
    c15      VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS non_batch;
CREATE TABLE non_batch
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    c1      VARCHAR(255) NOT NULL,
    c2      VARCHAR(255) NOT NULL,
    c3      VARCHAR(255) NOT NULL,
    c4      VARCHAR(255) NOT NULL,
    c5      VARCHAR(255) NOT NULL,
    c6      VARCHAR(255) NOT NULL,
    c7      VARCHAR(255) NOT NULL,
    c8      VARCHAR(255) NOT NULL,
    c9      VARCHAR(255) NOT NULL,
    c10      VARCHAR(255) NOT NULL
)