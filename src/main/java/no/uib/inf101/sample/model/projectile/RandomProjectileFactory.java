package no.uib.inf101.sample.model.projectile;

import java.util.Random;

public class RandomProjectileFactory implements ProjectileFactory {

    @Override
    public Projectile getNext() {
        Random rand = new Random();
        double speed = 0.01;
        double angle = rand.nextDouble() * 2 * Math.PI;
        double velocityX = speed * Math.cos(angle);
        double velocityY = speed * Math.sin(angle);

        return Projectile.createNewProjectile(velocityX, velocityY);
    }
    
}
