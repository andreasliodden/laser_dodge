package no.uib.inf101.sample.view.image;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.player.PlayerState;
import no.uib.inf101.sample.view.Inf101Graphics;

public class TestPlayerImage extends ImageTest {
    private static final BufferedImage FRONT_RIGHT = Inf101Graphics.loadImageFromResources("player/front_right.png");
    private static final BufferedImage BACK_LEFT = Inf101Graphics.loadImageFromResources("player/back_left.png");
    private static final BufferedImage HURT_BACK_RIGHT = Inf101Graphics.loadImageFromResources("player/hurt_back_right.png");

    private PlayerState playerState;

    @Test
    public void getImage() {
        playerState = PlayerState.FRONT_RIGHT;
        assert(imagesAreEqual(FRONT_RIGHT, PlayerImage.get(playerState)));

        playerState = PlayerState.BACK_LEFT;
        assert(imagesAreEqual(BACK_LEFT, PlayerImage.get(playerState)));

        playerState = PlayerState.HURT_BACK_RIGHT;
        assert(imagesAreEqual(HURT_BACK_RIGHT, PlayerImage.get(playerState)));

        playerState = PlayerState.HURT_FRONT_LEFT;
        assertFalse(imagesAreEqual(HURT_BACK_RIGHT, PlayerImage.get(playerState)));
    }
}
