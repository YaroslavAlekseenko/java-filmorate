package ru.yandex.practicum.filmorate.Exception;

public class UserValidationException extends RuntimeException{
    public UserValidationException(final String message) {
        super(message);
    }
}
