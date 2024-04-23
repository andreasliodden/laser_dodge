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
import no.uib.inf101.sample.view.viewable.ViewableEnemy;
import no.uib.inf101.sample.view.viewable.ViewableGameModel;
import no.uib.inf101.sample.view.viewable.ViewablePlayer;
import no.uib.inf101.sample.view.viewable.ViewableProjectile;

public class GameModel implements ControllableGameModel, ViewableGameModel {
    private Enemy enemy;
    private Player player;
    private ArrayList<Projectile> activeProjectiles = new ArrayList<>();
    private ProjectileFactory factory;
    private GameState gameState;
    private GoldenApple gapple;
    private boolean gappleExists;
    private int gameScore;
    private int gappleCountdown;

    private static final int GAPPLE_COOLDOWN = 40;
    private static final int PROJECTILE_POINTS = 10;
    private static final double COLLISION_ROOM = 0.035;

    public GameModel(ProjectileFactory factory) {
        this.factory = factory;
        this.gameState = GameState.HOME;
        this.enemy = new Enemy();
        this.player = new Player();
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
        if (gappleExists) {
            gapple.move();
            if (playerProjectileCollision(gapple.getX(), gapple.getY())) {
                enemy.switchMood();
                gameState = GameState.ACTIVE_HAPPY;
                gappleExists = false;
            }
        }
        Iterator<Projectile> iterator = activeProjectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.move();
            if (playerProjectileCollision(projectile.getX(), projectile.getY())) {
                player.registerHit(gameState);
                iterator.remove();
                if (gameState == GameState.ACTIVE_HAPPY) {
                    gameScore += PROJECTILE_POINTS;
                } else if (player.getHealth() == 0) {
                    gameState = GameState.GAME_OVER;
                }
            }
        }
    }

    @Override
    public void resetGapple() {
        this.gappleCountdown = GAPPLE_COOLDOWN;
        this.gappleExists = false;
    }

    @Override
    public void addProjectile() {
        enemy.updateShootingStatus();
        activeProjectiles.add(factory.getNextProjectile());
        gameScore += PROJECTILE_POINTS;
    }

    @Override
    public void setGameState(GameState nextState) {
        this.gameState = nextState;
        if (nextState == GameState.HOME) {
            enemy.reset();
        }
    }

    @Override
    public int getNumberOfProjectiles() {
        return activeProjectiles.size();
    }

    @Override
    public double getGappleX(){
        return gapple.getX();
    }

    @Override
    public double getGappleY(){
        return gapple.getY();
    }

    private boolean playerProjectileCollision(double projectileX, double projectileY) {
        double collisionRoom = COLLISION_ROOM;
        double projectilePlayerX = getAbsoluteDiff(projectileX, player.getX());
        double projectilePlayerY = getAbsoluteDiff(projectileY, player.getY());

        return projectilePlayerX < collisionRoom && projectilePlayerY < collisionRoom;
    }


    private double getAbsoluteDiff(double x1, double x2) {
        return Math.abs(x1 - x2);
    }

    private boolean playerEnemyCollision(double playerX, double playerY) {
        Rectangle2D restricedArea = enemy.getRestricedArea();
        return restricedArea.contains(playerX, playerY);
    }

    @Override
    public void addGapple() {
        if (!gappleExists) {
            gapple = factory.getGoldenApple();
            gappleExists = true;
        }
    }

    @Override
    public boolean gappleExists() {
        return this.gappleExists;
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
        gameScore += Math.max(1, activeProjectiles.size());
    }

    @Override
    public int getGappleCountdown() {
        return this.gappleCountdown;
    }

    @Override
    public void updateGappleCountdown() {
        if (gappleCountdown > 0) {
            gappleCountdown -= 1;
        }
    }

    @Override
    public void startNewGame() {
        this.gameState = GameState.ACTIVE_ANGRY;
        this.gameScore = 0;
        this.activeProjectiles = new ArrayList<>();
        resetGapple();
        player.reset();
        enemy.reset();
    }

    @Override
    public int getGappleCooldown() {
        return GAPPLE_COOLDOWN;
    }
}
