package no.uib.inf101.sample.model.projectile;

import java.util.Random;

/**
 * Generates random projectiles in the game.
 * Implements the ProjectileFactory interface,
 * which provides methods to generate new projectiles.
 */

public class RandomProjectileFactory implements ProjectileFactory {
    private static final double PROJECTILE_SPEED = 0.01;
    private static final double GAPPLE_SPEED = 0.003;

    private Random random = new Random();

    @Override
    public Projectile getNextProjectile() {
        double angle = random.nextDouble() * 2 * Math.PI;

        return Projectile.createNewProjectile(getVelocityX(PROJECTILE_SPEED, angle),
                getVelocityY(PROJECTILE_SPEED, angle));
    }

    @Override
    public GoldenApple getGoldenApple() {
        double x = Math.random();
        double y = Math.random();
        double angle = random.nextDouble() * 2 * Math.PI;

        return GoldenApple.createGoldenApple(x, y, getVelocityX(GAPPLE_SPEED, angle),
                getVelocityY(GAPPLE_SPEED, angle));
    }

    private double getVelocityX(double speed, double angle) {
        return speed * Math.cos(angle);
    }

    private double getVelocityY(double speed, double angle) {
        return speed * Math.sin(angle);
    }

}
