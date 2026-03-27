package org.example.validation;

import org.example.Dot;

public final class ArgumentValidator {

    private ArgumentValidator() {

    }


    public static void validate(Dot dot) {
        if (dot == null) {
            throw new IllegalArgumentException("Точка не может быть null.");
        }
        validateNumber(dot.getX(), "X");
        validateNumber(dot.getY(), "Y");
        validateNumber(dot.getR(), "R");
    }


    private static void validateNumber(double value, String fieldName) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Поле " + fieldName + " должно быть числом, а не NaN.");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Поле " + fieldName + " должно быть конечным числом.");
        }
    }

    /*
    public static double parseDouble(String raw, String fieldName) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new IllegalArgumentException("Поле " + fieldName + " не может быть пустым.");
        }

        String normalized = raw.trim().replace(',', '.');

        try {
            double value = Double.parseDouble(normalized);

            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Поле " + fieldName + " должно быть числом, а не NaN.");
            }
            if (Double.isInfinite(value)) {
                throw new IllegalArgumentException("Поле " + fieldName + " должно быть конечным числом.");
            }

            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Поле " + fieldName + " должно содержать число.", e);
        }
    }


    public static void fillDotFromStrings(Dot dot, String rawX, String rawY, String rawR) {
        if (dot == null) {
            throw new IllegalArgumentException("Точка не может быть null.");
        }

        double x = parseDouble(rawX, "X");
        double y = parseDouble(rawY, "Y");
        double r = parseDouble(rawR, "R");

        dot.setX((float) x);
        dot.setY((float) y);
        dot.setR((float) r);
    }*/
}
