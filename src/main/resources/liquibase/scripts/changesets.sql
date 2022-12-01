-- liquibase formatted sql

-- changeset denis:1
CREATE TABLE pet (
    pet_id BIGINT,
    name VARCHAR,
    age INT,
    user_id BIGINT,
    probation_period_up_to DATE,
    fixed BOOLEAN
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

-- changeset denis:2
CREATE TABLE correspondence (
    message_id BIGINT,
    chat_id BIGINT,
    date_time TIMESTAMP,
    text TEXT,
    answered BOOLEAN,
    who_sent_it VARCHAR
);
-- changeset denis:3
CREATE TABLE trustees_reports (
    message_id BIGINT,
    chat_id BIGINT,
    pet_id BIGINT,
    date_time TIMESTAMP,
    photo VARCHAR,
    text TEXT,
    viewed BOOLEAN
);
