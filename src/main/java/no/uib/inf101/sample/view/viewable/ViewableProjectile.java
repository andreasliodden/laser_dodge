package no.uib.inf101.sample.view.viewable;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Interface that extends ViewableEntity and provides a method 
 * for retrieving the trail of a viewable projectile in the game.
 */
public interface ViewableProjectile extends ViewableEntity {
    /**
     * Gets a list of previous coordinates of the projectile,
     * which is used to form a trail behind it.
     */
    ArrayList<Point2D> getTrail();
}
