-- liquibase formatted sql

-- changeset denis:1
CREATE TABLE pet
(
    pet_id  BIGSERIAL,
    name    VARCHAR,
    age     INT,
    user_id BIGINT
);
CREATE TABLE user_data_table
(
    chat_id      BIGINT,
    firstname    VARCHAR,
    lastname     VARCHAR,
    phone_number VARCHAR
);
CREATE TABLE volunteer
(
    vol_id       BIGINT,
    firstname    VARCHAR,
    lastname     VARCHAR,
    phone_number VARCHAR
);
CREATE TABLE report
(
    rep_id BIGINT,
    report VARCHAR
);

-- changeset alexander:1
ALTER TABLE pet
    ADD fixed BOOLEAN DEFAULT false,
                ADD probation_period_up_to DATE DEFAULT null;

-- changeset alexander:2
CREATE TABLE correspondence
(
    message_id  BIGSERIAL,
    answered    BOOLEAN DEFAULT false,
    chat_id     BIGINT,
    date_time   TIMESTAMP,
    text        TEXT,
    who_sent_it VARCHAR
);

CREATE TABLE trustees_reports
(
    message_id BIGSERIAL,
    chat_id    BIGINT,
    date_time  TIMESTAMP,
    answered   BOOLEAN DEFAULT false,
    pet_id     BIGINT,
    photo      VARCHAR,
    text       TEXT,
    viewed     BOOLEAN DEFAULT false
);

DROP TABLE report;

-- changeset alexander:3
ALTER TABLE trustees_reports DROP COLUMN answered;

-- changeset andrew: 1
DROP TABLE volunteer;

-- changeset alexander:4
ALTER TABLE user_data_table RENAME TO dog_user_data_table;

-- changeset alexander:5
CREATE TABLE cat_user_data_table
(
    chat_id      BIGINT,
    firstname    VARCHAR,
    lastname     VARCHAR,
    phone_number VARCHAR
);

-- changeset alexander:6
ALTER TABLE correspondence
    ADD type_of_pet VARCHAR;

-- changeset alexander:7
ALTER TABLE pet
    ADD type_of_pet VARCHAR;

-- changeset alexander:8
ALTER TABLE pet
    ADD cat_user_id BIGINT;
ALTER TABLE pet RENAME COLUMN user_id TO dog_user_id;

-- changeset alexander:9
ALTER TABLE pet ALTER COLUMN cat_user_id TYPE BIGINT;
ALTER TABLE pet
    ALTER COLUMN probation_period_up_to SET DEFAULT null;
ALTER TABLE pet
    ALTER COLUMN fixed SET DEFAULT false;


-- changeset andrew: 2
ALTER TABLE trustees_reports RENAME COLUMN photo TO photo_file_path;
ALTER TABLE trustees_reports
    ADD photo_file_size BIGINT;
ALTER TABLE trustees_reports
    ADD media_type VARCHAR;
ALTER TABLE trustees_reports
    ADD preview BYTEA;

-- changeset andrew: 3
ALTER TABLE trustees_reports
    ADD type_of_pet VARCHAR;

-- changeset andrew: 4
ALTER TABLE trustees_reports DROP COLUMN media_type;

-- changeset denis:2
CREATE TABLE bad_user_data_table
(
    bad_user_id BIGSERIAL,
    dog_user_id BIGINT,
    cat_user_id BIGINT
);
ALTER TABLE cat_user_data_table
    ADD bad_user_id BIGINT;
ALTER TABLE dog_user_data_table
    ADD bad_user_id BIGINT;

-- changeset andrew: 5
ALTER TABLE cat_user_data_table
    ADD bad_user_bad_user_id BIGINT;
ALTER TABLE dog_user_data_table
    ADD bad_user_bad_user_id BIGINT;