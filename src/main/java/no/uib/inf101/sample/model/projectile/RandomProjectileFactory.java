package no.uib.inf101.sample.model.projectile;

import java.util.Random;

public class RandomProjectileFactory implements ProjectileFactory {
    private Random random = new Random();
    private double speed, angle;

    @Override
    public Projectile getNextProjectile() {
        speed = 0.01;
        angle = random.nextDouble() * 2 * Math.PI;

        return Projectile.createNewProjectile(getVelocityX(speed, angle), getVelocityY(speed, angle));
    }

    @Override
    public GoldenApple getGoldenApple() {
        double x = Math.random();
        double y = Math.random();
        speed = 0.003;
        angle = random.nextDouble() * 2 * Math.PI;

        return GoldenApple.createGoldenApple(x, y, getVelocityX(speed, angle), getVelocityY(speed, angle));
    }

    private double getVelocityX(double speed, double angle) {
        return speed * Math.cos(angle);
    }

    private double getVelocityY(double speed, double angle) {
        return speed * Math.sin(angle);
    }
    
}
