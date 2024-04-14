package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public enum PlayerState {
    FRONT_LEFT("player_front_left.png"),
    FRONT_RIGHT("player_front_right.png"),
    BACK_LEFT("player_back_left.png"),
    BACK_RIGHT("player_back_right.png"),
    HURT_FRONT_LEFT("hurt_front_left.png"),
    HURT_FRONT_RIGHT("hurt_front_right.png"),
    HURT_BACK_LEFT("hurt_back_left.png"),
    HURT_BACK_RIGHT("hurt_back_right.png");

    private final BufferedImage image;

    PlayerState(String imagePath) {
        this.image = Inf101Graphics.loadImageFromResources(imagePath);
    }

    public BufferedImage getImage() {
        return this.image;
    }
}


