package no.uib.inf101.sample.model.projectile;

import java.util.Random;

public class RandomProjectileFactory implements ProjectileFactory {
    private Random random = new Random();

    @Override
    public Projectile getNextProjectile() {
        double speed = 0.01;
        double angle = random.nextDouble() * 2 * Math.PI;

        return Projectile.createNewProjectile(getVelocityX(speed, angle), getVelocityY(speed, angle));
    }

    @Override
    public GoldenApple getGoldenApple() {
        double speed = 0.003;
        double x = Math.random();
        double y = Math.random();
        double angle = random.nextDouble() * 2 * Math.PI;

        return GoldenApple.createGoldenApple(x, y, getVelocityX(speed, angle), getVelocityY(speed, angle));
    }

    private double getVelocityX(double speed, double angle) {
        return speed * Math.cos(angle);
    }

    private double getVelocityY(double speed, double angle) {
        return speed * Math.sin(angle);
    }
    
}
