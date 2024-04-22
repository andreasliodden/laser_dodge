package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllableEnemy {
    void updateState();
    void switchMood();
    void updateShootingStatus();
    void pause(GameState gameState);
    void resume();
}
