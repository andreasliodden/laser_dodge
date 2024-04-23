package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.enemy.EnemyState;

public interface ControllableEnemy {
    
    /**
     * Gets the current state of the enemy.
     * 
     * @return the current enemy state
     */
    EnemyState getCurrentState();

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
