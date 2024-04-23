package no.uib.inf101.sample.view.viewable;

import no.uib.inf101.sample.model.player.PlayerState;

public interface ViewablePlayer extends ViewableEntity {
    PlayerState getState();
    int getHealth();
    int getMaxHealth();

}
