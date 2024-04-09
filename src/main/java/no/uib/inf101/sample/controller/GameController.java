package no.uib.inf101.sample.controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.model.EnemyModel;
import no.uib.inf101.sample.model.GameModel;
import no.uib.inf101.sample.view.GameView;

public class GameController implements KeyListener {
    private ControllablePlayerModel playerModel;
    private EnemyModel enemyModel;
    private GameView gameView;
    private Timer gameLoop;

    private static final int timeDelay = 5;
    private int counter = 0;

    private boolean movedUp, movedDown, movedLeft, movedRight;
    
    public GameController(ControllablePlayerModel playerModel, GameModel gameModel, GameView gameView) {
        this.playerModel = playerModel;
        this.enemyModel = gameModel.getEnemyModel();
        this.gameView = gameView;
        this.gameLoop = new Timer(timeDelay, e -> clockTick());
        gameView.setFocusable(true);
        gameView.addKeyListener(this);
        gameLoop.start();

        // Basert på https://stackoverflow.com/questions/2303305/window-resize-event
        // og lagt til egne, relevante metoder.
        this.gameView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                playerModel.updateWindowSize(gameView.getWidth(), gameView.getHeight());
                playerModel.checkValidPosition();
            }
        });
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            movedUp = true;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            movedDown = true;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            movedLeft = true;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            movedRight = true;
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            movedUp = false;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            movedDown = false;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            movedLeft = false;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            movedRight = false;
        }
    }

    private void clockTick() {
       GameState gameState = playerModel.getCurrentState();
       if (gameState == GameState.ACTIVE) {
            gameView.repaint();
            handleKeyInputs();
            if (counter % 20 == 0) {
                enemyModel.getNextImage();
            }
            counter++;
       }
    }

    private void handleKeyInputs() {
        if ((movedUp && movedDown) || (movedLeft && movedRight)) {
            return;
        } 
        
        if (movedUp) {
            playerModel.movePlayerY(-1);
        } if (movedDown) {
            playerModel.movePlayerY(1);
        } if (movedLeft) {
            playerModel.movePlayerX(-1);
        } if (movedRight) {
            playerModel.movePlayerX(1);
        }
    }
} 
