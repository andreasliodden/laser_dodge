package no.uib.inf101.sample.model.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import no.uib.inf101.sample.view.interfaces.ViewableProjectile;

public class Projectile extends RandomMovingEntity implements ViewableProjectile {
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

    @Override
    public void move() {
        super.move();
        addPositionToTrail();
    }

    private void addPositionToTrail() {
        trail.add(new Point2D.Double(x, y));
    
        if (trail.size() > 25) {
            trail.remove(0);
        }
    }

    @Override
    public ArrayList<Point2D> getTrail() {
        return this.trail;
    }
}

