package ru.yandex.practicum.filmorate.Exception;

public class FilmValidationException extends RuntimeException{
    public FilmValidationException(final String message) {
        super(message);
    }
}
