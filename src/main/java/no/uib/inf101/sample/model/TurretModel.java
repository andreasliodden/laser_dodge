package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.GameView;
import no.uib.inf101.sample.view.Inf101Graphics;

public class TurretModel {
    private GamePosition position;
    private BufferedImage turretImage;

    public TurretModel() {
        // placeholder
        this.turretImage = Inf101Graphics.loadImageFromResources("/player_right.png");
    }

    // Fix problems with image scaling
    public void setPosition(GameView gameView) {
        double imageWidth = turretImage.getWidth();
        double imageHeight = turretImage.getHeight();
        this.position = new GamePosition((gameView.getWidth() - imageWidth) / 2, (gameView.getHeight() - imageHeight) / 2);
    }

    public GamePosition getPosition() {
        return this.position;
    }

    public BufferedImage getImage() {
        return this.turretImage;
    }
}
