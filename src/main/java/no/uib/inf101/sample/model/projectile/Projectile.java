package no.uib.inf101.sample.model.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Projectile {
    private static final double POSITION_LIMIT = 1;

    private double y;
    private double x;
    private double velocityX;
    private double velocityY;
    private ArrayList<Point2D> trail = new ArrayList<>();
    
    private Projectile(double velocityX, double velocityY) {
        this.x = 0.5;
        this.y = 0.5;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    static Projectile createNewProjectile(double velocityX, double velocityY) {
        return new Projectile(velocityX, velocityY);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public ArrayList<Point2D> getTrail() {
        return this.trail;
    }

    public void move() {
        addPositionToTrail();
    
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        if(isLegalPosition(nextX, nextY)) {
            x = nextX;
            y = nextY;
        } else {
            if (nextX < 0) {
                velocityX = -velocityX;
                x = 0;
            } else if (nextX > POSITION_LIMIT) {
                velocityX = -velocityX;
                x = 1;
            } else if (nextY < 0) {
                velocityY = -velocityY;
                y = 0;
            } else {
                velocityY = -velocityY;
                y = 1;
            }
        }
    }

    private boolean isLegalPosition(double x, double y) {
        return (x >= 0 && x <= POSITION_LIMIT && y >= 0 && y <= POSITION_LIMIT);
    }

    private void addPositionToTrail() {
        trail.add(new Point2D.Double(x, y));

        if (trail.size() > 25) {
            trail.remove(0);
        }
    }
}
