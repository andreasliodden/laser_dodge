package no.uib.inf101.sample.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import no.uib.inf101.sample.model.projectile.RandomProjectileFactory;
import no.uib.inf101.sample.view.viewable.ViewableEnemy;
import no.uib.inf101.sample.view.viewable.ViewablePlayer;
import no.uib.inf101.sample.view.viewable.ViewableProjectile;
import no.uib.inf101.sample.controller.ControllableEnemy;
import no.uib.inf101.sample.model.projectile.ProjectileFactory;

public class TestGameModel {
    private GameModel gameModel;
    @BeforeEach
    void initGameModel() {
        ProjectileFactory factory = new RandomProjectileFactory();
        gameModel = new GameModel(factory);
    }

    @Test
    public void switchBetweenGameStates() {
        assertEquals(gameModel.getCurrentState(), GameState.HOME);

        gameModel.setGameState(GameState.ACTIVE_ANGRY);
        assertEquals(gameModel.getCurrentState(), GameState.ACTIVE_ANGRY);

        gameModel.setGameState(GameState.ACTIVE_HAPPY);
        assertEquals(gameModel.getCurrentState(), GameState.ACTIVE_HAPPY);

        gameModel.setGameState(GameState.PAUSED);
        assertEquals(gameModel.getCurrentState(), GameState.PAUSED);
    }

    @Test
    public void movePlayerAllDirections() {
        assert(gameModel.movePlayer(1, 0));
        assert(gameModel.movePlayer(-1, 0));
        assert(gameModel.movePlayer(0, 1));
        assert(gameModel.movePlayer(0, -1));
    }

    @Test
    public void playerEnemyCollision() {
        ViewablePlayer player = gameModel.getViewablePlayer();
        ViewableEnemy enemy = gameModel.getViewableEnemy();
        while (player.getY() < enemy.getY()) {
            gameModel.movePlayer(0, 1);
        }
        
        while(gameModel.movePlayer(1, 0));
        assertFalse(gameModel.movePlayer(1, 0));
        assertTrue(player.getX() < enemy.getX());

        while(gameModel.movePlayer(0, 1));

        while (player.getX() < enemy.getX()) {
            gameModel.movePlayer(1, 0);
        } 
        while(gameModel.movePlayer(0, -1)); 
        assertFalse(gameModel.movePlayer(0, -1));
        assertTrue(player.getY() > enemy.getY());
    } 

    @Test
    public void addProjectileAndGapple() {
        assertEquals(0, gameModel.getNumberOfProjectiles());
        gameModel.addProjectile();
        assertEquals(1, gameModel.getNumberOfProjectiles());
        gameModel.addProjectile();
        assertEquals(2, gameModel.getNumberOfProjectiles());

        assertFalse(gameModel.gappleExists());
        gameModel.addGapple();
        assertTrue(gameModel.gappleExists());

        assertTrue(gameModel.getGappleX() > 0 && gameModel.getGappleX() < 1);
        assertTrue(gameModel.getGappleY() > 0 && gameModel.getGappleY() < 1);
    }

    @Test
    public void clockTick() {
        gameModel.addProjectile();
        gameModel.addGapple();

        ViewableProjectile projectile = gameModel.getProjectile(0);
        double projectileX = projectile.getX();
        double projectileY = projectile.getY();

        double gappleX = gameModel.getGappleX();
        double gappleY = gameModel.getGappleY();

        gameModel.clockTick();

        assertNotEquals(gappleX, gameModel.getGappleX());
        assertNotEquals(gappleY, gameModel.getGappleY());
        assertNotEquals(projectileX, projectile.getX());
        assertNotEquals(projectileY, projectile.getX());
    }

    @Test
    public void getViewablePlayer() {
        ViewablePlayer player = gameModel.getViewablePlayer();
        assertTrue(player instanceof ViewablePlayer);
    }

    @Test
    public void getViewableEnemy() {
        ViewableEnemy enemy = gameModel.getViewableEnemy();
        assertTrue(enemy instanceof ViewableEnemy);
    }

    @Test
    public void getControllableEnemy() {
        ControllableEnemy enemy = gameModel.getControllableEnemy();
        assertTrue(enemy instanceof ControllableEnemy);
    }

    @Test
    public void getProjectiles() {
        gameModel.addProjectile();
        gameModel.addProjectile();

        assertTrue(gameModel.getProjectile(0) instanceof ViewableProjectile);
        assertTrue(gameModel.getProjectile(1) instanceof ViewableProjectile);
        assertThrows(IndexOutOfBoundsException.class, () -> gameModel.getProjectile(2));
    }

    @Test
    public void getAndUpdateScore() {
        gameModel.addTimeScore();
        gameModel.addTimeScore();

        int currentScore = 2;
        assertEquals(currentScore, gameModel.getScore());

        int iterations = 5;
        int projectileScore = 10;

        for (int i = 0; i < iterations; i++) {
            gameModel.addProjectile();
        }

        currentScore += iterations * projectileScore;
        assertEquals(currentScore, gameModel.getScore());

        for (int i = 0; i < iterations; i++) {
            gameModel.addTimeScore();
        }

        currentScore += iterations * iterations;
        assertEquals(currentScore, gameModel.getScore());
    }

    @Test
    public void initiateNewGame() {
        gameModel.startNewGame();
        assertEquals(40, gameModel.getGappleCountdown());
        assertFalse(gameModel.gappleExists());
        assertEquals(0, gameModel.getScore());
        assertEquals(GameState.ACTIVE_ANGRY, gameModel.getCurrentState());
        assertEquals(0, gameModel.getNumberOfProjectiles());


    }

    @Test
    public void gappleCountdown() {
        gameModel.startNewGame();
        
        int countdownStart = gameModel.getGappleCooldown();
        assertEquals(40, countdownStart);

        int iterations = 5;

        for (int i = 0; i < iterations; i++) {
            gameModel.updateGappleCountdown();
        }
        assertEquals(countdownStart - iterations, gameModel.getGappleCountdown());
        gameModel.resetGapple();
        assertEquals(countdownStart, gameModel.getGappleCountdown());

        while (gameModel.getGappleCountdown() > 0) {
            gameModel.updateGappleCountdown();
        }
        assertEquals(0, gameModel.getGappleCountdown());
        gameModel.updateGappleCountdown();
        assertEquals(0, gameModel.getGappleCountdown());
    }
}
