package no.uib.inf101.sample.model.entity;

/**
 * Represents a random moving entity, 
 * which moves in a random x and y direction.
 * Is an extension of the entity class.
 */

public class RandomMovingEntity extends Entity {
    protected double velocityX;
    protected double velocityY;
    
    /**
     * Moves the entity by updating its x- and 
     * y-coordinates with its velocity in both directions.
     * Validates the move with the checkAndUpdatePosition() 
     * method inherited from the entity class.
     */
    public void move() {
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        checkAndUpdatePosition(nextX, nextY);
    }

    @Override
    protected boolean checkAndUpdatePosition(double nextX, double nextY) {
        if(super.checkAndUpdatePosition(nextX, nextY)) {
            return true;
        }
        else {
            if (nextX < MIN_LIMIT) {
                velocityX = -velocityX;
            } else if (nextX > MAX_LIMIT) {
                velocityX = -velocityX;
            } else if (nextY < MIN_LIMIT) {
                velocityY = -velocityY;
            } else if (nextY > MAX_LIMIT) {
                velocityY = -velocityY;
            }
            
            return false;
        }
    }
}
