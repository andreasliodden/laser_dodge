package no.uib.inf101.sample.model.projectile;

public interface ProjectileFactory {
    Projectile getNextProjectile();
    
    GoldenApple getGoldenApple();
}
