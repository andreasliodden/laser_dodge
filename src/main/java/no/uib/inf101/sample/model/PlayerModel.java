package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.controller.ControllablePlayerModel;
import no.uib.inf101.sample.view.Inf101Graphics;

public class PlayerModel implements ControllablePlayerModel {
    private static final double START_X = 100;
    private static final double START_Y = 100;
    private static final int START_HEALTH = 100;
    private static final int SPEED = 7;

    private double playerSpeed;
    private int playerHealth;
    private BufferedImage playerImage;
    private GameState gameState;
    private double playerX;
    private double playerY;

    private double windowWidth;
    private double windowHeight;

    private BufferedImage playerFrontLeft = Inf101Graphics.loadImageFromResources("/player_front_left.png");
    private BufferedImage playerFrontRight = Inf101Graphics.loadImageFromResources("/player_front_right.png");
    private BufferedImage playerBackLeft = Inf101Graphics.loadImageFromResources("/player_back_left.png");
    private BufferedImage playerBackRight = Inf101Graphics.loadImageFromResources("/player_back_right.png");

    public PlayerModel() {
        this.playerX = START_X;
        this.playerY = START_Y;
        this.playerHealth = START_HEALTH;
        this.playerSpeed = SPEED;
        this.playerImage = playerFrontRight; 
        this.gameState = GameState.ACTIVE;
    }

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
    public void updateWindowSize(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }

    @Override
    public GameState getCurrentState() {
        return this.gameState;
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
        double nextX;
        if (direction == -1) {
            nextX = playerX - playerSpeed;
            if (isLegalPosition(nextX, playerY)) {
                playerX = nextX;
            } 
        } else if (direction == 1) {
            nextX = playerX + playerSpeed;
            if (isLegalPosition(nextX, playerY)) {
                playerX = nextX;
            } 
        }
        setPlayerImage(direction, 0);

        
    }

    @Override
    public void movePlayerY(int direction) {
        double nextY;
        if (direction == -1) {
            nextY = playerY - playerSpeed;
            if (isLegalPosition(playerX, nextY)) {
                playerY = nextY;
            } 
        } else if (direction == 1) {
            nextY = playerY + playerSpeed;
            if (isLegalPosition(playerX, nextY)) {
                playerY = nextY;
            } 
        }
        setPlayerImage(0, direction);
    }

    private boolean isLegalPosition(double x, double y) {
        if (x < playerImage.getWidth() * 2 || x > windowWidth - playerImage.getWidth() * 2) {
            return false;
        } else if (y < playerImage.getHeight() * 2 || y > windowHeight - playerImage.getHeight() * 2) {
            return false;
        }
        return true;
    } 

    @Override
    public void checkValidPosition() {
        double maxX = windowWidth - playerImage.getHeight() * 2;
        double maxY = windowHeight - playerImage.getHeight() * 2;

        if(!isLegalPosition(playerX, playerY)) {
            if(isLegalPosition(maxX, playerY)) {
                playerX = maxX;
            } else if (isLegalPosition(playerX, maxY)) {
                playerY = maxY;
            } else {
                playerX = maxX;
                playerY = maxY;
            }
        }
    }
}