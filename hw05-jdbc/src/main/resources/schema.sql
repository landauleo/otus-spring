DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS BOOK;

CREATE TABLE AUTHOR(ID BIGINT PRIMARY KEY, NAME VARCHAR(255));

-- CREATE TABLE GENRE(ID BIGINT PRIMARY KEY, NAME VARCHAR(255));
--
-- CREATE TABLE BOOK(ID BIGINT PRIMARY KEY, NAME VARCHAR(255), GENRE_ID BIGINT REFERENCES GENRE, AUTHOR_ID BIGINT REFERENCES AUTHOR);
