package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableGameModel {
    GameState getCurrentState();

    void movePlayer(int x, int y);

    void checkOutOfBounds();

    void scaleComponents(double windowScale);

    void updateBounds(double windowWidth, double windowHeight);

    void getNextEnemyImage();
}
