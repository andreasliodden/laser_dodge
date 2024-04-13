package no.uib.inf101.sample.model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import no.uib.inf101.sample.controller.ControllableGameModel;
import no.uib.inf101.sample.model.projectile.Projectile;
import no.uib.inf101.sample.model.projectile.ProjectileFactory;

public class GameModel implements ControllableGameModel {
    private Enemy enemy;
    private Player player;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
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
    public void getNextEnemyImage() {
        enemy.getNextImage();
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

    public BufferedImage getEnemyImage() {
        return enemy.getImage();
    }

    public double getEnemyX() {
        return enemy.getX();
    }

    public double getEnemyY() {
        return enemy.getY();
    }

    public double getProjectileX(int index) {
        return projectiles.get(index).getX();
    }

    public double getProjectileY(int index) {
        return projectiles.get(index).getY();
    }

    public int getNumberOfProjectiles() {
        return projectiles.size();
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
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            projectile.move();
            if (playerProjectileCollision(projectile)) {
                player.isHit();
                projectiles.remove(projectile);
                i--;
            }
        }
    }

    private boolean playerProjectileCollision(Projectile projectile) {
        return Math.abs(projectile.getX() - player.getX()) < 0.02 && Math.abs(projectile.getY() - player.getY()) < 0.02;
    }

    @Override
    public void addProjectile() {
        projectiles.add(factory.getNext());
    }

    public ArrayList<Point2D> getProjectileTrail(int index) {
        return projectiles.get(index).getTrail();
    }
}
