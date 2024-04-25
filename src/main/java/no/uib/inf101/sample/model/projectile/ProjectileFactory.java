package no.uib.inf101.sample.model.projectile;

public interface ProjectileFactory {
    /**
     * Generates a new projectile to be added to the game.
     * 
     * @return a new projectile
     * 
     */
    Projectile getNextProjectile();

    /**
     * Generates a new golden apple to be added to the game.
     * 
     * @return a new golden apple
     */
    GoldenApple getGoldenApple();
}
