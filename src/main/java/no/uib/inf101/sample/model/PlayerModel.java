package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.controller.ControllablePlayerModel;
import no.uib.inf101.sample.view.Inf101Graphics;

public class PlayerModel implements ControllablePlayerModel {
    private double playerSpeed;
    private int playerHealth;
    private BufferedImage playerImage;
    private GameState gameState;
    private double playerX;
    private double playerY;

    private BufferedImage playerFrontLeft = Inf101Graphics.loadImageFromResources("/player_front_left.png");
    private BufferedImage playerFrontRight = Inf101Graphics.loadImageFromResources("/player_front_right.png");
    private BufferedImage playerBackLeft = Inf101Graphics.loadImageFromResources("/player_back_left.png");
    private BufferedImage playerBackRight = Inf101Graphics.loadImageFromResources("/player_back_right.png");

    public PlayerModel() {
        this.playerX = 100;
        this.playerY = 100;
        this.playerSpeed = 7;
        this.playerHealth = 100;
        this.playerImage = playerFrontRight; 
        this.gameState = GameState.ACTIVE;
    }

    // Fix problems with image scaling
    public double getX() {
        return this.playerX;
    }

    public double getY() {
        return this.playerY;
    }

    public double getSpeed() {
        return this.playerSpeed;
    }

    public int getHealth()  {
        return this.playerHealth;
    }

    public BufferedImage getImage() {
        return this.playerImage;
    }

    @Override
    public GameState getCurrentState() {
        return gameState;
    }

    private void setPlayerImage(int directionX, int directionY) {
        if (directionX == -1) {
            if (playerImage == playerFrontRight) {
                playerImage = playerFrontLeft;
            } else if (playerImage == playerBackRight) {
                playerImage = playerBackLeft;
            }
        } else if (directionX == 1) {
            if (playerImage == playerFrontLeft) {
                playerImage = playerFrontRight;
            } else if (playerImage == playerBackLeft) {
                playerImage = playerBackRight;
            } 
        } else if (directionY == -1) {
            if (playerImage == playerFrontRight) {
                playerImage = playerBackRight;
            } else if (playerImage == playerFrontLeft) {
                playerImage = playerBackLeft;
            } 
        } else if (directionY == 1) {
            if (playerImage == playerBackRight) {
                playerImage = playerFrontRight;
            } else if (playerImage == playerBackLeft) {
                playerImage = playerFrontLeft;
            } 
        }
    }

    @Override
    public void movePlayerX(int direction) {
        playerX += playerSpeed * direction;
        setPlayerImage(direction, 0);
    }

    @Override
    public void movePlayerY(int direction) {
        playerY += playerSpeed * direction;
        setPlayerImage(0, direction);
    }
}