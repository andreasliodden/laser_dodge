package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.EnemyModel;
import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.model.PlayerModel;

public interface ControllableGameModel {
    GameState getCurrentState();

    void movePlayer(int x, int y);

    void playerOutOfBounds();

    void scaleComponents(double windowScale);

    void updateWindowSize(double windowWidth, double windowHeight);

    EnemyModel getEnemyModel();

    PlayerModel getPlayerModel();
}
