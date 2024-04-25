package no.uib.inf101.sample.model.player;

import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.model.entity.Entity;
import no.uib.inf101.sample.view.viewable.ViewablePlayer;

/** 
 * Represents the player entity in the game, which the user controls.
 * It extends the Entity class and implements the ViewablePlayer interface.
 */
public class Player extends Entity implements ViewablePlayer {
    private static final double START_X = 0.20;
    private static final double START_Y = 0.20;
    private static final double PLAYER_SPEED = 0.007;
    private static final int MAX_HEALTH = 80;
    private static final int HEALTHPOINT = 2;
    private static final int HITPOINT = 10;

    private int playerHealth;
    private PlayerState currentState;

    public Player() {
        this.x = START_X;
        this.y = START_Y;
        this.currentState = PlayerState.FRONT_RIGHT;
        this.playerHealth = MAX_HEALTH;
    }

    @Override
    public PlayerState getPlayerState() {
        return this.currentState;
    }

    @Override
    public int getHealth() {
        return this.playerHealth;
    }

    @Override
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    private void updatePlayerState(int deltaX, int deltaY) {
        if (deltaX == 0 && deltaY == 0) {
            switch (currentState) {
                case FRONT_LEFT:
                    currentState = PlayerState.HURT_FRONT_LEFT;
                    break;
                case FRONT_RIGHT:
                    currentState = PlayerState.HURT_FRONT_RIGHT;
                    break;
                case BACK_LEFT:
                    currentState = PlayerState.HURT_BACK_LEFT;
                    break;
                case BACK_RIGHT:
                    currentState = PlayerState.HURT_BACK_RIGHT;
                    break;
                default: 
                    break;
            }
        } else if (deltaX == -1) {
            switch (currentState) {
                case FRONT_RIGHT:
                case HURT_FRONT_RIGHT: 
                    currentState = PlayerState.FRONT_LEFT;
                    break;
                case BACK_RIGHT:
                case HURT_BACK_RIGHT:
                    currentState = PlayerState.BACK_LEFT;
                    break;
                default: 
                    break; 
            }
        } else if (deltaX == 1) {
            switch (currentState) {
                case FRONT_LEFT:
                case HURT_FRONT_LEFT: 
                    currentState = PlayerState.FRONT_RIGHT;
                    break;
                case BACK_LEFT:
                case HURT_BACK_LEFT:
                    currentState = PlayerState.BACK_RIGHT;
                    break;
                default: 
                    break; 
            }
        } else if (deltaY == -1) {
            switch (currentState) {
                case FRONT_LEFT:
                case HURT_FRONT_LEFT: 
                    currentState = PlayerState.BACK_LEFT;
                    break;
                case FRONT_RIGHT:
                case HURT_FRONT_RIGHT:
                    currentState = PlayerState.BACK_RIGHT;
                    break;
                default: 
                    break; 
            }
        } else if (deltaY == 1) {
            switch (currentState) {
                case BACK_LEFT:
                case HURT_BACK_LEFT: 
                    currentState = PlayerState.FRONT_LEFT;
                    break;
                case BACK_RIGHT:
                case HURT_BACK_RIGHT:
                    currentState = PlayerState.FRONT_RIGHT;
                    break;
                default: 
                    break; 
            }
        }
    }

    /**
     * Gets the next x-coordinate based on the given delta. 
     * 
     * @param deltaX the given x-direction
     * @return the next x-coordinate
     */
    public double getNextX(int deltaX) {
        return x + deltaX * PLAYER_SPEED;
    }

    /**
     * Gets the next y-coordinate based on the given delta. 
     * 
     * @param deltaY the given y-direction
     * @return the next y-coordinate
     */
    public double getNextY(int deltaY) {
        return y + deltaY * PLAYER_SPEED;
    }

    /**
     * Moves the player by the specified delta values in x and y direction.
     * Updates the player state based on the new direction.
     * 
     * @param deltaX the change in x-direction
     * @param deltaY the change in y-direction
     * 
     * @return true if the player's position was 
     * successfully updated, false otherwise
     */
    public boolean move(int deltaX, int deltaY) {
        updatePlayerState(deltaX, deltaY);
        return checkAndUpdatePosition(getNextX(deltaX), getNextY(deltaY));
    }

    /**
     * Registers when the player is hit by a projectile.
     * The effects of the hit depends on the current game state;
     * it takes health if the enemy is angry
     * and adds health if the enemy is happy.
     * 
     * @param gameState the current state of the game
     */

    public void registerHit(GameState gameState) {
        if (gameState == GameState.ACTIVE_ANGRY) {
            if (playerHealth - HITPOINT < 0) {
                playerHealth = 0;
            } else {
                playerHealth -= HITPOINT;
            }
            updatePlayerState(0, 0);
        } else {
            if (playerHealth + HEALTHPOINT > MAX_HEALTH) {
                playerHealth = MAX_HEALTH;
            } else {
                playerHealth += HEALTHPOINT;
            }
        }
    }

    /**
     * Resets the player and its values.
     * Used when initializing a new game.
     */

    public void reset() {
        this.x = START_X;
        this.y = START_Y;
        this.playerHealth = MAX_HEALTH;
        this.currentState = PlayerState.FRONT_RIGHT;
    }
}
