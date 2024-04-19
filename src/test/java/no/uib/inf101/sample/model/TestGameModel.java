package no.uib.inf101.sample.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import no.uib.inf101.sample.model.projectile.RandomProjectileFactory;
import no.uib.inf101.sample.view.interfaces.ViewableEnemy;
import no.uib.inf101.sample.view.interfaces.ViewablePlayer;
import no.uib.inf101.sample.view.interfaces.ViewableProjectile;
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

        gameModel.setGameState(GameState.ACTIVE_ENEMY);
        assertEquals(gameModel.getCurrentState(), GameState.ACTIVE_ENEMY);

        gameModel.setGameState(GameState.ACTIVE_FRIENDLY);
        assertEquals(gameModel.getCurrentState(), GameState.ACTIVE_FRIENDLY);

        gameModel.setGameState(GameState.PAUSED);
        assertEquals(gameModel.getCurrentState(), GameState.PAUSED);
    }

    @Test
    public void playerCanMoveAllDirections() {
        ViewablePlayer player = gameModel.getViewablePlayer();
        double initialX = player.getX();
        double initialY = player.getY();
        double currentX = initialX;
        double currentY = initialY;

        gameModel.movePlayer(1, 0);

        assertTrue(player.getX() > currentX);
        assertEquals(currentY, player.getY());
        currentX = player.getX();

        gameModel.movePlayer(0, -1);

        assertTrue(player.getY() < currentY);
        assertEquals(currentX, player.getX());
        currentY = player.getY();

        gameModel.movePlayer(-1, 0);

        assertTrue(player.getX() < currentX);
        assertEquals(currentY, player.getY());
        currentX = player.getX();

        gameModel.movePlayer(0, 1);
        assertTrue(player.getY() > currentY);
        assertEquals(currentX, player.getX());
        currentY = player.getY();

        assertEquals(initialY, currentY);
        assertEquals(initialX, currentX);
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

        assertFalse(gameModel.goldenAppleExists());
        gameModel.addGoldenApple();
        assertTrue(gameModel.goldenAppleExists());

        assertTrue(gameModel.getGappleX() > 0 && gameModel.getGappleX() < 1);
        assertTrue(gameModel.getGappleY() > 0 && gameModel.getGappleY() < 1);
    }

    @Test
    public void clockTick() {
        gameModel.addProjectile();
        gameModel.addGoldenApple();

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
        assertEquals(0, gameModel.getScore());

        int iterations = 5;
        for (int i = 0; i < iterations; i++) {
            gameModel.addTimeScore();
        }
        int currentScore = iterations * 2;
        assertEquals(currentScore, gameModel.getScore());

        gameModel.addProjectile();
        assertEquals(currentScore + 10, gameModel.getScore());
    }
}
