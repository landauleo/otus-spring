insert into author (id, `name`)
values (1, 'bodler');

insert into genre (id, `name`)
values (1, 'poetry');

insert into book (id, `name`, genre_id, author_id)
values (1, 'kitchen', 1, 1);
