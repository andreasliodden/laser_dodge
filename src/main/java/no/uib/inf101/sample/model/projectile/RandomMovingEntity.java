package no.uib.inf101.sample.model.projectile;

import no.uib.inf101.sample.model.Entity;

public class RandomMovingEntity extends Entity {
    protected double velocityX;
    protected double velocityY;
    
    public void move() {
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        checkAndUpdatePosition(nextX, nextY);
    }

    @Override
    public boolean checkAndUpdatePosition(double nextX, double nextY) {
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
