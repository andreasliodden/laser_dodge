package no.uib.inf101.sample.view.viewable;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface ViewableProjectile extends ViewableEntity {
    ArrayList<Point2D> getTrail();
}
