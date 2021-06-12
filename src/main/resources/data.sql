DROP TABLE IF EXISTS bg_users;

CREATE TABLE `bg_users` (
	`user_id` VARCHAR(30) PRIMARY KEY,
	`pass_md5` VARCHAR(50) NOT NULL,
	`mail` VARCHAR(50) NOT NULL,
	`verified` TINYINT(1) NOT NULL,
	`user_uuid` VARCHAR(36) NOT NULL,
	`registration_ts` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
	`lastaccess_ts` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE UNIQUE INDEX mail ON bg_users(mail);
CREATE UNIQUE INDEX uuid ON bg_users(user_uuid);

INSERT INTO bg_users (user_id, pass_md5, mail, verified, user_uuid) VALUES
  ('subject_1', 'd4e2a737fbebdb7a5e34e25d5337d8d6', 'subject1@bgtest.com', 1, UUID()),
  ('subject_2', '722e6f6198a3c04f31bf65c40087922a', 'subject2@bgtest.com', 0, '675864d2-45cd-48a8-b730-0f01f3efa886'),
  ('subject_3', 'f04c4b942f8ae117a9029a283ef3a9b8', 'subject3@bgtest.com', 1, 'db5f65e7-6924-4468-99b0-88d1bd3cb2ee'),
  ('subject_0', 'c2877d97cd9a09a5f76f4345a6180806', 'subject4@bgtest.com', 0, '123e4567-e89b-12d3-a456-426614174000');
  
  

CREATE TABLE `bg_units` (
	`unit_uuid` VARCHAR(36) NOT NULL PRIMARY KEY,
	`title` VARCHAR(50) NOT NULL,
	`desc` VARCHAR(256) NOT NULL,
	`cancelpolicy_id` INT(10) NOT NULL DEFAULT '0',
	`region_id` INT(10) NOT NULL DEFAULT '0',
	`price` DECIMAL(20,6) NOT NULL DEFAULT '0.000000'
);
  
INSERT INTO bg_units
	(unit_uuid, title, `desc`, cancelpolicy_id, region_id, price) VALUES
	('92a885b6-42e4-46e2-a070-3a6282592806', 'Red View Development', 'A nice martian estate near the red lake!', 1, 1, '26564.41568'),
	('c5094a7f-4a7d-4e00-b5e9-4f96715216ea', 'Red Sky House', 'A place with profound red colors.', 2, 2, '12564.41568'),
	('db5f65e7-6924-4468-99b0-88d1bd3cb2ee', 'Pink Hills Hideout', 'Home sweet home.', 1, 3, '32264.41568'),
	('d24858a0-47e5-4b70-b101-a9a776c7faed', 'Blue Lake Bunker', 'Dont drink the liquid from this lake!.', 2, 3, '22564.41568'),
	('e98375fa-ba97-4fec-9c73-26917763dbcd', 'Pleasant Valley Tower', 'The mountains are visible in the horizon.', 0, 3, '32564.41568'),
	('d6653f5d-9579-4b9a-9fc6-558242b04a9e', 'Windy Bunker', 'You are strongly advised not to step out.', 0, 3, '32564.41568');
	


DROP TABLE IF EXISTS  `bg_reviews`;

CREATE TABLE `bg_reviews` (
	`unit_id` VARCHAR(36) NOT NULL,
	`user_id` VARCHAR(36) NOT NULL,
	`score` DECIMAL(2,1) NOT NULL DEFAULT '0',
	`desc` VARCHAR(256) NULL DEFAULT NULL,
	`review_ts` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	
	PRIMARY KEY(`unit_id`, `user_id`),

	CONSTRAINT `FK_bg_reviews_bg_units` FOREIGN KEY (`unit_id`) REFERENCES `bg_units` (`unit_uuid`) ON UPDATE RESTRICT ON DELETE CASCADE,
	CONSTRAINT `FK_bg_reviews_bg_users` FOREIGN KEY (`user_id`) REFERENCES `bg_users` (`user_uuid`) ON UPDATE RESTRICT ON DELETE CASCADE
);

INSERT INTO `bg_reviews` (unit_id, user_id, score, `desc`) VALUES
	('92a885b6-42e4-46e2-a070-3a6282592806', '123e4567-e89b-12d3-a456-426614174000', '4.3', 'Very good experience, I would go again!'),
	('92a885b6-42e4-46e2-a070-3a6282592806', '675864d2-45cd-48a8-b730-0f01f3efa886', '4.6', 'it was nice!'),
	('92a885b6-42e4-46e2-a070-3a6282592806', 'db5f65e7-6924-4468-99b0-88d1bd3cb2ee', '4.1', NULL),
	
	('d6653f5d-9579-4b9a-9fc6-558242b04a9e', '123e4567-e89b-12d3-a456-426614174000', '3.6', 'Too cold! fix heating plz'),
	('d6653f5d-9579-4b9a-9fc6-558242b04a9e', '675864d2-45cd-48a8-b730-0f01f3efa886', '1.0', 'my polar bear pet loved it!'),
	('d6653f5d-9579-4b9a-9fc6-558242b04a9e', 'db5f65e7-6924-4468-99b0-88d1bd3cb2ee', '4.6', 'cold was good for my skin thx');
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	