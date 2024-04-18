package no.uib.inf101.sample.view.interfaces;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface ViewableProjectile extends ViewableEntity {
    ArrayList<Point2D> getTrail();
}
