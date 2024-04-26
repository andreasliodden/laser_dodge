package no.uib.inf101.sample.view;

import java.awt.Color;

/**
 * Interface that defines methods for retrieving color themes 
 * associated with different states of the game.
 */
public interface ColorTheme {
    /**
     * Gets the default background color 
     * when the enemy is in an angry state.
     * 
     * @return the background color when the enemy is angry.
     */
    Color getAngryBackground();

    /**
     * Gets the default background color 
     * when the enemy is in a happy state.
     * 
     * @return the background color when the enemy is happy.
     */
    Color getHappyBackground();

    /**
     * Gets the text color associated with a happy enemy state.
     * 
     * @return the text color representing a happy enemy state
     */
    Color getHappyTextColor();
}
