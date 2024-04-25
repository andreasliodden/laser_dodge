package no.uib.inf101.sample.model.projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import no.uib.inf101.sample.model.entity.RandomMovingEntity;
import no.uib.inf101.sample.view.viewable.ViewableProjectile;

/**
 * Represents the projectiles shot from the vicious enemy.
 * It extends the RandomMovingEntity class,
 * which means it has a position and velocity, and it can move in random
 * directions.
 */
public class Projectile extends RandomMovingEntity implements ViewableProjectile {
    private static final int MAX_TRAIL_SIZE = 20;
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
        addPositionToTrail();
        super.move();
    }

    private void addPositionToTrail() {
        trail.add(new Point2D.Double(x, y));

        if (trail.size() > MAX_TRAIL_SIZE) {
            trail.remove(0);
        }
    }

    @Override
    public ArrayList<Point2D> getTrail() {
        return new ArrayList<>(this.trail);
    }
}
