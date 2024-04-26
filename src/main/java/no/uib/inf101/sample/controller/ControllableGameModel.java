package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

/**
 * Interface that defines methods for controlling a game model, 
 * including player movement and game updates.
 */
public interface ControllableGameModel {
    /**
     * Moves the player by the specified delta x and y values.
     * 
     * @param deltaX the change in x-direction
     * @param deltaY the change in y-direction
     * 
     * @return true if the player is moved successfuly, false otherwise
     */
    boolean movePlayer(int deltaX, int deltaY);

    /**
     * Performs actions on game components 
     * that should occur on each clock tick.
     */
    void clockTick();

    /**
     * Adds a projectile to the game.
     */
    void addProjectile();

    /**
     * Adds a golden apple power-up to the game.
     */
    void addGapple();

    /**
     * Adds points to the game as time passes.
     */
    void addTimeScore();

    /**
     * Gets the pre-defined cooldown between each golden apple spawn.
     * 
     * @return the cooldown between each golden apple spawn
     */
    int getGappleCooldown();

    /**
     * Reduces the golden apple countdown by one.
     */
    void updateGappleCountdown();

    /**
     * Resets the golden apple countdown and the boolean value 
     * that tells if the golden apple is present.
     */
    void resetGapple();

    /**
     * Initializes game components and values to prepare for a new game.
     */
    void startNewGame();

    /**
     * Updates and sets the current game state to the one given.
     * 
     * @param nextState the game state to be set
     */
    void setGameState(GameState nextState);

    /**
     * Gets the current state of the game.
     * 
     * @return the current game state.
     */
    GameState getGameState();

    /**
     * Gets a ControllableEnemy object that is used by the GameController.
     * 
     * @return a ControllableEnemy object.
     */
    ControllableEnemy getControllableEnemy();
}
