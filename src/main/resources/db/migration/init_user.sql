--liquibase formatted sql

--changeset yushin:1
create table "user"
(
    id      int8 primary key not null,
    login   varchar not null unique,
    password   varchar,
    name    varchar
);

create index user_login_index on "user" (login);