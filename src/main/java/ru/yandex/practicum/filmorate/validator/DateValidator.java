package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value.isBefore(LocalDate.of(1895, 12, 28))) return false;
        return true;
    }
}

//  28 декабря 1895 года;
