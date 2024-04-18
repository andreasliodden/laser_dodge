package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.interfaces.ViewablePlayer;

public class Player extends Entity implements ViewablePlayer {
    private static final double START_X = 0.10;
    private static final double START_Y = 0.10;

    private double playerSpeed;
    private int playerHealth;
    private PlayerState playerState;

    public Player() {
        this.x = START_X;
        this.y = START_Y;
        this.playerState = PlayerState.FRONT_RIGHT;
        this.playerSpeed = 7;
        this.playerHealth = 50;
    }

    @Override
    public BufferedImage getImage() {
        return playerState.getImage();
    }

    @Override
    public int getHealth() {
        return this.playerHealth;
    }

    public double getPlayerSpeed() {
        return this.playerSpeed;
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

    boolean move(int deltaX, int deltaY) {
        updatePlayerState(deltaX, deltaY);
        return updatePosition(getNextX(deltaX), getNextY(deltaY));
    }

    public void registerHit(GameState gameState) {
        if (gameState == GameState.ACTIVE_ENEMY) {
            double hitpoint = 10;
            if (playerHealth - hitpoint < 0) {
                playerHealth = 0;
            } else {
                playerHealth -= hitpoint;
            }
            updatePlayerState(0, 0);
        } else {
            double regen = 3;
            if (playerHealth + regen <= 50) {
                playerHealth += regen;
            } else {
                playerHealth = 50;
            }
        }
    }
}
