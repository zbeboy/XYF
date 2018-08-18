CREATE TABLE users (
  username VARCHAR(64) PRIMARY KEY,
  password VARCHAR(300) NOT NULL,
  disabled  BOOLEAN DEFAULT 0,
  account_expired BOOLEAN DEFAULT 0,
  account_locked BOOLEAN DEFAULT 0,
  credentials_expired BOOLEAN DEFAULT 0
);

CREATE TABLE authorities (
  username  VARCHAR(64) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users (username),
  PRIMARY KEY (username, authority)
);

CREATE TABLE persistent_logins (
  username  VARCHAR(64) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL
);

INSERT INTO users (username, password)
VALUES ('govern', '$2a$10$wICea4jxjGeqeL99vXQBnO5dKtvT4Q2EbELrRoNZWCwuXJiLGNgE.');
INSERT INTO authorities (username, authority) VALUES ('govern', 'ROLE_ADMIN');