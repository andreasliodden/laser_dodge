package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public class Player {
    private static final double START_X = 0;
    private static final double START_Y = 0;
    private static final double MAX_X = 1;
    private static final double MAX_Y = 1;

    private BufferedImage playerImage;
    private double playerX, playerY;
    private double playerSpeed;

    private BufferedImage playerFrontLeft = Inf101Graphics.loadImageFromResources("/player_front_left.png");
    private BufferedImage playerFrontRight = Inf101Graphics.loadImageFromResources("/player_front_right.png");
    private BufferedImage playerBackLeft = Inf101Graphics.loadImageFromResources("/player_back_left.png");
    private BufferedImage playerBackRight = Inf101Graphics.loadImageFromResources("/player_back_right.png");

    public Player() {
        this.playerX = START_X;
        this.playerY = START_Y;
        this.playerImage = playerFrontRight;
        this.playerSpeed = 6;
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

    public double getPlayerSpeed() {
        return this.playerSpeed;
    }

    private void setPlayerImage(int deltaX, int deltaY) {
        if (deltaX == -1) {
            if (playerImage == playerFrontRight) {
                playerImage = playerFrontLeft;
            } else if (playerImage == playerBackRight) {
                playerImage = playerBackLeft;
            }
        } else if (deltaX == 1) {
            if (playerImage == playerFrontLeft) {
                playerImage = playerFrontRight;
            } else if (playerImage == playerBackLeft) {
                playerImage = playerBackRight;
            }
        } else if (deltaY == -1) {
            if (playerImage == playerFrontRight) {
                playerImage = playerBackRight;
            } else if (playerImage == playerFrontLeft) {
                playerImage = playerBackLeft;
            }
        } else if (deltaY == 1) {
            if (playerImage == playerBackRight) {
                playerImage = playerFrontRight;
            } else if (playerImage == playerBackLeft) {
                playerImage = playerFrontLeft;
            }
        }
    }

    public double getNextX(int deltaX) {
        return playerX + deltaX * 0.001 * playerSpeed;
    }

    public double getNextY(int deltaY) {
        return playerY + deltaY * 0.001 * playerSpeed;
    }

    public void move(int deltaX, int deltaY) {
        double nextX = getNextX(deltaX);
        double nextY = getNextY(deltaY);
        if (isLegalPosition(nextX, nextY)) {
            playerX = nextX;
            playerY = nextY;
        }
        setPlayerImage(deltaX, deltaY);
    }

    private boolean isLegalPosition(double x, double y) {
        return (x >= 0 && x <= 1 && y >= 0 && y <= 1);
    }
}
