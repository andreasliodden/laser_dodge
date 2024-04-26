package no.uib.inf101.sample.view.viewable;

/**
 * Interface that defines methods for retrieving the 
 * current x- and y-coordinates of a viewable entity in the game.
 */
public interface ViewableEntity {
    /**
     * Gets the current x-position of the entity.
     * 
     * @return the current x-position
     */
    double getX();

    /**
     * Gets the current y-position of the entity.
     * 
     * @return the current y-position
     */
    double getY();
}
