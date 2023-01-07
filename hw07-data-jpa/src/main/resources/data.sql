insert into author (`name`)
values ('bodler');

insert into genre (`name`)
values ('poetry');

insert into book (`name`, genre_id, author_id)
values ('moshi moshi', 1, 1);

insert into comment (text, book_id)
values ('hmm, I feel like Bobby Fisher, always 4 moves ahead of', 1);
