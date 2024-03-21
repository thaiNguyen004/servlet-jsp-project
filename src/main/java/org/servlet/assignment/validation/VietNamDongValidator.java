package org.servlet.assignment.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VietNamDongValidator implements ConstraintValidator<VietNamDong, String> {
    private static String fieldName;
    private static int min;
    private static int max;
    private static String message;

    @Override
    public void initialize(VietNamDong constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        message = "The " + fieldName + " must be an integer between " + min + " and " + max + " and la boi cua 1000";
    }

    @Override
    public boolean isValid(String price, ConstraintValidatorContext context) {
        if (price == null || price.isEmpty()) {
            return false;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return isVietNamDongValid(price);
        }
    }

    public boolean isVietNamDongValid(String widthStr) {
        try {
            int number = Integer.parseInt(widthStr);
            return (number >= min && number <= max) && number % 1000 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
