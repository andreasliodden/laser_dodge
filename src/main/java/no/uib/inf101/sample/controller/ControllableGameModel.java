package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableGameModel {
    /**
     * Moves the player by the specified delta x and y values.
     * 
     * @param deltaX the change in x-direction
     * @param deltaY the change in y-direction
     * 
     * @return true if the player was moved successfuly, false otherwise
     */
    boolean movePlayer(int deltaX, int deltaY);

    /**
     * Performs actions that should occur on each clock tick.
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
     * Ticks the gapple countdown down until it has reached zero.
     */
    void updateGappleCountdown();

    /**
     * Resets the gapple countdown and boolean value that tells if the gapple is
     * present.
     */
    void resetGapple();

    /**
     * Initializes game components and values to prepare for a new game.
     */
    void startNewGame();

    /**
     * Updates and sets the game state to the given one,
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
