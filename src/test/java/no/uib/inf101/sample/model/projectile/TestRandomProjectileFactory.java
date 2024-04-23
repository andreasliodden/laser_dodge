package no.uib.inf101.sample.model.projectile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestRandomProjectileFactory {
    private ProjectileFactory factory;

    @BeforeEach
    private void initFactory() {
        factory = new RandomProjectileFactory();
    }

    @Test
    public void getInitialProjectile() {
        Projectile projectile = factory.getNextProjectile();
        double startPos = 0.5;
        assertEquals(startPos, projectile.getX());
        assertEquals(startPos, projectile.getY());
        assertEquals(0, projectile.getTrail().size());
        
    }

    @Test
    public void getInitialGapple() {
        GoldenApple gapple1 = factory.getGoldenApple();
        GoldenApple gapple2 = factory.getGoldenApple();

        assertNotEquals(gapple1.getX(), gapple2.getX());
        assertNotEquals(gapple1.getY(), gapple2.getY());
    }
}
