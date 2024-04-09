package no.uib.inf101.sample.model;

public class GameModel{
    private EnemyModel enemyModel;

    public GameModel() {
        this.enemyModel = new EnemyModel();
    }

    public EnemyModel getEnemyModel() {
        return enemyModel;
    }
}
