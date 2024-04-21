package no.uib.inf101.sample.controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.view.GameView;

public class GameController implements KeyListener {
    private ControllableGameModel gameModel;
    private ControllableEnemy enemy;
    private GameView gameView;
    private Timer timer;
    private boolean playerUp, playerDown, playerLeft, playerRight;
    private GameState currentGameState;
    private GameState previousGameState;

    private int tickCounter = 1;
    private static final int timeDelay = 10;
    private static final int UPDATE_ENEMY_TICK = 30;
    private static final int ADD_SCORE_TICK = 100;
    private static final int GAPPLE_COUNTDOWN_TICK = 100;
    private static final int ADD_PROJECTILE_TICK = 400;
    private static final int READY_TO_SHOOT_TICK = ADD_PROJECTILE_TICK - 2 * UPDATE_ENEMY_TICK;
    private static final int ADD_GAPPLE_TICK = 5000;
    private static final double RESET_GAME_TICK = 750;
    private static final int TICK_DIVISOR = 3;

    public GameController(ControllableGameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.enemy = gameModel.getControllableEnemy();
        this.gameView = gameView;
        this.timer = new Timer(timeDelay, e -> clockTick());
        gameView.setFocusable(true);
        gameView.addKeyListener(this);

        // Hentet fra https://stackoverflow.com/questions/2303305/window-resize-event
        gameView.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                gameView.updateWindowRatio();
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        GameState gameState = gameModel.getCurrentState();
        if (gameState == GameState.HOME) {
            if (keyCode == KeyEvent.VK_S) {
                gameModel.startNewGame();
                timer.start();
                resetTickCounter();
            } else if (keyCode == KeyEvent.VK_C) {
                gameModel.setGameState(GameState.CONTROLS);
            }
        } else if (gameState == GameState.CONTROLS) {
            gameModel.setGameState(GameState.HOME);
        }
        else if (gameState == GameState.ACTIVE_ANGRY || gameState == GameState.ACTIVE_HAPPY) {
            if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                playerUp = true;
            } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                playerDown = true;
            } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                playerLeft = true;
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                playerRight = true;
            } else if (keyCode == KeyEvent.VK_P) {
                previousGameState = gameState;
                gameModel.setGameState(GameState.PAUSED);
                timer.stop();
            }
        } else if (gameModel.getCurrentState() == GameState.PAUSED) {
            if (previousGameState == GameState.ACTIVE_ANGRY) {
                gameModel.setGameState(GameState.ACTIVE_ANGRY);
            } else {
                gameModel.setGameState(GameState.ACTIVE_HAPPY);
            }
            timer.start();
        } else if (gameModel.getCurrentState() == GameState.GAME_OVER) {
            if (keyCode == KeyEvent.VK_H) {
                gameModel.setGameState(GameState.HOME);
            } else if (keyCode == KeyEvent.VK_R) {
                gameModel.startNewGame();
                timer.restart();
                resetTickCounter();
            }
        }
        gameView.repaint();
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
        currentGameState = gameModel.getCurrentState();
        if (currentGameState == GameState.ACTIVE_ANGRY || currentGameState == GameState.ACTIVE_HAPPY) {
            if (previousGameState != null && currentGameState != previousGameState) {
                resetTickCounter();
            }
            previousGameState = currentGameState;

            if (tickCounter % UPDATE_ENEMY_TICK == 0) {
                enemy.updateState();
            } if (tickCounter % ADD_SCORE_TICK == 0) {
                gameModel.addTimeScore();
            }

            if (currentGameState == GameState.ACTIVE_ANGRY) {
                if (tickCounter % GAPPLE_COUNTDOWN_TICK == 0) {
                    gameModel.updateGappleCountdown();
                }
                if (tickCounter % ADD_PROJECTILE_TICK == READY_TO_SHOOT_TICK) {
                    enemy.updateShootingStatus();
                } 
                if (tickCounter % ADD_PROJECTILE_TICK == 0) {
                    gameModel.addProjectile();
                }
                if (tickCounter % ADD_GAPPLE_TICK == 0) {
                    gameModel.addGoldenApple();
                }

            } else if (currentGameState == GameState.ACTIVE_HAPPY) {
                    if (tickCounter % (ADD_PROJECTILE_TICK / TICK_DIVISOR) == READY_TO_SHOOT_TICK / TICK_DIVISOR) {
                        enemy.updateShootingStatus();
                    } 
                    if (tickCounter % (ADD_PROJECTILE_TICK / TICK_DIVISOR) == 0) {
                        gameModel.addProjectile();
                    }
                    if (tickCounter % RESET_GAME_TICK == 0) {
                        gameModel.setGameState(GameState.ACTIVE_ANGRY);
                        gameModel.resetGapple();
                        enemy.switchMood();
                    }
                }
            gameView.repaint();
            handlePlayerMovement();
            gameModel.clockTick();
            tickCounter++;
        }
    }

    private void handlePlayerMovement() {
        if ((playerUp && playerDown) || (playerLeft && playerRight)) {
            return;
        }
        if (playerUp) {
            gameModel.movePlayer(0, -1);
        }
        if (playerDown) {
            gameModel.movePlayer(0, 1);
        }
        if (playerLeft) {
            gameModel.movePlayer(-1, 0);
        }
        if (playerRight) {
            gameModel.movePlayer(1, 0);
        }
    }

    private void resetTickCounter() {
        tickCounter = 1;
    }
}
