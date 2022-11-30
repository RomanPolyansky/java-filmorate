DELETE FROM FILM_LIKES;
ALTER TABLE FILM_LIKES ALTER COLUMN ID RESTART WITH 1;
DELETE FROM FILM_MPA CASCADE;
ALTER TABLE FILM_MPA ALTER COLUMN ID RESTART WITH 1;
DELETE FROM FILM_GENRE;
ALTER TABLE FILM_GENRE ALTER COLUMN ID RESTART WITH 1;
DELETE FROM FRIENDSHIPS;
ALTER TABLE FRIENDSHIPS ALTER COLUMN ID RESTART WITH 1;
DELETE FROM films;
ALTER TABLE films ALTER COLUMN ID RESTART WITH 1;
DELETE FROM users;
ALTER TABLE users ALTER COLUMN ID RESTART WITH 1;
DELETE FROM genres;
ALTER TABLE genres ALTER COLUMN ID RESTART WITH 1;
DELETE FROM mpa;
ALTER TABLE mpa ALTER COLUMN ID RESTART WITH 1;


INSERT into genres (name) values ('Комедия');
INSERT into genres (name) values ('Драма');
INSERT into genres (name) values ('Мультфильм');
INSERT into genres (name) values ('Триллер');
INSERT into genres (name) values ('Документальный');
INSERT into genres (name) values ('Боевик');

INSERT into mpa (name) values ('G');
INSERT into mpa (name) values ('PG');
INSERT into mpa (name) values ('PG-13');
INSERT into mpa (name) values ('R');
INSERT into mpa (name) values ('NC-17');
