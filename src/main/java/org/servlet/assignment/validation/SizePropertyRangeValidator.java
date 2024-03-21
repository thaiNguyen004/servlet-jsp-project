package org.servlet.assignment.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntRangeValidator implements ConstraintValidator<IntRange, String> {

    private static String fieldName;

    @Override
    public void initialize(IntRange constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        if (str == null || str.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The " + fieldName + " must not be null").addConstraintViolation();
            return false;
        }
        switch (fieldName) {
            case "width":
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Width must be an integer between 45 and 60").addConstraintViolation();
                return isWidthValid(str);
            case "length":
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Length must be an integer between 63 and 76").addConstraintViolation();
                return isLengthValid(str);
            default:
                return false;
        }
    }

    public boolean isWidthValid(String widthStr) {
        try {
            int width = Integer.parseInt(widthStr);
            return width >= 45 && width <= 60;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isLengthValid(String lengthStr) {
        try {
            int length = Integer.parseInt(lengthStr);
            return length >= 63 && length <= 76;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
