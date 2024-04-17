package no.uib.inf101.sample.model.projectile;

import no.uib.inf101.sample.model.Entity;

public class RandomMovingEntity extends Entity {
    protected static final double MIN_POSITION = 0;
    protected static final double MAX_POSITION = 1;

    protected double velocityX;
    protected double velocityY;
    
    public void move() {
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        updatePosition(nextX, nextY);
    }

    @Override
    protected boolean updatePosition(double nextX, double nextY) {
        if(super.updatePosition(nextX, nextY)) {
            return true;
        }
        else {
            if (nextX < MIN_POSITION) {
                velocityX = -velocityX;
            } else if (nextX > MAX_POSITION) {
                velocityX = -velocityX;
            } else if (nextY < MIN_POSITION) {
                velocityY = -velocityY;
            } else if (nextY > MAX_POSITION) {
                velocityY = -velocityY;
            }
        return false;
        }
    }


}
