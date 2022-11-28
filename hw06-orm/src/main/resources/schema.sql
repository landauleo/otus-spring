DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS book;

CREATE TABLE author
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar(255) UNIQUE
);

CREATE TABLE genre
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar(255) UNIQUE
);

CREATE TABLE book
(
    id        bigserial NOT NULL PRIMARY KEY,
    name      varchar(255),
    genre_id  bigint REFERENCES genre,
    author_id bigint REFERENCES author
);

CREATE TABLE comment
(
    id      bigserial NOT NULL PRIMARY KEY,
    text    varchar(255),
    book_id bigint REFERENCES book on delete cascade
);