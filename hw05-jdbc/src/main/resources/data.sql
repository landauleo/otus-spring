insert into author (id, `name`)
values (1, 'bodler');
insert into genre (id, `name`)
values (1, 'poetry');
insert into book (`name`, genre_id, author_id)
values ('poetry', 1, 1);
