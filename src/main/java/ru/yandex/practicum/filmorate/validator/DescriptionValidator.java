package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DescriptionValidator implements ConstraintValidator<ValidDesc, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() > 200) return false;
        return true;
    }
}

//  28 декабря 1895 года;
