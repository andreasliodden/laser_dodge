package no.uib.inf101.sample.view.viewables;

import no.uib.inf101.sample.model.GameState;

public interface ViewableGameModel {
    ViewablePlayer getViewablePlayer();
    ViewableEnemy getViewableEnemy();
    ViewableProjectile getProjectile(int index);
    boolean gappleExists();
    double getGappleX();
    double getGappleY();
    GameState getCurrentState();
    int getNumberOfProjectiles();
    int getScore();
    int getGappleCountdown();
}
