package no.uib.inf101.sample.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf101.sample.view.GameView;

public class GameController implements KeyListener {
    private ControllablePlayerModel playerModel;
    private GameView gameView;
    
    public GameController(ControllablePlayerModel playerModel, GameView gameView) {
        this.playerModel = playerModel;
        this.gameView = gameView;
        gameView.setFocusable(true);
        gameView.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP){

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN){

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT){

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){

        }
        gameView.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
