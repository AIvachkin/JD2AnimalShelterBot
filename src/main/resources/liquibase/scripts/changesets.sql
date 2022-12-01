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