package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public enum EnemyState {
    ANGRY_ONE("enemy/angry_1.png"),
    ANGRY_TWO("enemy/angry_2.png"),
    ANGRY_READY("enemy/angry_ready.png"),
    HAPPY_ONE("enemy/happy_1.png"),
    HAPPY_TWO("enemy/happy_2.png"),
    HAPPY_READY("enemy/happy_ready.png");

    private final BufferedImage image;

    
    EnemyState(String imagePath) {
        this.image = Inf101Graphics.loadImageFromResources(imagePath);
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
}
