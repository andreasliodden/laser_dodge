package no.uib.inf101.sample.view.interfaces;

import no.uib.inf101.sample.model.GameState;

public interface ViewableGameModel {
    ViewablePlayer getViewablePlayer();
    ViewableEnemy getViewableEnemy();
    ViewableProjectile getProjectile(int index);
    boolean goldenAppleExists();
    double getGappleX();
    double getGappleY();
    GameState getCurrentState();
    int getNumberOfProjectiles();
}
