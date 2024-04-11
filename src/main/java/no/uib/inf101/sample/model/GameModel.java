package no.uib.inf101.sample.model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import no.uib.inf101.sample.controller.ControllableGameModel;

public class GameModel implements ControllableGameModel {
    private Enemy enemy;
    private Player player;
    private GameState gameState;

    public GameModel() {
        this.enemy = new Enemy();
        this.player = new Player();
        this.gameState = GameState.ACTIVE;
    }


    @Override
    public GameState getCurrentState() {
        return this.gameState;
    }

    @Override
    public void movePlayer(int deltaX, int deltaY) {
        double nextX = player.getNextX(deltaX);
        double nextY = player.getNextY(deltaY);
        if (!playerEnemyCollision(nextX, nextY)) {
            player.move(deltaX, deltaY);
        }
    }

    @Override
    public void getNextEnemyImage() {
        enemy.getNextImage();
    }

    public double getPlayerX() {
        return player.getX();
    }

    public double getPlayerY() {
        return player.getY();
    }

    public BufferedImage getPlayerImage() {
        return player.getImage();
    }

    public BufferedImage getEnemyImage() {
        return enemy.getImage();
    }

    public double getEnemyX() {
        return enemy.getX();
    }

    public double getEnemyY() {
        return enemy.getY();
    }


    private boolean playerEnemyCollision(double playerX, double playerY) {
        Rectangle2D restricedArea = enemy.getRestricedArea();
        return restricedArea.contains(playerX, playerY);
    }
}
