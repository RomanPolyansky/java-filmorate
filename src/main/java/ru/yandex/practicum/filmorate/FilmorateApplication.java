package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		Film film = Film.builder().
				id(1).name("asd").
				description("asdf").
				releaseDate(Date.valueOf("1967-03-25")).
				duration(Duration.ofHours(2)).build();
		System.out.println(film);
		SpringApplication.run(FilmorateApplication.class, args);
	}
}
