package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public class PlayerModel {
    private static final double START_X = 100;
    private static final double START_Y = 100;
    private static final int START_HEALTH = 100;

    private double playerSpeed;
    private int playerHealth;
    private BufferedImage playerImage;
    private double playerX, playerY;
    private double windowWidth, windowHeight;
    private double playerWidth, playerHeight;

    private BufferedImage playerFrontLeft = Inf101Graphics.loadImageFromResources("/player_front_left.png");
    private BufferedImage playerFrontRight = Inf101Graphics.loadImageFromResources("/player_front_right.png");
    private BufferedImage playerBackLeft = Inf101Graphics.loadImageFromResources("/player_back_left.png");
    private BufferedImage playerBackRight = Inf101Graphics.loadImageFromResources("/player_back_right.png");

    public PlayerModel() {
        this.playerX = START_X;
        this.playerY = START_Y;
        this.playerHealth = START_HEALTH;
        this.playerImage = playerFrontRight; 
    }

    public double getX() {
        return this.playerX;
    }

    public double getY() {
        return this.playerY;
    }

    public BufferedImage getImage() {
        return this.playerImage;
    }

    double getPlayerWidth() {
        return this.playerWidth;
    }

    double getPlayerHeight() {
        return this.playerHeight;
    }

    double getSpeed() {
        return this.playerSpeed;
    }

    int getHealth()  {
        return this.playerHealth;
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

    void moveX(int direction) {
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

    void moveY(int direction) {
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
        if (x < playerWidth / 2 || x > windowWidth - playerWidth / 2) {
            return false;
        } else if (y < playerWidth / 2 || y > windowHeight - playerHeight / 2) {
            return false;
        }
        return true;
    } 

    public void checkOutOfBounds() {
        double maxX = windowWidth - playerWidth / 2;
        double maxY = windowHeight - playerHeight / 2;

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

    void updateWindowSize(double width, double height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }

    void resizeComponents(double windowScale) {
        this.playerWidth = playerImage.getWidth() * windowScale * 5;
        this.playerHeight = playerImage.getHeight() * windowScale * 5;
        this.playerSpeed = windowScale * 7;
    }
}
