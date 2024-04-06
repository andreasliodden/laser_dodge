package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.controller.ControllablePlayerModel;
import no.uib.inf101.sample.view.Inf101Graphics;

public class PlayerModel implements ControllablePlayerModel {
    private GamePosition position;
    private double playerSpeed;
    private int playerHealth;
    private BufferedImage playerImage;

    public PlayerModel() {
        this.position = new GamePosition(20, 20);
        this.playerSpeed = 3;
        this.playerHealth = 100;
        this.playerImage = Inf101Graphics.loadImageFromResources("resources/player_right.png");
    }

    public GamePosition getPosition() {
        return this.position;
    }

    public double getCenterX() {
        return this.position.x() + playerImage.getWidth() / 2;
    }

    public double getCenterY() {
        return this.position.y() + playerImage.getHeight() / 2;
    }

    public double getPlayerSpeed() {
        return this.playerSpeed;
    }

    public int getPlayerHealth()  {
        return this.playerHealth;
    }

    public BufferedImage getPlayerImage() {
        return this.playerImage;
    }
}