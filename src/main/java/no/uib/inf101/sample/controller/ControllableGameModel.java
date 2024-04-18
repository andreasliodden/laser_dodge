package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableGameModel {
    boolean movePlayer(int deltaX, int deltaY);

    void addProjectile();

    void addGoldenApple();

    void clockTick();

    void updateGameState();

    GameState getCurrentState();

    ControllableEnemy getControllableEnemy();

    void addTimeScore();

    void updateGappleCountdown();
}
