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
import no.uib.inf101.sample.model.enemy.EnemyState;
import no.uib.inf101.sample.model.projectile.ProjectileFactory;

public class TestGameModel {
    private final static int ITERATIONS = 5;
    private GameModel gameModel;

    @BeforeEach
    private void initiateGameModel() {
        ProjectileFactory factory = new RandomProjectileFactory();
        gameModel = new GameModel(factory);
    }

    @Test
    public void switchBetweenGameStates() {
        assertEquals(gameModel.getGameState(), GameState.HOME);

        gameModel.setGameState(GameState.ACTIVE_ANGRY);
        assertEquals(gameModel.getGameState(), GameState.ACTIVE_ANGRY);

        gameModel.setGameState(GameState.ACTIVE_HAPPY);
        assertEquals(gameModel.getGameState(), GameState.ACTIVE_HAPPY);

        gameModel.setGameState(GameState.PAUSED);
        assertEquals(gameModel.getGameState(), GameState.PAUSED);
    }

    @Test
    public void canMovePlayerAllDirections() {
        assert (gameModel.movePlayer(1, 0));
        assert (gameModel.movePlayer(-1, 0));
        assert (gameModel.movePlayer(0, 1));
        assert (gameModel.movePlayer(0, -1));
    }

    @Test
    public void playerEnemyCollision() {
        ViewablePlayer player = gameModel.getViewablePlayer();
        ViewableEnemy enemy = gameModel.getViewableEnemy();
        while (player.getY() < enemy.getY()) {
            gameModel.movePlayer(0, 1);
        }

        // Player is blocked from going right
        while (gameModel.movePlayer(1, 0));
        assertTrue(player.getX() < enemy.getX());

        while (gameModel.movePlayer(0, 1));

        while (player.getX() < enemy.getX()) {
            gameModel.movePlayer(1, 0);
        }

        // Player is blocked from going up
        while (gameModel.movePlayer(0, -1));
        assertTrue(player.getY() > enemy.getY());
    }

    @Test
    public void addProjectile() {
        assertEquals(0, gameModel.getNumberOfProjectiles());
        gameModel.addProjectile();
        assertEquals(1, gameModel.getNumberOfProjectiles());
        gameModel.addProjectile();
        assertEquals(2, gameModel.getNumberOfProjectiles());
    }

    @Test
    public void addGappleWhenCountdownIsZero() {
        gameModel.startNewGame();
        assertFalse(gameModel.gappleExists());
        
        while (gameModel.getGappleCountdown() > 0) {
            gameModel.updateGappleCountdown();
        }
        assertEquals(0, gameModel.getGappleCountdown());
        assert(gameModel.gappleExists());

        assert(gameModel.getGappleX() >= 0 && gameModel.getGappleX() <= 1);
        assert(gameModel.getGappleY() >= 0 && gameModel.getGappleY() <= 1);
    }

    @Test
    public void clockTick() {
        gameModel.startNewGame();
        gameModel.addProjectile();
        
        while (gameModel.getGappleCountdown() > 0) {
            gameModel.updateGappleCountdown();
        }

        ViewableProjectile projectile = gameModel.getProjectile(0);
        double projectileX = projectile.getX();
        double projectileY = projectile.getY();

        double gappleX = gameModel.getGappleX();
        double gappleY = gameModel.getGappleY();

        gameModel.clockTick();

        // Gapple and projectile has been moved
        assertNotEquals(gappleX, gameModel.getGappleX());
        assertNotEquals(gappleY, gameModel.getGappleY());
        assertNotEquals(projectileX, projectile.getX());
        assertNotEquals(projectileY, projectile.getX());
    }

    @Test
    public void getProjectiles() {
        double initialPosition = 0.5;

        gameModel.addProjectile();
        gameModel.addProjectile();

        ViewableProjectile firstProjectile = gameModel.getProjectile(0);
        assertEquals(initialPosition, firstProjectile.getX());
        assertEquals(initialPosition, firstProjectile.getY());

        ViewableProjectile secondProjectile = gameModel.getProjectile(1);
        assertEquals(initialPosition, secondProjectile.getX());
        assertEquals(initialPosition, secondProjectile.getY());

        assertThrows(IndexOutOfBoundsException.class, () -> gameModel.getProjectile(2));
    }

    @Test
    public void getAndUpdateScore() {
        gameModel.addTimeScore();
        gameModel.addTimeScore();

        int currentScore = 2;
        assertEquals(currentScore, gameModel.getScore());

        int projectileScore = 10;

        for (int i = 0; i < ITERATIONS; i++) {
            gameModel.addProjectile();
        }

        currentScore += ITERATIONS * projectileScore;
        assertEquals(currentScore, gameModel.getScore());

        for (int i = 0; i < ITERATIONS; i++) {
            gameModel.addTimeScore();
        }

        currentScore += ITERATIONS * ITERATIONS;
        assertEquals(currentScore, gameModel.getScore());
    }

    @Test
    public void initiateNewGame() {
        gameModel.startNewGame();

        assertEquals(GameState.ACTIVE_ANGRY, gameModel.getGameState());
        assertEquals(0, gameModel.getNumberOfProjectiles());
        assertEquals(0, gameModel.getScore());
        assertEquals(gameModel.getGappleCooldown(), gameModel.getGappleCountdown());
        assertFalse(gameModel.gappleExists());

    }

    @Test
    public void gappleCountdown() {
        gameModel.startNewGame();

        int countdownStart = gameModel.getGappleCooldown();
        assertEquals(40, countdownStart);


        for (int i = 0; i < ITERATIONS; i++) {
            gameModel.updateGappleCountdown();
        }
        assertEquals(countdownStart - ITERATIONS, gameModel.getGappleCountdown());
        gameModel.resetGapple();
        assertEquals(countdownStart, gameModel.getGappleCountdown());

        while (gameModel.getGappleCountdown() > 0) {
            gameModel.updateGappleCountdown();
        }
        assertEquals(0, gameModel.getGappleCountdown());

        // Control that the countdown can't be less than zero
        gameModel.updateGappleCountdown();
        assertEquals(0, gameModel.getGappleCountdown());
    }

    @Test
    public void getViewablePlayer() {
        ViewablePlayer player = gameModel.getViewablePlayer();
        double initialPosition = 0.2;
        
        assertTrue(player instanceof ViewablePlayer);
        assertEquals(initialPosition, player.getX());
        assertEquals(initialPosition, player.getY());
    }

    @Test
    public void getViewableEnemy() {
        ViewableEnemy enemy = gameModel.getViewableEnemy();
        double initialPosition = 0.5;

        assertTrue(enemy instanceof ViewableEnemy);
        assertEquals(initialPosition, enemy.getX());
        assertEquals(initialPosition, enemy.getY());
    }

    @Test
    public void getControllableEnemy() {
        ControllableEnemy enemy = gameModel.getControllableEnemy();

        assertTrue(enemy instanceof ControllableEnemy);
        assertEquals(EnemyState.ANGRY_ONE, enemy.getEnemyState());
    }
}
