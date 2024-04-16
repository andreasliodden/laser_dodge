package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

public class Player extends Entity {
    private static final double START_X = 0.10;
    private static final double START_Y = 0.10;

    private double playerSpeed;
    private int playerHealth;
    private PlayerState playerState;

    Player() {
        this.x = START_X;
        this.y = START_Y;
        this.playerState = PlayerState.FRONT_RIGHT;
        this.playerSpeed = 7;
        this.playerHealth = 50;
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
        return x + deltaX * 0.001 * playerSpeed;
    }

    double getNextY(int deltaY) {
        return y + deltaY * 0.001 * playerSpeed;
    }

    void move(int deltaX, int deltaY) {
        double nextX = getNextX(deltaX);
        double nextY = getNextY(deltaY);
        updatePosition(nextX, nextY);
        updatePlayerState(deltaX, deltaY);
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
    }
}
