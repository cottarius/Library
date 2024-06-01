drop table if exists book;
drop table if exists reader;
drop table if exists issue;

create table if not exists reader
(
    id         bigserial primary key,
    first_name varchar(128),
    last_name  varchar(128)
    );

create table if not exists book
(
    id    bigserial primary key,
    title varchar(128)
    );

create table IF NOT EXISTS issue
(
    id          bigserial primary key,
    reader_id   bigint references reader (id),
    book_id     bigint references book (id),
    issued_at   DATE,
    returned_at Date
    );

create table if not exists person
(
    id       bigserial primary key,
    login    varchar(128) not null unique,
    password varchar(128),
    role     varchar(128) not null
    );

INSERT INTO reader (first_name, last_name)
VALUES ('Евгений', 'Миронов'),
       ('Владимир', 'Машков'),
       ('Константин', 'Хабенский');

INSERT INTO book (title)
VALUES ('Поднятая Целина'),
       ('Тихий Дон'),
       ('Мастер и Маргарита'),
       ('Молодая Гвардия');

insert into person (login, password, role)
VALUES ('admin', 'admin', 'admin'),
       ('user', 'user', 'users');