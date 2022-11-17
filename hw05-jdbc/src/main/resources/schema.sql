DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS BOOK;

CREATE TABLE AUTHOR
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar(255) UNIQUE
);

CREATE TABLE GENRE
(
    ID   BIGSERIAL NOT NULL PRIMARY KEY,
    name varchar(255) UNIQUE
);

CREATE TABLE BOOK
(
    ID        BIGSERIAL NOT NULL PRIMARY KEY,
    NAME      VARCHAR(255),
    GENRE_ID  BIGINT REFERENCES GENRE,
    AUTHOR_ID BIGINT REFERENCES AUTHOR
);
