package no.uib.inf101.sample.view;

import java.awt.Color;

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
     * Gets the default green color when the enemy is happy.
     * 
     * @return the default green color when the enemy is happy.
     */
    Color getHappyGreenColor();
}
