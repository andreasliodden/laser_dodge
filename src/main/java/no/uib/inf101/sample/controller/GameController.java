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
            } else if (keyCode == KeyEvent.VK_C) {
                gameModel.setGameState(GameState.CONTROLS);
            }
        } else if (gameState == GameState.CONTROLS) {
            gameModel.setGameState(GameState.HOME);
        }
        else if (gameState == GameState.ACTIVE_ENEMY || gameState == GameState.ACTIVE_FRIENDLY) {
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
            if (previousGameState == GameState.ACTIVE_ENEMY) {
                gameModel.setGameState(GameState.ACTIVE_ENEMY);
            } else {
                gameModel.setGameState(GameState.ACTIVE_FRIENDLY);
            }
            timer.start();
        } else if (gameModel.getCurrentState() == GameState.GAME_OVER) {
            if (keyCode == KeyEvent.VK_H) {
                gameModel.setGameState(GameState.HOME);
            } else if (keyCode == KeyEvent.VK_R) {
                gameModel.startNewGame();
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
        if (currentGameState == GameState.ACTIVE_ENEMY || currentGameState == GameState.ACTIVE_FRIENDLY) {
            if (previousGameState != null && currentGameState != previousGameState) {
                tickCounter = 1;
            }
            previousGameState = currentGameState;

            if (tickCounter % 30 == 0) {
                enemy.updateState();
            } if (tickCounter % 100 == 0) {
                gameModel.addTimeScore();
            }

            if (currentGameState == GameState.ACTIVE_ENEMY) {
                if (tickCounter % 100 == 0) {
                    gameModel.updateGappleCountdown();
                }
                if (tickCounter % 400 == 300) {
                    enemy.updateShootingStatus();
                } 
                if (tickCounter % 400 == 0) {
                    gameModel.addProjectile();
                }
                if (tickCounter % 5000 == 0) {
                    gameModel.addGoldenApple();
                }
            } else if (currentGameState == GameState.ACTIVE_FRIENDLY) {
                    if (tickCounter % 150 == 50) {
                        enemy.updateShootingStatus();
                    } 
                    if (tickCounter % 150 == 0) {
                        gameModel.addProjectile();
                    }
                    if (tickCounter % 750 == 0) {
                        gameModel.setGameState(GameState.ACTIVE_ENEMY);
                        gameModel.resetGappleCountdown();
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
}
