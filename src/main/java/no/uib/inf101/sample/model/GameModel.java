package no.uib.inf101.sample.model;

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
    public EnemyModel getEnemyModel() {
        return enemyModel;
    }

    @Override
    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    @Override
    public void updateWindowSize(double windowWidth, double windowHeight) {
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
        if(x == 0){
            playerModel.moveY(y);
        } else {
            playerModel.moveX(x);
        }
    }


    @Override
    public void playerOutOfBounds() {
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
}
