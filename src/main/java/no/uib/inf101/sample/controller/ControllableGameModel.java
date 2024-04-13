package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableGameModel {
    GameState getCurrentState();

    void movePlayer(int x, int y);

    void getNextEnemyImage();

    void clockTick();

    void addProjectile();

    void setEnemyStatus();
}
