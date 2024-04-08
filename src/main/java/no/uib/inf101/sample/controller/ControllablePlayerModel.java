package no.uib.inf101.sample.controller;

import no.uib.inf101.sample.model.GameState;

public interface ControllablePlayerModel {
    GameState getCurrentState();

    void movePlayerX(int direction);

    void movePlayerY(int direction);
}
