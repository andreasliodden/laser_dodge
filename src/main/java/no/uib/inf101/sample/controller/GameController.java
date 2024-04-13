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
    private Timer gameLoop;

    private static final int timeDelay = 5;
    private int tickCounter = 1;

    private boolean playerUp, playerDown, playerLeft, playerRight;
    
    public GameController(ControllableGameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.gameLoop = new Timer(timeDelay, e -> clockTick());
        gameView.setFocusable(true);
        gameView.addKeyListener(this);
        gameLoop.start();
        
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
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            playerUp = true;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            playerDown = true;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            playerLeft = true;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            playerRight = true;
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            playerUp = false;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            playerDown = false;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            playerLeft = false;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            playerRight = false;
        }
    }

    private void clockTick() {
       GameState gameState = gameModel.getCurrentState();
       if (gameState == GameState.ACTIVE) {
            gameView.repaint();
            handleKeyInputs();
            gameModel.clockTick();
            if (tickCounter % 400 == 0) {
                gameModel.setEnemyStatus();
            }
            if (tickCounter % 20 == 0) {
                gameModel.getNextEnemyImage();
            } if (tickCounter % 500 == 0) {
                gameModel.addProjectile();
            } 
            tickCounter++;

            if (tickCounter == 500) {
                tickCounter = 0;
            }
       }
    }

    private void handleKeyInputs() {
        if ((playerUp && playerDown) || (playerLeft && playerRight)) {
            return;
        } 
        
        if (playerUp) {
            gameModel.movePlayer(0, -1);
        } if (playerDown) {
            gameModel.movePlayer(0,1);
        } if (playerLeft) {
            gameModel.movePlayer(-1, 0);
        } if (playerRight) {
            gameModel.movePlayer(1, 0);
        }
    }
} 
