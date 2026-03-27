package org.example.validation;


import org.example.Dot;

public class AreaCheck {

    public static boolean isHit(Dot dot) {
        double x = dot.getX();
        double y = dot.getY();
        double r = dot.getR();

        return (isSectorZone(x, y, r)
                || isTriangleZone(x, y, r)
                || isRectangleZone(x, y, r));
    }

    private static boolean isRectangleZone(double x, double y, double r) {
        return (x <= 0) && (x >= -r) && (y >= 0) && (y <= r);
    }

    private static boolean isSectorZone(double x, double y, double r) {
        return (x >= 0) && (y <= 0) && (x * x + y * y <= r * r);
    }

    private static boolean isTriangleZone(double x, double y, double r) {

        double x1 = -r / 2, x2 = 0, x3 = 0;
        double y1 = 0,      y2 = 0, y3 = -r / 2;

        double a1 = (x1 - x) * (y2 - y1) - (x2 - x1) * (y1 - y);
        double a2 = (x2 - x) * (y3 - y2) - (x3 - x2) * (y2 - y);
        double a3 = (x3 - x) * (y1 - y3) - (x1 - x3) * (y3 - y);

        return (x <= 0) && (y <= 0)
                && ((a1 >= 0 && a2 >= 0 && a3 >= 0)
                || (a1 <= 0 && a2 <= 0 && a3 <= 0));
    }
}
