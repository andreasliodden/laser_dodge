package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.enemy.EnemyState;

/**
 * Interface that defines methods for controlling the enemy, 
 * which includes its state and shooting status.
 */
public interface ControllableEnemy {

    /**
     * Gets the current state of the enemy.
     * 
     * @return the current enemy state
     */
    EnemyState getEnemyState();

    /**
     * Updates the current state of the enemy.
     */
    void updateState();

    /**
     * Switches the current mood of the enemy.
     * Swicthes between angry- and happy enemy states.
     */
    void switchMood();

    /**
     * Updates the current shooting status of the enemy.
     */
    void updateShootingStatus();

    /**
     * Updates the pause status of the enemy.
     * Used to switch between active and paused state.
     */
    void updatePause();
}
