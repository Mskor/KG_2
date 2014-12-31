package model;

import javafx.geometry.Point2D;

/**
 * Created by oyakov on 31.12.2014.
 */
public class ConjugationDesc {
    public Point2D con_center;
    public Double start_angle, end_angle;

    ConjugationDesc(Point2D cc, Double sa, Double ea) {
        con_center = cc;
        start_angle = sa;
        end_angle = ea;
    }
}
