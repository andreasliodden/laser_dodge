package no.uib.inf101.sample.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestProjectile {
    private Projectile projectile;

    @BeforeEach
    private void initProjectile() {
        projectile = Projectile.createNewProjectile(0.1, 0.1);
    }

    @Test
    public void positionUpdateAfterMove() {
        double currentX = 0.5;
        double currentY = 0.5;
        projectile.move();

        assertNotEquals(currentX, projectile.getX());
        assertNotEquals(currentY, projectile.getY());

        currentX = projectile.getX();
        currentY = projectile.getY();

        projectile.move();

        assertNotEquals(currentX, projectile.getX());
        assertNotEquals(currentY, projectile.getY());
    }

    @Test
    public void maxTrailSize() {
        int maxSize = 20;
        for (int i = 0; i < maxSize; i++) {
            projectile.move();
        }
        assertEquals(maxSize, projectile.getTrail().size());

        projectile.move();
        assertEquals(maxSize, projectile.getTrail().size());
    }

    @Test
    public void projectileWallBounceX() {
        double velocityX = 0.1;
        projectile = Projectile.createNewProjectile(velocityX, 0);

        while (projectile.getX() < 1) {
            projectile.move();
        }

        projectile.move();
        assertEquals(0.9, projectile.getX());

        while (projectile.getX() > 0) {
            projectile.move();
        }

        projectile.move();
        assertEquals(0.1, projectile.getX());
    }

    @Test
    public void projectileWallBounceY() {
        double velocityY = 0.1;
        projectile = Projectile.createNewProjectile(0, velocityY);

        while (projectile.getY() < 1) {
            projectile.move();
        }

        projectile.move();
        assertEquals(0.9, projectile.getY());

        while (projectile.getY() > 0) {
            projectile.move();
        }

        projectile.move();
        assertEquals(0.1, projectile.getY());
    }
}
