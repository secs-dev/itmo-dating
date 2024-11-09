--liquibase formatted sql

--changeset vityaman:initialize
CREATE SCHEMA people;

--changeset vityaman:people
CREATE TABLE people.faculty (
    id              serial              PRIMARY KEY,
    short_name      VARCHAR(16)         NOT NULL UNIQUE
                    CHECK (short_name ~ '^[A-Za-z]{2,16}$'),
    full_name       VARCHAR(128)        NOT NULL UNIQUE
                    CHECK (full_name ~ '^[A-Za-z ]{6,128}$')
);

CREATE DOMAIN people.person_name AS VARCHAR(32)
CHECK (VALUE ~ '^[A-Za-z,.''-]{2,32}$');

CREATE TABLE people.person (
    id              serial              PRIMARY KEY,
    first_name      people.person_name  NOT NULL,
    last_name       people.person_name  NOT NULL,
    height          integer             NOT NULL CHECK (height BETWEEN 50 AND 280),
    birthday        date                NOT NULL CHECK ('1990-01-01'::date <= birthday),
    faculty_id      integer             NOT NULL REFERENCES people.faculty(id),
    creation_moment timestamptz         NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE people.topic (
    id              serial              PRIMARY KEY,
    name            VARCHAR(32)         NOT NULL UNIQUE
                    CHECK (name ~ '^[A-Za-z]{3,32}$')
);

CREATE TABLE people.person_interest (
    person_id       integer             REFERENCES people.person(id),
    topic_id        integer             REFERENCES people.topic(id),
    rank            integer             NOT NULL CHECK (rank BETWEEN 1 AND 5),
    PRIMARY KEY (person_id, topic_id)
);
