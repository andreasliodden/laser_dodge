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
    private GameView gameView;
    private Timer timer;
    private boolean playerUp, playerDown, playerLeft, playerRight;
    private GameState currentGameState;
    private GameState previousGameState;

    private int tickCounter = 1;
    private static final int timeDelay = 10;

    public GameController(ControllableGameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.timer = new Timer(timeDelay, e -> clockTick());
        gameView.setFocusable(true);
        gameView.addKeyListener(this);
        timer.start();

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
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            playerUp = true;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            playerDown = true;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            playerLeft = true;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            playerRight = true;
        }
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
        if (previousGameState != null && currentGameState != previousGameState) {
            tickCounter = 1;
        }
        previousGameState = currentGameState;

        if (tickCounter % 30 == 0) {
            gameModel.updateEnemyImage();
        } 

        if (currentGameState == GameState.ACTIVE_ENEMY) {
            if (tickCounter % 500 == 400) {
                gameModel.readyToShoot();
            } 
            if (tickCounter % 500 == 0) {
                gameModel.addProjectile();
            }
            if (tickCounter % 5000 == 0) {
                gameModel.addGoldenApple();
            }
        } else if (currentGameState == GameState.ACTIVE_FRIENDLY) {
                if (tickCounter % 150 == 50) {
                    gameModel.readyToShoot();
                } 
                if (tickCounter % 150 == 0) {
                    gameModel.addProjectile();
                }
                if (tickCounter % 900 == 0) {
                    gameModel.updateGameState();
                }
            }
        gameView.repaint();
        handleKeyInput();
        gameModel.clockTick();
        tickCounter++;
    }

    private void handleKeyInput() {
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
