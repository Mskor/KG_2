package model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

/**
 * Created by oyakov on 30.12.2014.
 */
public class ConjugationManager {
    public static Point2D calculate(Circle c1, Circle c2, Double r) {

        Circle greater, lesser;

        if (c1.getRadius() == c2.getRadius()) {
            return null;
        } else if (c1.getRadius() > c2.getRadius()) {
            greater = c1;
            lesser = c2;
        } else {
            greater = c2;
            lesser = c1;
        }

        Point2D A = new Point2D(greater.getCenterX(), greater.getCenterY());
        Point2D B = new Point2D(lesser.getCenterX(), lesser.getCenterY());

        Double b = greater.getRadius() - r;
        Double a = lesser.getRadius() + r;

        // Known side as vector
        Point2D V = B.subtract(A);

        // Module of V
        Double c = Math.sqrt(V.getX() * V.getX() + V.getY() * V.getY());
        Double cosV = V.getX() / c;
        Double sinV = V.getY() / c;

        Double cosN = sinV;
        Double sinN = -cosV;

        // Cosine of alpha, where alpha is angle between known side and contiguous side
        Double d = (b * b + c * c - a * a) / (2 * b * c);

        Point2D X = new Point2D(b * d * cosV, b * d * sinV);
        Point2D Y = new Point2D(b * Math.sqrt(1 - d * d) * cosN, b * Math.sqrt(1 - d * d) * sinN);

        Point2D C = A.add(X).add(Y);

        return C;
    }

    public static Point2D calculateInverse(Circle c1, Circle c2, Double r) {

        Circle greater, lesser;

        if (c1.getRadius() == c2.getRadius()) {
            return null;
        } else if (c1.getRadius() > c2.getRadius()) {
            greater = c1;
            lesser = c2;
        } else {
            greater = c2;
            lesser = c1;
        }

        Point2D A = new Point2D(greater.getCenterX(), greater.getCenterY());
        Point2D B = new Point2D(lesser.getCenterX(), lesser.getCenterY());

        Double b = greater.getRadius() - r;
        b = -b;
        Double a = lesser.getRadius() + r;

        // Known side as vector
        Point2D V = B.subtract(A);

        // Module of V
        Double c = Math.sqrt(V.getX() * V.getX() + V.getY() * V.getY());
        Double cosV = V.getX() / c;
        Double sinV = V.getY() / c;

        Double cosN = sinV;
        Double sinN = -cosV;

        // Cosine of alpha, where alpha is angle between known side and contiguous side
        Double d = (b * b + c * c - a * a) / (2 * b * c);

        Point2D X = new Point2D(b * d * cosV, b * d * sinV);
        Point2D Y = new Point2D(b * Math.sqrt(1 - d * d) * cosN, b * Math.sqrt(1 - d * d) * sinN);

        Point2D C = A.add(X).add(Y);

        return C;
    }
}
