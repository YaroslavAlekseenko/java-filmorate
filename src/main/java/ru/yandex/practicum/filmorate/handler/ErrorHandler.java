package ru.yandex.practicum.filmorate.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.exception.StorageException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final FilmValidationException e) {
        log.info("400 {}", "Ошибка валидации. Дата релиза не должна быть раньше 28 декабря 1895!");
        return new ErrorResponse(
                e.getMessage(), "Ошибка валидации. Дата релиза не должна быть раньше 28 декабря 1895!"
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final StorageException e) {
        log.info("404 {}", "Искомый объект не найден.");
        return new ErrorResponse(
                e.getMessage(), "Искомый объект не найден."
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(final Throwable e) {
        log.info("500 {}", "Произошла непредвиденная ошибка.");
        return new ErrorResponse(
                e.getMessage(), "Произошла непредвиденная ошибка."
        );
    }
}