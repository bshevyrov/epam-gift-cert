drop table  if exists gift_certificate;
CREATE TABLE gift_certificate (
                                  id int PRIMARY KEY AUTO_INCREMENT,
                                  name varchar(45) NULL,
                                  description varchar(45) NULL,
                                  price double NULL,
                                  create_date datetime DEFAULT CURRENT_TIMESTAMP,
                                  last_update_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  duration int NULL
);
drop table if exists tag;
CREATE TABLE tag (
                     id int PRIMARY KEY AUTO_INCREMENT,
                     name varchar(45) NULL
);
drop table if exists gift_certificate_has_tag;
CREATE TABLE gift_certificate_has_tag (
                                          gift_certificate_id int NOT NULL,
                                          tag_id int NOT NULL
);
CREATE INDEX fk_new_table_has_tag_new_table_idx ON gift_certificate_has_tag (gift_certificate_id);
CREATE INDEX fk_new_table_has_tag_tag1_idx ON gift_certificate_has_tag (tag_id);
