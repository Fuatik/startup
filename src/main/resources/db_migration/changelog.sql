--liquibase formatted sql

--changeset gkislin:1_init_schema
drop table if exists USER_ROLE;
drop table if exists CONTACT;
drop table if exists USER_DATA;
drop table if exists REF;
drop table if exists USERS;

create table USERS
(
    ID         serial primary key,
    EMAIL      varchar(64) not null
        constraint UK_USERS_EMAIL unique,
    FIRST_NAME varchar(32) not null,
    LAST_NAME  varchar(32),
    AVATAR_URL varchar(256),
    STARTPOINT timestamp   not null default localtimestamp not null,
    ENDPOINT   timestamp
);

create table REF
(
    ID          serial primary key,
    CODE        varchar(32)                      not null
        constraint UK_REF_CODE unique,
    TYPE varchar(32)                      not null,
    REF_ORDER   integer                          not null,
    AUX         varchar(2048),
    STARTPOINT  timestamp default localtimestamp not null,
    ENDPOINT    timestamp
);

create table USER_DATA
(
    USER_ID INTEGER not null primary key,
    constraint FK_USER_DATA_USER foreign key (USER_ID) references USERS (ID) on delete cascade
);

create table CONTACT
(
    USER_ID integer      not null,
    CODE    varchar(32)  not null,
    C_VALUE varchar(256) not null,
    primary key (USER_ID, CODE),
    constraint FK_CONTACT_USER foreign key (USER_ID) references USER_DATA (USER_ID) on delete cascade,
    constraint FK_CONTACT_CODE foreign key (CODE) references REF (CODE)
);

create table USER_ROLE
(
    USER_ID integer      not null,
    ROLE    varchar(255) not null,
    constraint UK_USER_ROLE unique (USER_ID, ROLE),
    constraint FK_USER_ROLE foreign key (USER_ID) references USERS (ID) on delete cascade
);

--changeset gkislin:2_populate_data
insert into users (EMAIL, FIRST_NAME, LAST_NAME)
values ('user@yandex.ru', 'User', 'UserLastName'),
       ('admin@gmail.com', 'Admin', 'AdminLastName'),
       ('guest@gmail.com', 'Guest', 'GuestLastName');

insert into USER_ROLE (ROLE, USER_ID)
values ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

insert into REF (CODE, TYPE, REF_ORDER, ENDPOINT)
values ('C_TG', 'C', 10, null), --telegram
       ('C_WA', 'C', 20, null), --whatsapp
       ('C_PH', 'C', 30, null), --phone
       ('C_SK', 'C', 40, now()); --skype