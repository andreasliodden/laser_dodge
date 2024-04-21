package no.uib.inf101.sample.model.player;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.model.Entity;
import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.view.interfaces.ViewablePlayer;

public class Player extends Entity implements ViewablePlayer {
    private static final double START_X = 0.20;
    private static final double START_Y = 0.20;
    private static final double PLAYER_SPEED = 0.007;
    private static final int MAX_HEALTH = 80;

    private int playerHealth;
    private PlayerState playerState;

    public Player() {
        this.x = START_X;
        this.y = START_Y;
        this.playerState = PlayerState.FRONT_RIGHT;
        this.playerHealth = MAX_HEALTH;
    }

    @Override
    public BufferedImage getImage() {
        return playerState.getImage();
    }

    @Override
    public int getHealth() {
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

    public double getNextX(int deltaX) {
        return x + deltaX * PLAYER_SPEED;
    }

    public double getNextY(int deltaY) {
        return y + deltaY * PLAYER_SPEED;
    }

    public boolean move(int deltaX, int deltaY) {
        updatePlayerState(deltaX, deltaY);
        return checkAndUpdatePosition(getNextX(deltaX), getNextY(deltaY));
    }

    public void registerHit(GameState gameState) {
        if (gameState == GameState.ACTIVE_ANGRY) {
            double hitpoint = 10;
            if (playerHealth - hitpoint < 0) {
                playerHealth = 0;
            } else {
                playerHealth -= hitpoint;
            }
            updatePlayerState(0, 0);
        } else {
            double healthPoints = 2;
            if (playerHealth + healthPoints > MAX_HEALTH) {
                playerHealth = MAX_HEALTH;
            } else {
                playerHealth += healthPoints;
            }
        }
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public void reset() {
        this.x = START_X;
        this.y = START_Y;
        this.playerHealth = MAX_HEALTH;
        this.playerState = PlayerState.FRONT_RIGHT;
    }
}
