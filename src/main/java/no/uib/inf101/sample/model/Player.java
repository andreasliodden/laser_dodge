package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

public class Player {
    private static final double START_X = 0.10;
    private static final double START_Y = 0.10;
    private static final double MAX_X = 1;
    private static final double MAX_Y = 1;

    private double playerX, playerY;
    private double playerSpeed;
    private int playerHealth;
    private PlayerState playerState;
    private boolean isHit;

    Player() {
        this.playerX = START_X;
        this.playerY = START_Y;
        this.playerState = PlayerState.FRONT_RIGHT;
        this.playerSpeed = 7;
        this.playerHealth = 50;
        this.isHit = false;
    }

    double getX() {
        return this.playerX;
    }

    double getY() {
        return this.playerY;
    }

    BufferedImage getImage() {
        return playerState.getImage();
    }

    double getPlayerSpeed() {
        return this.playerSpeed;
    }

    int getPlayerHealth() {
        return this.playerHealth;
    }

    private void updatePlayerState(int deltaX, int deltaY) {
        if (deltaX == 0 && deltaY == 0) {
            switch (playerState) {
                case FRONT_LEFT:
                    playerState = PlayerState.HURT_FRONT_LEFT;
                    break;
                case FRONT_RIGHT:
                    playerState = PlayerState.HURT_FRONT_RIGHT;
                    break;
                case BACK_LEFT:
                    playerState = PlayerState.HURT_BACK_LEFT;
                    break;
                case BACK_RIGHT:
                    playerState = PlayerState.HURT_BACK_RIGHT;
                    break;
                default: 
                    break;
            }
        } else if (deltaX == -1) {
            switch (playerState) {
                case FRONT_RIGHT:
                case HURT_FRONT_RIGHT: 
                    playerState = PlayerState.FRONT_LEFT;
                    break;
                case BACK_RIGHT:
                case HURT_BACK_RIGHT:
                    playerState = PlayerState.BACK_LEFT;
                    break;
                default: 
                    break; 
            }
        } else if (deltaX == 1) {
            switch (playerState) {
                case FRONT_LEFT:
                case HURT_FRONT_LEFT: 
                    playerState = PlayerState.FRONT_RIGHT;
                    break;
                case BACK_LEFT:
                case HURT_BACK_LEFT:
                    playerState = PlayerState.BACK_RIGHT;
                    break;
                default: 
                    break; 
            }
        } else if (deltaY == -1) {
            switch (playerState) {
                case FRONT_LEFT:
                case HURT_FRONT_LEFT: 
                    playerState = PlayerState.BACK_LEFT;
                    break;
                case FRONT_RIGHT:
                case HURT_FRONT_RIGHT:
                    playerState = PlayerState.BACK_RIGHT;
                    break;
                default: 
                    break; 
            }
        } else if (deltaY == 1) {
            switch (playerState) {
                case BACK_LEFT:
                case HURT_BACK_LEFT: 
                    playerState = PlayerState.FRONT_LEFT;
                    break;
                case BACK_RIGHT:
                case HURT_BACK_RIGHT:
                    playerState = PlayerState.FRONT_RIGHT;
                    break;
                default: 
                    break; 
            }
        }
    }

    double getNextX(int deltaX) {
        return playerX + deltaX * 0.001 * playerSpeed;
    }

    double getNextY(int deltaY) {
        return playerY + deltaY * 0.001 * playerSpeed;
    }

    void move(int deltaX, int deltaY) {
        double nextX = getNextX(deltaX);
        double nextY = getNextY(deltaY);
        if (isLegalPosition(nextX, nextY)) {
            playerX = nextX;
            playerY = nextY;
        } else if (isLegalPosition(nextX, playerY)) {
            if (deltaY == 1) {
                playerY = 1;
            } else {
                playerY = 0;
            }
        } else {
            if (deltaX == 1) {
                playerX = 1;
            } else {
                playerX = 0;
            }
        }

        if (!isHit) {
            updatePlayerState(deltaX, deltaY);
        }
        isHit = false;
    }

    private boolean isLegalPosition(double x, double y) {
        return (x >= 0 && x <= MAX_X && y >= 0 && y <= MAX_Y);
    }

    void registerHit(GameState gameState) {
        if (gameState == GameState.ACTIVE) {
            if (playerHealth > 0) {
                playerHealth -= 10;
            }
            updatePlayerState(0, 0);
        } else {
            if (playerHealth < 50) {
                playerHealth += 5;
            }
        }
        isHit = true;
    }
}
