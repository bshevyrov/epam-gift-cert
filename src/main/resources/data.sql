
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
INSERT INTO gift_certificate
VALUES (1, 'First Client', 'Special promotion for first customer', 100, '2023-04-29 22:07:01'
       , '2023-04-29 22:07:01', 66),
       (2, 'Initial promotion campaign -1', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (3, 'Initial promotion campaign -2', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (4, 'Initial promotion campaign -3', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (5, 'Initial promotion campaign -4', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (6, 'Initial promotion campaign -5', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (7, 'Initial promotion campaign -6', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (8, 'Initial promotion campaign -7', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (9, 'Initial promotion campaign -8', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (10, 'Initial promotion campaign -9', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (11, 'Initial promotion campaign -10', 'Promotion for new customers.', 20, '2023-04-28 22:10:17'
       , '2023-04-28 22:10:17', 7),
       (23, 'TEST111', 'Promotion for new customers.', 66, NULL
       , NULL, 4),
       (24, 'TEST111', 'Promotion for new customers.', 66, NULL
       , NULL, 4),
       (25, 'TEST111', 'Promotion for new customers.', 66, NULL
       , NULL, 4),
       (29, 'TEST2', 'Promotion for new customers.', 66, NULL
       , NULL, 4),
       (30, 'TESTerror', 'Promotion for new customers.', 66, NULL
       , NULL, 4);
INSERT INTO gift_certificate_has_tag
VALUES (1, 2),
       (29, 2),
       (1, 3),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (6, 3),
       (7, 3),
       (8, 3),
       (9, 3),
       (10, 3),
       (23, 14),
       (24, 14),
       (25, 15),
       (29, 15),
       (29, 16),
       (30, 17);
INSERT INTO tag
VALUES (1, 'birthday'),
       (2, 'vip'),
       (3, 'promo'),
       (4, 'partner'),
       (5, 'test'),
       (14, 'euro'),
       (15, 'euro2'),
       (16, 'euro3'),
       (17, NULL);
