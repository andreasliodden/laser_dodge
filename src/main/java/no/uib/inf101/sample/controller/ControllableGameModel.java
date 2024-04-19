package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableGameModel {
    boolean movePlayer(int deltaX, int deltaY);

    void addProjectile();

    void addGoldenApple();

    void clockTick();

    GameState getCurrentState();

    ControllableEnemy getControllableEnemy();

    void addTimeScore();

    void updateGappleCountdown();

    void startNewGame();

    void setGameState(GameState nextState);

    void resetGappleCountdown();
}
