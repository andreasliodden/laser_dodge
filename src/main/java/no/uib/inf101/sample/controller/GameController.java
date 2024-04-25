package no.uib.inf101.sample.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.view.GameView;

/**
 * Responsible for managing user input and
 * game logic in the game.
 * Implements the KeyListener interface to handle keyboard events.
 */

public class GameController implements KeyListener {
    private static final int TIME_DELAY = 10;
    private static final int UPDATE_ENEMY_TICK = 30;
    private static final int ADD_SCORE_TICK = 100;
    private static final int GAPPLE_COUNTDOWN_TICK = 100;
    private static final int ADD_PROJECTILE_TICK = 450;
    private static final int ENEMY_READY_TICK = ADD_PROJECTILE_TICK - 2 * UPDATE_ENEMY_TICK;
    private static final int RESET_GAME_TICK = 700;
    private static final int TICK_DIVISOR = 3;

    private ControllableGameModel gameModel;
    private ControllableEnemy enemy;
    private GameView gameView;
    private Timer timer;
    private boolean playerUp, playerDown, playerLeft, playerRight;
    private GameState currentGameState, previousGameState;
    private int tickCounter;

    public GameController(ControllableGameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.enemy = gameModel.getControllableEnemy();
        this.gameView = gameView;
        this.timer = new Timer(TIME_DELAY, e -> clockTick());
        this.tickCounter = 1;
        gameView.setFocusable(true);
        gameView.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentGameState = gameModel.getGameState();
        int keyCode = e.getKeyCode();
        switch (currentGameState) {
            case HOME -> handleHomeState(keyCode);
            case CONTROLS -> gameModel.setGameState(GameState.HOME);
            case ACTIVE_ANGRY, ACTIVE_HAPPY -> handleActiveState(keyCode);
            case PAUSED -> handlePauseState(keyCode);
            case GAME_OVER -> handleGameOver(keyCode);
            default -> throw new IllegalArgumentException("'" + currentGameState + "' is not a known game state.");
        }
        gameView.repaint();
    }

    private void handleHomeState(int keyCode) {
        if (keyCode == KeyEvent.VK_S) {
            gameModel.startNewGame();
            timer.start();
            resetTickCounter();
        } else if (keyCode == KeyEvent.VK_C) {
            gameModel.setGameState(GameState.CONTROLS);
        }
    }

    private void handleActiveState(int keyCode) {
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            playerUp = true;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            playerDown = true;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            playerLeft = true;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            playerRight = true;
        } else if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) {
            enemy.updatePause();
            gameModel.setGameState(GameState.PAUSED);
            timer.stop();
        }
    }

    private void handleGameOver(int keyCode) {
        if (keyCode == KeyEvent.VK_H) {
            gameModel.setGameState(GameState.HOME);
        } else if (keyCode == KeyEvent.VK_R) {
            gameModel.startNewGame();
            timer.restart();
            resetTickCounter();
        }
    }

    private void handlePauseState(int keyCode) {
        if (previousGameState == GameState.ACTIVE_ANGRY) {
            gameModel.setGameState(GameState.ACTIVE_ANGRY);
        } else {
            gameModel.setGameState(GameState.ACTIVE_HAPPY);
        }
        timer.start();
        enemy.updatePause();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            playerUp = false;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            playerDown = false;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            playerLeft = false;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            playerRight = false;
        }
    }

    private void clockTick() {
        currentGameState = gameModel.getGameState();

        if (currentGameState == GameState.GAME_OVER) {
            timer.stop();
        } else {
            // Checks if the game state is the same as last clock tick
            if (previousGameState != null && currentGameState != previousGameState) {
                resetTickCounter();
            }
            previousGameState = currentGameState;

            if (tickCounter % UPDATE_ENEMY_TICK == 0) {
                enemy.updateState();
            }
            if (tickCounter % ADD_SCORE_TICK == 0) {
                gameModel.addTimeScore();
            }

            if (currentGameState == GameState.ACTIVE_ANGRY) {
                angryEnemyTick();
            } else if (currentGameState == GameState.ACTIVE_HAPPY) {
                happyEnemyTick();
            }

            handlePlayerMovement();
            gameModel.clockTick();
            tickCounter++;
        }
        gameView.repaint();
    }

    private void angryEnemyTick() {
        if (tickCounter % GAPPLE_COUNTDOWN_TICK == 0) {
            gameModel.updateGappleCountdown();
        }
        if (tickCounter % ADD_PROJECTILE_TICK == ENEMY_READY_TICK) {
            enemy.updateShootingStatus();
        } else if (tickCounter % ADD_PROJECTILE_TICK == 0) {
            gameModel.addProjectile();
        }
        if (tickCounter == gameModel.getGappleCooldown() * 100) {
            gameModel.addGapple();
        }
    }

    private void happyEnemyTick() {
        int newProjectileTick = ADD_PROJECTILE_TICK / TICK_DIVISOR;

        if (tickCounter % newProjectileTick == newProjectileTick - 2 * UPDATE_ENEMY_TICK) {
                enemy.updateShootingStatus();
        } else if (tickCounter % newProjectileTick == 0) {
                gameModel.addProjectile();
        }
        if (tickCounter == RESET_GAME_TICK) {
            gameModel.setGameState(GameState.ACTIVE_ANGRY);
            gameModel.resetGapple();
            enemy.switchMood();
        }
    }

    private void handlePlayerMovement() {
        if ((playerUp && playerDown) || (playerLeft && playerRight)) {
            return;
        }
        if (playerUp) {
            gameModel.movePlayer(0, -1);
        } else if (playerDown) {
            gameModel.movePlayer(0, 1);
        }
        if (playerLeft) {
            gameModel.movePlayer(-1, 0);
        } else if (playerRight) {
            gameModel.movePlayer(1, 0);
        }
    }

    private void resetTickCounter() {
        this.tickCounter = 1;
    }
}
