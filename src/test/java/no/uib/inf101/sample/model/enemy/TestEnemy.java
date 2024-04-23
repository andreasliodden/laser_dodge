package no.uib.inf101.sample.model.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestEnemy {
    private Enemy enemy;
    @BeforeEach
    private void initGameModel() {
        enemy = new Enemy();
    }

    @Test
    public void initialPositionAndState() {
        double initPosition = 0.50;
        assertEquals(initPosition, enemy.getX());
        assertEquals(initPosition, enemy.getY());
        assertEquals(EnemyState.ANGRY_ONE, enemy.getCurrentState());
    }

    @Test
    public void restrictedArea() {
        Rectangle2D restrictedArea = enemy.getRestricedArea();
        assertEquals(enemy.getX(), restrictedArea.getCenterX());
        assertEquals(enemy.getY(), restrictedArea.getCenterY());
    }

    @Test
    public void getAndUpdateState() {
        enemy.updateState();
        assertEquals(EnemyState.ANGRY_TWO, enemy.getCurrentState());
        enemy.updateShootingStatus();
        assertEquals(EnemyState.ANGRY_READY, enemy.getCurrentState());

        enemy.switchMood();

        assertEquals(EnemyState.HAPPY_ONE, enemy.getCurrentState());
        enemy.updateState();
        assertEquals(EnemyState.HAPPY_TWO, enemy.getCurrentState());
        enemy.updateShootingStatus();
        assertEquals(EnemyState.HAPPY_READY, enemy.getCurrentState());

        enemy.switchMood();

        assertEquals(EnemyState.ANGRY_ONE, enemy.getCurrentState());
    }

    @Test
    public void resetEnemy() {
        enemy.updateState();
        enemy.switchMood();
        enemy.updateShootingStatus();
        
        enemy.reset();
        assertEquals(EnemyState.ANGRY_ONE, enemy.getCurrentState());
    }

    @Test
    public void pauseAndResume() {
        enemy.updateShootingStatus();
        enemy.updatePause();

        assertEquals(EnemyState.ANGRY_PAUSED, enemy.getCurrentState());
        enemy.updatePause();
        assertEquals(EnemyState.ANGRY_READY, enemy.getCurrentState());

        enemy.switchMood();

        enemy.updatePause();
        assertEquals(EnemyState.HAPPY_PAUSED, enemy.getCurrentState());
        enemy.updatePause();
        assertEquals(EnemyState.HAPPY_ONE, enemy.getCurrentState());

    }
}
