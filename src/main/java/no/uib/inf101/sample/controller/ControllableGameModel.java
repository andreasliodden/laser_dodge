package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableGameModel {
    boolean movePlayer(int deltaX, int deltaY);

    void clockTick();

    void addProjectile();

    void addGapple();

    void addTimeScore();

    int getGappleCooldown();

    void updateGappleCountdown();

    void resetGapple();

    void startNewGame();

    void setGameState(GameState nextState);

    GameState getCurrentState();

    ControllableEnemy getControllableEnemy();
}
