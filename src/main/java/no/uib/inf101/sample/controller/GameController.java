package no.uib.inf101.sample.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.model.EnemyModel;
import no.uib.inf101.sample.view.GameView;

public class GameController implements KeyListener, Runnable {
    private ControllablePlayerModel playerModel;
    private EnemyModel turretModel;
    private GameView gameView;
    private Thread gameLoop;

    private boolean movedUp, movedDown, movedLeft, movedRight;
    
    public GameController(ControllablePlayerModel playerModel, EnemyModel turretModel, GameView gameView) {
        this.playerModel = playerModel;
        this.turretModel = turretModel;
        this.gameView = gameView;
        this.gameLoop = new Thread(this);
        gameView.setFocusable(true);
        gameView.addKeyListener(this);
        gameLoop.start();
    
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

    @Override
    public void run() {
        double drawInterval = 10;
        GameState gameState = playerModel.getCurrentState();
        int counter = 0;

       while (gameState == GameState.ACTIVE) {
            gameView.repaint();
            handleKeyInputs();
            if (counter % 15 == 0) {
                turretModel.getNextImage();
                counter = 0;
            }

            try {
                Thread.sleep((long) drawInterval);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
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
