insert into author (id, `name`)
values (1, 'yoshimoto banana');

insert into genre (id, `name`)
values (1, 'poem');

insert into book (`name`, genre_id, author_id)
values ('lake', 1, 1); --чтобы руками не задавать, с какого номера начинать sequence в тестах, просто не проставляю здесь айдишник и тогда автоинкремент не спотыкается на тестах

