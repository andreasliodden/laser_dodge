package no.uib.inf101.sample.view.image;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.enemy.EnemyState;
import no.uib.inf101.sample.view.Inf101Graphics;

public class TestEnemyImage extends ImageTest {
    private static final BufferedImage ENEMY_ANGRY_ONE = Inf101Graphics.loadImageFromResources("enemy/angry_1.png");
    private static final BufferedImage ENEMY_HAPPY_ONE = Inf101Graphics.loadImageFromResources("enemy/happy_1.png");
    private static final BufferedImage ENEMY_HAPPY_PAUSED = Inf101Graphics.loadImageFromResources("enemy/happy_paused.png");
    
    private EnemyState enemyState;

    @Test
    public void getImage() {
        enemyState = EnemyState.ANGRY_ONE;
        assert(imagesAreEqual(ENEMY_ANGRY_ONE, EnemyImage.get(enemyState)));

        enemyState = EnemyState.HAPPY_ONE;
        assert(imagesAreEqual(ENEMY_HAPPY_ONE, EnemyImage.get(enemyState)));

        enemyState = EnemyState.HAPPY_PAUSED;
        assert(imagesAreEqual(ENEMY_HAPPY_PAUSED, EnemyImage.get(enemyState)));

        enemyState = EnemyState.HAPPY_READY;
        assertFalse(imagesAreEqual(ENEMY_HAPPY_PAUSED, EnemyImage.get(enemyState)));
    }
}
