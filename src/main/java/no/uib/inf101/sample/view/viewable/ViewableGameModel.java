package no.uib.inf101.sample.view.viewable;

import no.uib.inf101.sample.model.GameState;

public interface ViewableGameModel {
    /**
     * Gets a viewable representation of the player.
     * 
     * @return a viewable representasion of the player.
     */
    ViewablePlayer getViewablePlayer();

    /**
     * Gets a viewable representation of the enemy.
     * 
     * @return a viewable representasion of the enemy.
     */
    ViewableEnemy getViewableEnemy();

    /**
     * Returns a viewable representation of the projectile at the given index.
     * 
     * @param index the index of the projectile
     * @return the viewable projectile
     */
    ViewableProjectile getProjectile(int index);

    /**
     * Checks if a golden apple exists in the game.
     * 
     * @return true if a golden apple exists, false otherwise.
     */
    boolean gappleExists();

    /**
     * Gets the current x-position of the golden apple.
     * 
     * @return the current x-position of the golden apple.
     */
    double getGappleX();

    /**
     * Gets the current y-position of the golden apple.
     * 
     * @return the current y-position of the golden apple.
     */
    double getGappleY();

    /**
     * Gets the current state of the game.
     * 
     * @return the current state of the game.
     */
    GameState getGameState();

    /**
     * Gets the number of active projectiles in the game.
     * 
     * @return the number of active projectiles.
     */
    int getNumberOfProjectiles();

    /**
     * Gets the total game score.
     * 
     * @return the total game score.
     */
    int getScore();

    /**
     * Gets the remaining time before a new golden apple spawns.
     * 
     * @return the time before a new golden apple spawns.
     */
    int getGappleCountdown();
}
