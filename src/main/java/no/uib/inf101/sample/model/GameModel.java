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
    private GoldenApple goldenApple;
    private boolean goldenAppleExists;

    public GameModel(ProjectileFactory factory) {
        this.factory = factory;
        this.enemy = new Enemy();
        this.player = new Player();
        this.gameState = GameState.ACTIVE_ENEMY;
        this.goldenAppleExists = false;
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
            player.move(deltaX, deltaY, nextX, nextY);
        }
    }

    @Override
    public void updateEnemyImage() {
        enemy.updateState();
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
            }
        }
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
        if (gameState == GameState.ACTIVE_ENEMY) {
            gameState = GameState.ACTIVE_FRIENDLY;
        } else {
            gameState = GameState.ACTIVE_ENEMY;
        }

        enemy.switchGameState();
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

    public int getPlayerHealth() {
        return player.getPlayerHealth();
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

    public double getGappleX(){
        return goldenApple.getX();
    }

    public double getGappleY(){
        return goldenApple.getY();
    }

    public BufferedImage getGappleImage(){
        return goldenApple.getImage();
    }

    private boolean playerProjectileCollision(double projectileX, double projectileY) {
        double collisionRoom = 0.025;
        double projectilePlayerX = Math.abs(projectileX- player.getX());
        double projectilePlayerY = Math.abs(projectileY - player.getY());
        return projectilePlayerX < collisionRoom && projectilePlayerY < collisionRoom;
    }

    private boolean playerEnemyCollision(double playerX, double playerY) {
        Rectangle2D restricedArea = enemy.getRestricedArea();
        return restricedArea.contains(playerX, playerY);
    }

    @Override
    public void addGoldenApple() {
        if (!goldenAppleExists) {
            goldenApple = GoldenApple.createNewApple();
            goldenAppleExists = true;
        }
    }

    public boolean goldenAppleExists() {
        return goldenAppleExists;
    }

    public ArrayList<Point2D> getGappleTrail() {
        return goldenApple.getTrail();
    }
}
