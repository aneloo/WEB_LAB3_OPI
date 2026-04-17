package org.example.validation;

import org.example.Dot;

import java.util.ResourceBundle;

public final class ArgumentValidator {

    private ArgumentValidator() {

    }


    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

    public static void validate(Dot dot) {
        if (dot == null) {
            throw new IllegalArgumentException(BUNDLE.getString("error.dot.null"));
        }
        validateNumber(dot.getX(), "X");
        validateNumber(dot.getY(), "Y");
        validateNumber(dot.getR(), "R");
    }

    private static void validateNumber(double value, String fieldName) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(String.format(BUNDLE.getString("error.field.nan"), fieldName));
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException(String.format(BUNDLE.getString("error.field.infinite"), fieldName));
        }
    }
}
