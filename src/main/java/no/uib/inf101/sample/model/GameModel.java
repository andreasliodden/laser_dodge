package no.uib.inf101.sample.model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import java.util.Iterator;

import no.uib.inf101.sample.controller.ControllableEnemy;
import no.uib.inf101.sample.controller.ControllableGameModel;
import no.uib.inf101.sample.model.enemy.Enemy;
import no.uib.inf101.sample.model.player.Player;
import no.uib.inf101.sample.model.projectile.GoldenApple;
import no.uib.inf101.sample.model.projectile.Projectile;
import no.uib.inf101.sample.model.projectile.ProjectileFactory;
import no.uib.inf101.sample.view.interfaces.ViewableEnemy;
import no.uib.inf101.sample.view.interfaces.ViewableGameModel;
import no.uib.inf101.sample.view.interfaces.ViewablePlayer;
import no.uib.inf101.sample.view.interfaces.ViewableProjectile;

public class GameModel implements ControllableGameModel, ViewableGameModel {
    private Enemy enemy;
    private Player player;
    private ArrayList<Projectile> activeProjectiles = new ArrayList<>();
    private ProjectileFactory factory;
    private GameState gameState;
    private GoldenApple goldenApple;
    private boolean goldenAppleExists;
    private int gameScore;

    public GameModel(ProjectileFactory factory) {
        this.factory = factory;
        this.enemy = new Enemy();
        this.player = new Player();
        this.gameState = GameState.ACTIVE_ENEMY;
        this.goldenAppleExists = false;
        this.gameScore = 0;
    }

    @Override
    public GameState getCurrentState() {
        return this.gameState;
    }

    @Override
    public boolean movePlayer(int deltaX, int deltaY) {
        double nextX = player.getNextX(deltaX);
        double nextY = player.getNextY(deltaY);
        if (!playerEnemyCollision(nextX, nextY)) {
            return player.move(deltaX, deltaY);
        }
        return false;
    }

    @Override
    public void clockTick() {
        if (goldenAppleExists) {
            goldenApple.move();
            if (playerProjectileCollision(goldenApple.getX(), goldenApple.getY())) {
                updateGameState();
                goldenAppleExists = false;
            }
        }
        Iterator<Projectile> iterator = activeProjectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.move();
            if (playerProjectileCollision(projectile.getX(), projectile.getY())) {
                player.registerHit(gameState);
                iterator.remove();
                if (gameState == GameState.ACTIVE_FRIENDLY) {
                    gameScore += 10;
                }
            }
        }
    }

    @Override
    public void addProjectile() {
        enemy.updateShootingStatus();
        activeProjectiles.add(factory.getNextProjectile());
        gameScore += 10;
    }

    @Override
    public void updateGameState() {
        if (gameState == GameState.ACTIVE_ENEMY) {
            gameState = GameState.ACTIVE_FRIENDLY;
        } else {
            gameState = GameState.ACTIVE_ENEMY;
        }

        enemy.switchMood();
    }

    @Override
    public int getNumberOfProjectiles() {
        return activeProjectiles.size();
    }

    @Override
    public double getGappleX(){
        return goldenApple.getX();
    }

    @Override
    public double getGappleY(){
        return goldenApple.getY();
    }

    private boolean playerProjectileCollision(double projectileX, double projectileY) {
        double collisionRoom = 0.025;
        double projectilePlayerX = Math.abs(projectileX - player.getX());
        double projectilePlayerY = Math.abs(projectileY - player.getY());
        return projectilePlayerX < collisionRoom && projectilePlayerY < collisionRoom;
    }

    public boolean playerEnemyCollision(double playerX, double playerY) {
        Rectangle2D restricedArea = enemy.getRestricedArea();
        return restricedArea.contains(playerX, playerY);
    }

    @Override
    public void addGoldenApple() {
        if (!goldenAppleExists) {
            goldenApple = factory.getGoldenApple();
            goldenAppleExists = true;
        }
    }

    @Override
    public boolean goldenAppleExists() {
        return this.goldenAppleExists;
    }

    @Override
    public ViewablePlayer getViewablePlayer() {
        return this.player;
    }

    @Override
    public ViewableEnemy getViewableEnemy() {
        return this.enemy;
    }

    @Override
    public ViewableProjectile getProjectile(int index) {
        return activeProjectiles.get(index);
    }

    @Override
    public ControllableEnemy getControllableEnemy() {
        return this.enemy;
    }

    @Override
    public int getScore() {
        return this.gameScore;
    }

    @Override
    public void addTimeScore() {
        gameScore += 2;
    }


}
