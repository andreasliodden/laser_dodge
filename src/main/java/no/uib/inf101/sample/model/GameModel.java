package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.controller.ControllableGameModel;

public class GameModel implements ControllableGameModel {
    private EnemyModel enemyModel;
    private PlayerModel playerModel;
    private GameState gameState;

    public GameModel() {
        this.enemyModel = new EnemyModel();
        this.playerModel = new PlayerModel();
        this.gameState = GameState.ACTIVE;
    }

    @Override
    public void updateBounds(double windowWidth, double windowHeight) {
        playerModel.updateWindowSize(windowWidth, windowHeight);
    }

    @Override
    public void scaleComponents(double windowScale) {
        enemyModel.resizeComponents(windowScale);
        playerModel.resizeComponents(windowScale);
    }


    @Override
    public GameState getCurrentState() {
        return this.gameState;
    }

    @Override
    public void movePlayer(int x, int y) {
        playerModel.move(x, y);
    }


    @Override
    public void checkOutOfBounds() {
        playerModel.checkOutOfBounds();
    }

    private boolean playerEnemyCollision() {
        double playerStartX = playerModel.getX() - playerModel.getPlayerWidth() / 2;
        double playerEndX = playerStartX + playerModel.getPlayerWidth();
        double playerStartY = playerModel.getY() - playerModel.getPlayerHeight() / 2;
        double playerEndY = playerStartY + playerModel.getPlayerHeight();

        double enemyStartX = enemyModel.getX() - enemyModel.getImageWidth() / 2;
        double enemyEndX = enemyStartX + enemyModel.getImageWidth();
        double enemyStartY = enemyModel.getY() - enemyModel.getImageHeight() / 2;
        double enemyEndY = enemyStartY + enemyModel.getImageHeight();

        return (playerEndX < enemyStartX || playerStartX > enemyEndX) 
            && (playerEndY < enemyStartY || playerStartY > enemyEndY);
    }

    @Override
    public void getNextEnemyImage() {
        enemyModel.getNextImage();
    }

    public double getPlayerX() {
        return playerModel.getX();
    }

    public double getPlayerY() {
        return playerModel.getY();
    }

    public BufferedImage getPlayerImage() {
        return playerModel.getImage();
    }

    public BufferedImage getEnemyImage() {
        return enemyModel.getImage();
    }
}
