package no.uib.inf101.sample.view.image;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.model.enemy.EnemyState;
import no.uib.inf101.sample.view.Inf101Graphics;

/**
 * Enum that defines the different images representing the enemy.
 */
public enum EnemyImage {
    ANGRY_ONE("enemy/angry_1.png"),
    ANGRY_TWO("enemy/angry_2.png"),
    ANGRY_READY("enemy/angry_ready.png"),
    ANGRY_PAUSED("enemy/angry_paused.png"),
    HAPPY_ONE("enemy/happy_1.png"),
    HAPPY_TWO("enemy/happy_2.png"),
    HAPPY_READY("enemy/happy_ready.png"),
    HAPPY_PAUSED("enemy/happy_paused.png");

    private final BufferedImage image;

    private EnemyImage(String imagePath) {
        this.image = Inf101Graphics.loadImageFromResources(imagePath);
    }

    /**
     * Gets an image that represents the given enemy state.
     */
    public static BufferedImage get(EnemyState enemyState) {
        EnemyImage enemy = EnemyImage.valueOf(enemyState.name());
        return enemy.image;
    }
}
