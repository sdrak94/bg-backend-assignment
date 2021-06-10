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
  ('subject_1', 'as8d0a9s8d0a9sas9d0', 'subject1@bgtest.com', 1, UUID()),
  ('subject_2', 'as8d0a9s8d0a9sas9d0', 'subject2@bgtest.com', 0, UUID()),
  ('subject_3', 'as8d0a9s8d0a9sas9d0', 'subject3@bgtest.com', 1, UUID()),
  ('subject_0', 'as8d0a9s8d0a9sas9d0', 'subject4@bgtest.com', 0, '123e4567-e89b-12d3-a456-426614174000')