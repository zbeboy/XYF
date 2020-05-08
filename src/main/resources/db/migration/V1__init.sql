CREATE TABLE users (
  username VARCHAR(64) PRIMARY KEY,
  password VARCHAR(300) NOT NULL,
  disabled  BOOLEAN DEFAULT 0,
  account_expired BOOLEAN DEFAULT 0,
  account_locked BOOLEAN DEFAULT 0,
  credentials_expired BOOLEAN DEFAULT 0,
  address VARCHAR(200),
  real_name VARCHAR(10),
  sex VARCHAR(2),
  contact VARCHAR(20),
  access_token VARCHAR(64),
  token_expired_date DATETIME,
  photo VARCHAR(100)
);

CREATE TABLE authorities (
  username  VARCHAR(64) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users (username),
  PRIMARY KEY (username, authority)
);

CREATE TABLE classify(
  classify_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  classify_name VARCHAR(30) NOT NULL UNIQUE,
  classify_is_del BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE goods(
  goods_id VARCHAR(64) NOT NULL PRIMARY KEY ,
  goods_name VARCHAR(100) NOT NULL UNIQUE ,
  goods_price DOUBLE NOT NULL DEFAULT 0,
  goods_brief VARCHAR(200) ,
  goods_recommend INT NOT NULL DEFAULT 0,
  goods_serial INT NOT NULL DEFAULT 0,
  goods_is_del BOOLEAN NOT NULL DEFAULT 0,
  goods_item INT NOT NULL DEFAULT 0,
  goods_is_stick BOOLEAN NOT NULL DEFAULT 0,
  classify_id INT NOT NULL ,
  FOREIGN KEY (classify_id) REFERENCES classify(classify_id)
);

CREATE TABLE goods_pics(
  pic_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  goods_id VARCHAR(64) NOT NULL,
  pic_url VARCHAR(500) NOT NULL ,
  FOREIGN KEY (goods_id) REFERENCES goods(goods_id)
);

CREATE TABLE banner(
  banner_id VARCHAR(64) NOT NULL PRIMARY KEY ,
  banner_serial INT NOT NULL DEFAULT 0,
  banner_url VARCHAR(500) NOT NULL ,
  banner_is_hide BOOLEAN NOT NULL DEFAULT 0,
  banner_item INT NOT NULL DEFAULT 0
);

CREATE TABLE data_info(
  data_key VARCHAR(200) NOT NULL PRIMARY KEY,
  data_value VARCHAR(600) NOT NULL
);

CREATE TABLE feedback(
  feedback_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  customer_name VARCHAR(20) NOT NULL ,
  customer_contact VARCHAR(100) NOT NULL ,
  content VARCHAR(500) NOT NULL,
  create_date DATETIME NOT NULL,
  has_deal BOOLEAN NOT NULL DEFAULT 0,
  remark VARCHAR(200)
);

CREATE TABLE table_time(
  table_name VARCHAR(20) NOT NULL PRIMARY KEY ,
  deal_time DATETIME NOT NULL
);

CREATE TABLE channel_info(
  channel_id VARCHAR(64) NOT NULL PRIMARY KEY ,
  channel_name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE request_log(
  log_id VARCHAR(64) NOT NULL PRIMARY KEY ,
  request_url VARCHAR(100) ,
  request_param VARCHAR(500),
  result VARCHAR(500),
  channel_name VARCHAR(20),
  request_date DATETIME NOT NULL
);

CREATE TABLE system_api(
  api_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  param VARCHAR(200) ,
  remark VARCHAR(500)
);

INSERT INTO users (username, password, photo)
VALUES ('govern', '$2a$10$wICea4jxjGeqeL99vXQBnO5dKtvT4Q2EbELrRoNZWCwuXJiLGNgE.', '/images/photo.jpg');
INSERT INTO authorities (username, authority) VALUES ('govern', 'ROLE_ADMIN');

INSERT INTO channel_info(channel_id, channel_name) VALUES
('28ecaae3e6ef47c68698a20b13c271ce','PC');
INSERT INTO channel_info(channel_id, channel_name) VALUES
('dcaf68564f0242aba9d03d20cd9fef60','APP');