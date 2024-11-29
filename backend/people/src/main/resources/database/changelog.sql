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
    update_moment   timestamptz             NOT NULL,
    ready_moment    timestamptz,
    version         integer                 NOT NULL DEFAULT 0,
    is_published    boolean                 NOT NULL DEFAULT FALSE
);

--changeset vityaman:data

INSERT INTO people.faculty (long_name)
VALUES
    ('Control Systems and Robotics'),
    ('Software Engineering and Computer Systems'),
    ('Information Technologies and Programming'),
    ('Infocommunication Technologies'),
    ('Physics'),
    ('Infochemistry'),
    ('Biotechnologies');

INSERT INTO people.location (name, latitude, longitude)
VALUES
    ('Yandex, Benois', 59.958988, 30.406129),
    ('ITMO, Kronverkskiy', 59.957427, 30.308053),
    ('ITMO, Birzhevaya', 59.943970, 30.295717),
    ('ITMO, Lomonosova', 59.926567, 30.339097);
