package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.enemy.EnemyState;

public interface ControllableEnemy {
    EnemyState getCurrentState();
    void updateState();
    void switchMood();
    void updateShootingStatus();
    void updatePause();
}
