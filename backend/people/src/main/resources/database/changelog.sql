--liquibase formatted sql

--changeset vityaman:initialize
CREATE SCHEMA people;

CREATE DOMAIN people.person_name
AS varchar(32);

CREATE DOMAIN people.person_height
AS integer;

CREATE TABLE people.faculty (
    id              serial                  PRIMARY KEY,
    long_name       varchar(64)             NOT NULL UNIQUE,
    creation_moment timestamptz             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE people.location (
    id              serial                  PRIMARY KEY,
    name            varchar(128)            NOT NULL UNIQUE,
    latitude        float8                  NOT NULL,
    longitude       float8                  NOT NULL,
    creation_moment timestamptz             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE people.person (
    account_id      integer                 PRIMARY KEY,
    first_name      people.person_name,
    last_name       people.person_name,
    height          people.person_height,
    birthday        date,
    faculty_id      integer                 REFERENCES people.faculty(id),
    location_id     integer                 REFERENCES people.location(id),
    creation_moment timestamptz             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_moment   timestamptz,
    ready_moment    timestamptz
);
