package no.uib.inf101.sample.model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.util.Iterator;

import no.uib.inf101.sample.controller.ControllableGameModel;
import no.uib.inf101.sample.model.projectile.Projectile;
import no.uib.inf101.sample.model.projectile.ProjectileFactory;

public class GameModel implements ControllableGameModel {
    private Enemy enemy;
    private Player player;
    private ArrayList<Projectile> activeProjectiles = new ArrayList<>();
    private ProjectileFactory factory;
    private GameState gameState;

    public GameModel(ProjectileFactory factory) {
        this.factory = factory;
        this.enemy = new Enemy();
        this.player = new Player();
        this.gameState = GameState.ACTIVE;
    }

    @Override
    public GameState getCurrentState() {
        return this.gameState;
    }

    @Override
    public void movePlayer(int deltaX, int deltaY) {
        double nextX = player.getNextX(deltaX);
        double nextY = player.getNextY(deltaY);
        if (!playerEnemyCollision(nextX, nextY)) {
            player.move(deltaX, deltaY);
        }
    }

    @Override
    public void updateEnemyImage() {
        enemy.updateImage();
    }

    public double getPlayerX() {
        return player.getX();
    }

    public double getPlayerY() {
        return player.getY();
    }

    public BufferedImage getPlayerImage() {
        return player.getImage();
    }

    public double getEnemyX() {
        return enemy.getX();
    }

    public double getEnemyY() {
        return enemy.getY();
    }

    public BufferedImage getEnemyImage() {
        return enemy.getImage();
    }

    public double getProjectileX(int index) {
        return activeProjectiles.get(index).getX();
    }

    public double getProjectileY(int index) {
        return activeProjectiles.get(index).getY();
    }

    public int getNumberOfProjectiles() {
        return activeProjectiles.size();
    }

    public int getPlayerHealth() {
        return player.getPlayerHealth();
    }


    private boolean playerEnemyCollision(double playerX, double playerY) {
        Rectangle2D restricedArea = enemy.getRestricedArea();
        return restricedArea.contains(playerX, playerY);
    }

    @Override
    public void clockTick() {
        Iterator<Projectile> iterator = activeProjectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.move();
            if (playerProjectileCollision(projectile)) {
                player.registerHit(gameState);
                iterator.remove();
            }
        }
    }

    private boolean playerProjectileCollision(Projectile projectile) {
        double collisionRoom = 0.02;
        double projectilePlayerX = Math.abs(projectile.getX() - player.getX());
        double projectilePlayerY = Math.abs(projectile.getY() - player.getY());
        return projectilePlayerX < collisionRoom && projectilePlayerY < collisionRoom;
    }

    @Override
    public void addProjectile() {
        enemy.updateShootingStatus();
        activeProjectiles.add(factory.getNext());
    }

    public ArrayList<Point2D> getProjectileTrail(int index) {
        return activeProjectiles.get(index).getTrail();
    }

    @Override
    public void readyToShoot() {
        enemy.updateShootingStatus();
    }

    @Override
    public void updateGameState() {
        if (gameState == GameState.ACTIVE) {
            gameState = GameState.EATING;
        } else {
            gameState = GameState.ACTIVE;
        }

        enemy.updateState();
    }

    
}
