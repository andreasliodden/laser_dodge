package no.uib.inf101.sample.view.viewable;

import no.uib.inf101.sample.model.player.PlayerState;

/**
 * Interface that extends ViewableEntity and provides methods 
 * for retrieving the current state and health of a viewable player.
 */
public interface ViewablePlayer extends ViewableEntity {
    /**
     * Gets the current state of the player.
     * 
     * @return the current state of the player
     */
    PlayerState getPlayerState();

    /**
     * Gets the current health of the player.
     * 
     * @return the current health of the player
     */
    int getHealth();

    /**
     * Gets the maximum health the player can have.
     * 
     * @return the maximum player health
     */
    int getMaxHealth();

}
