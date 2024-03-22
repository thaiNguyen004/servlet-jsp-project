package org.servlet.assignment.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntRangeValidator implements ConstraintValidator<IntRange, String> {

    private static String fieldName;
    private static int min;
    private static int max;
    private static String message;

    @Override
    public void initialize(IntRange constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        message = "The " + fieldName + " must be an integer between " + min + " and " + max;
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        if (str == null || str.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The " + fieldName + " must not be null").addConstraintViolation();
            return false;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return isNumberValid(str);
        }
    }

    public boolean isNumberValid(String widthStr) {
        try {
            int number = Integer.parseInt(widthStr);
            return number >= min && number <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
