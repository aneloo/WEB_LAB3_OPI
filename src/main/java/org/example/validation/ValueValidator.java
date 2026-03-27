package org.example.validation;

import org.example.Dot;

public final class ValueValidator {

    private ValueValidator() {

    }

    public static void validate(Dot dot) {
        if (dot == null) {
            throw new IllegalArgumentException("Точка не может быть null.");
        }

        validateX(dot.getX());
        validateY(dot.getY());
        validateR(dot.getR());
    }

    private static void validateX(double x) {
        checkRange(x, -2.0, 2.0, "X");
    }

    private static void validateY(double y) {
        checkRange(y, -5.0, 5.0, "Y");
    }

    private static void validateR(double r) {
        checkRange(r, 1.0, 4.0, "R");
    }

    private static void checkRange(double value, double min, double max, String fieldName) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(
                    "Поле " + fieldName + " должно быть в диапазоне [" + min + "; " + max + "]. " +
                            "Получено: " + value
            );
        }
    }
}
