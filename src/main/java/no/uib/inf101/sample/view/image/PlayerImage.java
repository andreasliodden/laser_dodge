package no.uib.inf101.sample.view.image;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.model.player.PlayerState;
import no.uib.inf101.sample.view.Inf101Graphics;

/**
 * Enum that defines the different images representing the player.
 */
public enum PlayerImage {
    FRONT_LEFT("player/front_left.png"),
    FRONT_RIGHT("player/front_right.png"),
    BACK_LEFT("player/back_left.png"),
    BACK_RIGHT("player/back_right.png"),
    HURT_FRONT_LEFT("player/hurt_front_left.png"),
    HURT_FRONT_RIGHT("player/hurt_front_right.png"),
    HURT_BACK_LEFT("player/hurt_back_left.png"),
    HURT_BACK_RIGHT("player/hurt_back_right.png");

    private final BufferedImage image;

    private PlayerImage(String imagePath) {
        this.image = Inf101Graphics.loadImageFromResources(imagePath);
    }

    /**
     * Gets an image that represents the given player state.
     */
    public static BufferedImage get(PlayerState playerState) {
        PlayerImage player = PlayerImage.valueOf(playerState.name());
        return player.image;
    }
}
