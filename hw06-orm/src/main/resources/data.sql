INSERT INTO author ( id, `NAME` )
VALUES ( 1, 'bodler' );
INSERT INTO genre ( id, `NAME` )
VALUES ( 1, 'poetry' );
INSERT INTO book ( `NAME`, genre_id, author_id )
VALUES ( 'poetry', 1, 1 );
