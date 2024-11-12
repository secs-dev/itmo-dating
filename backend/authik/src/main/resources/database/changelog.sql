--liquibase formatted sql

--changeset vityaman:initialize
CREATE SCHEMA authik;

--changeset vityaman:account
CREATE SEQUENCE authik.account_id_seq AS integer START 1;

CREATE TABLE authik.telegram_account (
    account_id      integer     PRIMARY KEY DEFAULT nextval('authik.account_id_seq'),
    telegram_id     bigint      NOT NULL UNIQUE,
    creation_moment timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);
