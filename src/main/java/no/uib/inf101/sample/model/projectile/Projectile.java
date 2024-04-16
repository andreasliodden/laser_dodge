package no.uib.inf101.sample.model.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import no.uib.inf101.sample.model.Entity;

public class Projectile extends Entity {
    private static final double MIN_POSITION = 0;
    private static final double MAX_POSITION = 1;

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

    public ArrayList<Point2D> getTrail() {
        return this.trail;
    }

    public void move() {
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        addPositionToTrail();
        updatePosition(nextX, nextY);
    }

    private void addPositionToTrail() {
        trail.add(new Point2D.Double(x, y));

        if (trail.size() > 25) {
            trail.remove(0);
        }
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
