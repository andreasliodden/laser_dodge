package no.uib.inf101.sample.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import no.uib.inf101.sample.model.projectile.RandomProjectileFactory;
import no.uib.inf101.sample.view.interfaces.ViewableEnemy;
import no.uib.inf101.sample.view.interfaces.ViewablePlayer;
import no.uib.inf101.sample.view.interfaces.ViewableProjectile;
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
        assertEquals(gameModel.getCurrentState(), GameState.ACTIVE_ENEMY);

        gameModel.updateGameState();
        assertEquals(gameModel.getCurrentState(), GameState.ACTIVE_FRIENDLY);

        gameModel.updateGameState();
        assertEquals(gameModel.getCurrentState(), GameState.ACTIVE_ENEMY);
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
    public void playerWindowCollision() {
        ViewablePlayer player = gameModel.getViewablePlayer();
        while (gameModel.movePlayer(1, 0));
        assertFalse(gameModel.movePlayer(1, 0));
        assertEquals(1, player.getX());

        while (gameModel.movePlayer(0, 1));
        assertFalse(gameModel.movePlayer(0, 1));
        assertEquals(1, player.getY());

        while (gameModel.movePlayer(-1, 0));
        assertFalse(gameModel.movePlayer(-1, 0));
        assertEquals(0, player.getX());

        while (gameModel.movePlayer(0, -1));
        assertFalse(gameModel.movePlayer(0, -1));
        assertEquals(0, player.getX());
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
}
