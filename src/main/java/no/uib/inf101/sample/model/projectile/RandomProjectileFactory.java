package no.uib.inf101.sample.model.projectile;

import java.util.Random;

public class RandomProjectileFactory implements ProjectileFactory {

    @Override
    public Projectile getNext() {
        Random rand = new Random();
        double velocityX = rand.nextDouble() * 0.03 - 0.015;
        double velocityY = rand.nextDouble() * 0.03 - 0.015;

        return Projectile.createNewProjectile(velocityX, velocityY);
    }
    
}
