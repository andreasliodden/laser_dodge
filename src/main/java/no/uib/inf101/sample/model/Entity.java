package no.uib.inf101.sample.model;

public class Entity {
    protected double x;
    protected double y;

    private static final double MIN_LIMIT = 0;
    private static final double MAX_LIMIT = 1;

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }

    protected boolean updatePosition(double nextX, double nextY) {
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
