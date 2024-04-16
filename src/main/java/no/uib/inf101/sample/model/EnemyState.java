package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public enum EnemyState {
    ANGRY_ONE("enemy/angry_1.png"),
    ANGRY_TWO("enemy/angry_2.png"),
    ANGRY_READY("enemy/angry_ready.png"),
    FRIENDLY_ONE("enemy/friendly_1.png"),
    FRIENDLY_TWO("enemy/friendly_2.png"),
    FRIENDLY_READY("enemy/friendly_ready.png");

    private final BufferedImage image;

    EnemyState(String imagePath) {
        this.image = Inf101Graphics.loadImageFromResources(imagePath);
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
}
