package no.uib.inf101.sample.controller;

public interface ControllableEnemy {
    void updateState();
    void switchGameState();
    void updateShootingStatus();
}
