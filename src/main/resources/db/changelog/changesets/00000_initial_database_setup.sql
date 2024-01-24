-- create database social;
-- drop table cities, skills, subscriptions, user_skills, users cascade;

CREATE TABLE IF NOT EXISTS "cities" (
  "id" uuid PRIMARY KEY,
  "city_name" varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS "users" (
  "id" uuid PRIMARY KEY,
  "fname" varchar(50) NOT NULL,
  "lname" varchar(50) NOT NULL,
  "mname" varchar(50),
  "gender" smallint,
  "bdate" date,
  "city_id" uuid,
  "avatar_url" varchar(500) NOT NULL,
  "bio" varchar(1000),
  "nickname" varchar(50) NOT NULL,
  "email" varchar(150) NOT NULL,
  "phone" varchar(20),
  "created_at" timestamp NOT NULL,
  "deleted" boolean,
  "deleted_at" timestamp
);

CREATE TABLE IF NOT EXISTS "skills" (
  "id" uuid PRIMARY KEY,
  "skill_name" varchar(100) NOT NULL,
  "skill_desc" varchar(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS "user_skills" (
  "skill_id" uuid NOT NULL,
  "user_id" uuid NOT NULL
);

CREATE TABLE IF NOT EXISTS "subscriptions" (
  "user_following_id" uuid NOT NULL,
  "user_followed_id" uuid NOT NULL,
  "created_at" timestamp NOT NULL
);

CREATE INDEX IF NOT EXISTS "FK_usercity" ON "users" ("city_id");

CREATE INDEX IF NOT EXISTS "FK_userskill" ON "user_skills" ("skill_id");

CREATE INDEX IF NOT EXISTS "FK_userid" ON "user_skills" ("user_id");

CREATE INDEX IF NOT EXISTS "FK_following" ON "subscriptions" ("user_following_id");

CREATE INDEX IF NOT EXISTS "FK_followed" ON "subscriptions" ("user_followed_id");

ALTER TABLE "subscriptions" ADD FOREIGN KEY ("user_followed_id") REFERENCES "users" ("id")
ON DELETE restrict;

ALTER TABLE "subscriptions" ADD FOREIGN KEY ("user_following_id") REFERENCES "users" ("id")
ON DELETE restrict;

ALTER TABLE "user_skills" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id")
ON DELETE restrict;

ALTER TABLE "users" ADD FOREIGN KEY ("city_id") REFERENCES "cities" ("id")
ON DELETE restrict;

ALTER TABLE "user_skills" ADD FOREIGN KEY ("skill_id") REFERENCES "skills" ("id")
ON DELETE restrict;

---
CREATE INDEX IF NOT EXISTS  i_users_fname ON users USING BTREE (fname);
CREATE INDEX IF NOT EXISTS  i_users_lname ON users USING BTREE (lname);
CREATE INDEX IF NOT EXISTS  i_users_mname ON users USING BTREE (mname);
CREATE INDEX IF NOT EXISTS  i_users_nickname ON users USING HASH (nickname);
CREATE INDEX IF NOT EXISTS  i_users_email ON users USING HASH (email);
CREATE INDEX IF NOT EXISTS  i_users_phone ON users USING HASH (phone);
CREATE INDEX IF NOT EXISTS  i_users_gender ON users (gender);


CREATE INDEX IF NOT EXISTS  i_cities_name ON cities USING BTREE (city_name);

CREATE INDEX IF NOT EXISTS  i_skills_name ON skills USING BTREE (skill_name);

---