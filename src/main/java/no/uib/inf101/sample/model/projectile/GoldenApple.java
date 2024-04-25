package no.uib.inf101.sample.model.projectile;

import no.uib.inf101.sample.model.entity.RandomMovingEntity;

/**
 * Represents the golden apple power-up spawning in the game.
 * It extends the RandomMovingEntity class,
 * which means it has a position and velocity, and moves in a random direction.
 */
public class GoldenApple extends RandomMovingEntity {
    private GoldenApple(double x, double y, double velocityX, double velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    static GoldenApple createGoldenApple(double x, double y, double velocityX, double velocityY) {
        return new GoldenApple(x, y, velocityX, velocityY);
    }
}
