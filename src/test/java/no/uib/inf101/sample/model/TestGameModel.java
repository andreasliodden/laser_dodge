package no.uib.inf101.sample.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.sample.model.projectile.RandomProjectileFactory;
import no.uib.inf101.sample.view.Inf101Graphics;
import no.uib.inf101.sample.model.projectile.ProjectileFactory;

public class TestGameModel {
    private GameModel gameModel;
    @BeforeEach
    void initGameModel() {
        ProjectileFactory factory = new RandomProjectileFactory();
        gameModel = new GameModel(factory);
    }

    @Test
    public void getInitialPlayerPosition() {
        double initialPosition = 0.1;
        assertEquals(gameModel.getPlayerY(), initialPosition);
        assertEquals(gameModel.getPlayerY(), initialPosition);
    }

    @Test
    public void getEnemyPosition() {
        double initialPosition = 0.5;
        assertEquals(gameModel.getEnemyX(), initialPosition);
        assertEquals(gameModel.getEnemyY(), initialPosition);
    
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
        double initialX = gameModel.getPlayerX();
        double initialY = gameModel.getPlayerY();
        double currentX = initialX;
        double currentY = initialY;

        gameModel.movePlayer(1, 0);

        assertTrue(gameModel.getPlayerX() > currentX);
        assertEquals(currentY, gameModel.getPlayerY());
        currentX = gameModel.getPlayerX();

        gameModel.movePlayer(0, -1);

        assertTrue(gameModel.getPlayerY() < currentY);
        assertEquals(currentX, gameModel.getPlayerX());
        currentY = gameModel.getPlayerY();

        gameModel.movePlayer(-1, 0);

        assertTrue(gameModel.getPlayerX() < currentX);
        assertEquals(currentY, gameModel.getPlayerY());
        currentX = gameModel.getPlayerX();

        gameModel.movePlayer(0, 1);
        assertTrue(gameModel.getPlayerY() > currentY);
        assertEquals(currentX, gameModel.getPlayerX());
        currentY = gameModel.getPlayerY();

        assertEquals(initialY, currentY);
        assertEquals(initialX, currentX);
    }

    @Test
    public void playerWindowCollision() {
        while (gameModel.movePlayer(1, 0));
        assertFalse(gameModel.movePlayer(1, 0));
        assertEquals(1, gameModel.getPlayerX());

        while (gameModel.movePlayer(0, 1));
        assertFalse(gameModel.movePlayer(0, 1));
        assertEquals(1, gameModel.getPlayerY());

        while (gameModel.movePlayer(-1, 0));
        assertFalse(gameModel.movePlayer(-1, 0));
        assertEquals(0, gameModel.getPlayerX());

        while (gameModel.movePlayer(0, -1));
        assertFalse(gameModel.movePlayer(0, -1));
        assertEquals(0, gameModel.getPlayerX());
    }

    @Test
    public void playerEnemyCollision() {
        while (gameModel.getPlayerY() < gameModel.getEnemyY()) {
            gameModel.movePlayer(0, 1);
        }
        Rectangle2D restrictedArea = gameModel.getRestrictedEnemyArea();
        
        while(gameModel.movePlayer(1, 0));
        assertFalse(gameModel.movePlayer(1, 0));
        assertTrue(gameModel.getPlayerX() < restrictedArea.getMinX());

        while(gameModel.movePlayer(0, 1));

        while (gameModel.getPlayerX() < gameModel.getEnemyX()) {
            gameModel.movePlayer(1, 0);
        } 
        while(gameModel.movePlayer(0, -1)); 
        assertFalse(gameModel.movePlayer(0, -1));
        assertTrue(gameModel.getPlayerY() > restrictedArea.getMaxY());
    } 

    @Test
    public void enemyImageIsUpdated() {
        BufferedImage enemyImage = gameModel.getEnemyImage();
        gameModel.updateEnemyImage();

        assertNotEquals(enemyImage, gameModel.getEnemyImage());
    }

    @Test
    public void addProjectileAndGapple() {
        BufferedImage goldenApple = Inf101Graphics.loadImageFromResources("golden_apple.png");
        assertEquals(0, gameModel.getNumberOfProjectiles());
        gameModel.addProjectile();
        assertEquals(1, gameModel.getNumberOfProjectiles());
        assertTrue(gameModel.getProjectileX(0) > 0 && gameModel.getProjectileX(0) < 1);
        assertTrue(gameModel.getProjectileY(0) > 0 && gameModel.getProjectileY(0) < 1);

        assertFalse(gameModel.goldenAppleExists());
        gameModel.addGoldenApple();
        assertTrue(gameModel.goldenAppleExists());

        assertTrue(gameModel.getGappleX() > 0 && gameModel.getGappleX() < 1);
        assertTrue(gameModel.getGappleY() > 0 && gameModel.getGappleY() < 1);
        assertEquals(goldenApple.getColorModel(), gameModel.getGappleImage().getColorModel());
        
        
    }

    @Test
    public void clockTick() {
        gameModel.addProjectile();
        gameModel.addGoldenApple();

        double projectileX = gameModel.getProjectileX(0);
        double projectileY = gameModel.getProjectileY(0);

        double gappleX = gameModel.getGappleX();
        double gappleY = gameModel.getGappleY();

        gameModel.clockTick();

        assertNotEquals(gappleX, gameModel.getGappleX());
        assertNotEquals(gappleY, gameModel.getGappleY());
        assertNotEquals(projectileX, gameModel.getProjectileX(0));
        assertNotEquals(projectileY, gameModel.getProjectileY(0));
    }

    @Test
    public void projectileTrail() {
        gameModel.addProjectile();
        ArrayList<Point2D> trail = gameModel.getProjectileTrail(0);
        assertEquals(0, trail.size());

        for (int i = 0; i < 30; i++) {
            gameModel.clockTick();
        }
        assertEquals(25, trail.size());
    }
    
    @Test
    public void enemyImageWhenReadyToShoot() {
        BufferedImage currentImage = gameModel.getEnemyImage();

        gameModel.readyToShoot();
        assertNotEquals(currentImage, gameModel.getEnemyImage());

        currentImage = gameModel.getEnemyImage();

        gameModel.readyToShoot();
        assertNotEquals(currentImage, gameModel.getEnemyImage());
    }

    @Test
    public void playerHealth() {
        assertEquals(50, gameModel.getPlayerHealth());
        gameModel.addProjectile();

        for (int i = 0; i < 10; i++) {
            gameModel.clockTick();
        }

        if (gameModel.getPlayerX() < gameModel.getProjectileX(0)) {
            while (gameModel.getPlayerX() < gameModel.getProjectileX(0)) {
                gameModel.movePlayer(1, 0);
            }
        } else {
            while (gameModel.getPlayerX() > gameModel.getProjectileX(0)) {
                gameModel.movePlayer(-1, 0);
            }
        }

        if (gameModel.getPlayerY() < gameModel.getProjectileY(0)) {
            while (gameModel.getPlayerY() < gameModel.getProjectileY(0)) {
                gameModel.movePlayer(0, 1);
            }
        } else {
            while (gameModel.getPlayerY() > gameModel.getProjectileY(0)) {
                gameModel.movePlayer(0, -1);
            }
        }

        gameModel.clockTick();
        assertEquals(40, gameModel.getPlayerHealth());
    }
}
