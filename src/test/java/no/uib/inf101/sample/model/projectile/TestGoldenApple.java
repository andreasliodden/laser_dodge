package no.uib.inf101.sample.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGoldenApple {
    private GoldenApple gapple;

    @BeforeEach
    private void initProjectile() {
        gapple = GoldenApple.createGoldenApple(0.5, 0.5, 0.1, 0.1);
    }

    @Test
    public void positionUpdateAfterMove() {
        double currentX = gapple.getX();
        double currentY = gapple.getY();

        gapple.move();

        assertNotEquals(currentX, gapple.getX());
        assertNotEquals(currentY, gapple.getY());

        currentX = gapple.getX();
        currentY = gapple.getY();

        gapple.move();

        assertNotEquals(currentX, gapple.getX());
        assertNotEquals(currentY, gapple.getY());
    }

    @Test
    public void gappleWallBounceX() {
        double velocityX = 0.1;
        gapple = GoldenApple.createGoldenApple(0.5, 0.5, velocityX, 0);

        while(gapple.getX() < 1) {
            gapple.move();
        }

        gapple.move();
        assertEquals(1 - velocityX, gapple.getX());

        while (gapple.getX() > 0) {
            gapple.move();
        }

        gapple.move();
        assertEquals(velocityX, gapple.getX());
    }

    @Test
    public void gappleWallBounceY() {
        double velocityY = 0.1;
        gapple = GoldenApple.createGoldenApple(0.5, 0.5, 0, velocityY);

        while(gapple.getY() < 1) {
            gapple.move();
        }

        gapple.move();
        assertEquals(1 - velocityY, gapple.getY());

        while (gapple.getY() > 0) {
            gapple.move();
        }

        gapple.move();
        assertEquals(velocityY, gapple.getY());
    }

}
