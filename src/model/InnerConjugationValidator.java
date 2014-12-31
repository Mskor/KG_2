package model;

import javafx.scene.shape.Circle;

/**
 * Created by oyakov on 30.12.2014.
 */
public class InnerConjugationValidator {
    public static boolean validate(Circle first, Circle second, Double conRadius) {

        Double xOfft = second.getCenterX() - first.getCenterX();
        Double yOfft = second.getCenterY() - first.getCenterY();
        Double connLength = Math.sqrt(xOfft * xOfft + yOfft * yOfft);

        Circle greater, lesser;

        if (first.getRadius() == second.getRadius()) {
            return false;
        } else if (first.getRadius() > second.getRadius()) {
            greater = first;
            lesser = second;
        } else {
            greater = second;
            lesser = first;
        }

        return !(connLength > greater.getRadius() + lesser.getRadius() ||
                connLength + lesser.getRadius() < greater.getRadius() - 2 * conRadius ||
                greater.getRadius() + connLength - lesser.getRadius() < conRadius * 2
        );
    }
}
