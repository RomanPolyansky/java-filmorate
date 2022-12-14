create table if not exists users
(
    id     int generated by default as identity primary key,
    email       varchar,
    login       varchar,
    name        varchar,
    birthday    Date
);

create table if not exists films
(
    id           int generated by default as identity primary key,
    name         varchar,
    description  varchar,
    release_date Date,
    duration int
);

create table if not exists mpa
(
    id           int generated by default as identity primary key,
    name         varchar
);

create table if not exists genres
(
    id           int generated by default as identity primary key,
    name         varchar
);

create table if not exists film_likes
(
    id           identity primary key,
    film_id int,
    like_user_id int,
    CONSTRAINT fk_film_id FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT fk_user_id_like FOREIGN KEY (like_user_id) REFERENCES users(id)
);

create table if not exists friendships
(
    id           identity primary key,
    user_id    int,
    friend_id int,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_friend_id FOREIGN KEY (friend_id) REFERENCES users(id)
);

create table if not exists film_mpa
(
    id           identity primary key,
    film_id    int,
    mpa_id int,
    CONSTRAINT fk_film_id_mpa FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT fk_mpa_id FOREIGN KEY (mpa_id) REFERENCES mpa(id)
    );

create table if not exists genres
(
    id         identity primary key,
    film_id    int,
    genre_id   int,
    CONSTRAINT fk_film_id_genre FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT fk_genre_id FOREIGN KEY (genre_id) REFERENCES genres(id)
    );

create table if not exists film_genre
(
    id           identity primary key,
    film_id    int,
    genre_id int,
    CONSTRAINT fk_film_id_genre FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT fk_genre_id_film FOREIGN KEY (genre_id) REFERENCES mpa(id)
);
