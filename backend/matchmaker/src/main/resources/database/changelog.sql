--liquibase formatted sql

--changeset vityaman:initialize
CREATE SCHEMA matchmaker;

CREATE TABLE matchmaker.person (
    account_id      integer     PRIMARY KEY,
    version         integer     NOT NULL
);

CREATE TYPE matchmaker.attitude_kind
AS ENUM ('like', 'skip');

CREATE TABLE matchmaker.attitude (
    target_id       integer                     REFERENCES matchmaker.person (account_id),
    source_id       integer                     REFERENCES matchmaker.person (account_id),
    kind            matchmaker.attitude_kind    NOT NULL,
    PRIMARY KEY (target_id, source_id)
);
