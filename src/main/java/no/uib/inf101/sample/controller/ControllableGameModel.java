package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableGameModel {
    boolean movePlayer(int x, int y);

    void addProjectile();

    void addGoldenApple();

    void clockTick();

    void updateEnemyImage();

    void updateGameState();

    void readyToShoot();

    GameState getCurrentState();
}
