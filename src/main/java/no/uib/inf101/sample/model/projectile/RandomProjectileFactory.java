package no.uib.inf101.sample.model.projectile;

import java.util.Random;

public class RandomProjectileFactory implements ProjectileFactory {

    @Override
    public Projectile getNext() {
        Random rand = new Random();
        double velocityX = rand.nextDouble() * 0.02 - 0.01;
        double velocityY = rand.nextDouble() * 0.02 - 0.01;

        return Projectile.createNewProjectile(velocityX, velocityY);
    }
    
}
