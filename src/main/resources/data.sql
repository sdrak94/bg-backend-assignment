DROP TABLE IF EXISTS bg_users;

CREATE TABLE `bg_users` (
	`user_id` VARCHAR(30) PRIMARY KEY,
	`pass_md5` VARCHAR(50) NOT NULL,
	`user_uuid` VARCHAR(36) NOT NULL,
	`registration_ts` TIMESTAMP NULL DEFAULT NULL,
	`lastaccess_ts` TIMESTAMP NULL DEFAULT NULL
);

INSERT INTO bg_users (user_id, pass_md5, user_uuid) VALUES
  ('subject_1', 'as8d0a9s8d0a9sas9d0', UUID()),
  ('subject_2', 'as8d0a9s8d0a9sas9d0', UUID()),
  ('subject_123', 'as8d0a9s8d0a9sas9d0', UUID());