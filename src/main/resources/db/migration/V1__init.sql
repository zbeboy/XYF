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

CREATE TABLE classify(
  classify_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  classify_name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE goods(
  goods_id VARCHAR(64) NOT NULL PRIMARY KEY ,
  goods_name VARCHAR(100) NOT NULL UNIQUE ,
  goods_price DOUBLE NOT NULL DEFAULT 0,
  goods_brief VARCHAR(200) ,
  goods_recommend INT NOT NULL DEFAULT 0,
  is_stick BOOLEAN NOT NULL DEFAULT 0,
  stick_serial INT NOT NULL DEFAULT 0,
  goods_serial INT NOT NULL DEFAULT 0,
  classify_id INT NOT NULL ,
  FOREIGN KEY (classify_id) REFERENCES classify(classify_id)
);

CREATE TABLE goods_pics(
  pic_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  goods_id VARCHAR(64) NOT NULL,
  pic_url VARCHAR(500) NOT NULL ,
  FOREIGN KEY (goods_id) REFERENCES goods(goods_id)
);

CREATE TABLE shop_info(
  shop_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  shop_name VARCHAR(200) NOT NULL,
  shop_brief TEXT
);

CREATE TABLE data_info(
  data_key VARCHAR(200) NOT NULL PRIMARY KEY,
  data_value VARCHAR(200) NOT NULL
);

CREATE TABLE feedback(
  feedback_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  customer_name VARCHAR(20) NOT NULL ,
  customer_contact VARCHAR(100) NOT NULL ,
  content VARCHAR(500) NOT NULL,
  is_deal BOOLEAN NOT NULL DEFAULT 0,
  remark VARCHAR(200)
);

INSERT INTO users (username, password)
VALUES ('govern', '$2a$10$wICea4jxjGeqeL99vXQBnO5dKtvT4Q2EbELrRoNZWCwuXJiLGNgE.');
INSERT INTO authorities (username, authority) VALUES ('govern', 'ROLE_ADMIN');