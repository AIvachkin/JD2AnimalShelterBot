-- liquibase formatted sql

-- changeset denis:1
CREATE TABLE pet (
    pet_id BIGINT,
    name VARCHAR,
    age INT,
    user_id BIGINT
);
CREATE TABLE user_data_table (
    chat_id BIGINT,
    firstname VARCHAR,
    lastname VARCHAR,
    phone_number VARCHAR
);
CREATE TABLE volunteer (
    vol_id BIGINT,
    firstname VARCHAR,
    lastname VARCHAR,
    phone_number VARCHAR
);
CREATE TABLE report (
    rep_id BIGINT,
    report VARCHAR
);

-- changeset alexander:1
ALTER TABLE pet ADD fixed BOOLEAN DEFAULT false,
                ADD probation_period_up_to DATE DEFAULT null;

-- changeset alexander:2
CREATE TABLE correspondence (
   message_id BIGINT,
   answered BOOLEAN DEFAULT false,
   chat_id BIGINT,
   date_time TIMESTAMP,
   text TEXT,
   who_sent_it VARCHAR
);

CREATE TABLE trustees_reports (
    message_id BIGINT,
    chat_id BIGINT,
    date_time TIMESTAMP,
    answered BOOLEAN DEFAULT false,
    pet_id BIGINT,
    photo VARCHAR,
    text TEXT,
    viewed BOOLEAN DEFAULT false
);

DROP TABLE report
