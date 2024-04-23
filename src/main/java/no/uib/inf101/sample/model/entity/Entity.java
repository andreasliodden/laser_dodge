package no.uib.inf101.sample.model.entity;

import no.uib.inf101.sample.view.viewable.ViewableEntity;

public class Entity implements ViewableEntity {
    protected double x;
    protected double y;
    
    protected static final double MIN_LIMIT = 0;
    protected static final double MAX_LIMIT = 1;

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    protected boolean checkAndUpdatePosition(double nextX, double nextY) {
        if (isLegalPosition(nextX, nextY)) {
            x = nextX;
            y = nextY;
            return true;
        } else if (isLegalPosition(nextX, y)) {
            if (nextY > y) {
                y = 1;
            } else {
                y = 0;
            }
        } else {
            if (nextX > x) {
                x = 1;
            } else {
                x = 0;
            }
        }
        return false;
    }

    private boolean isLegalPosition(double x, double y) {
        return (x >= MIN_LIMIT && x <= MAX_LIMIT && y >= MIN_LIMIT && y <= MAX_LIMIT);
    }
}
