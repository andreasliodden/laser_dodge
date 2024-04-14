package no.uib.inf101.sample.model.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Projectile {
    private static final double MAX_LIMIT = 1;

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
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        trail.add(new Point2D.Double(x, y));

        if (trail.size() > 25) {
            trail.remove(0);
        }

        if(isLegalPosition(nextX, nextY)) {
            x = nextX;
            y = nextY;
        } else {
            if (nextX < 0 || nextX > MAX_LIMIT) {
                velocityX = -velocityX;
            } else {
                velocityY = -velocityY;
            }
        }
    }

    private boolean isLegalPosition(double x, double y) {
        return (x >= 0 && x <= MAX_LIMIT && y >= 0 && y <= MAX_LIMIT);
    }
}
