package no.uib.inf101.sample.model.enemy;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import no.uib.inf101.sample.controller.ControllableEnemy;
import no.uib.inf101.sample.model.Entity;
import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.view.interfaces.ViewableEnemy;

public class Enemy extends Entity implements ViewableEnemy, ControllableEnemy {
    private static final double MARGIN_X = 0.05;
    private static final double MARGIN_Y = 0.1;
    private static final double X_POS = 0.5;
    private static final double Y_POS = 0.5;
    private final Rectangle2D restrictedArea;
    private EnemyState currentState, previousState;
    private boolean readyToShoot;
    private ArrayList<EnemyState> listOfAngryStates = new ArrayList<>();
    

    public Enemy() {
        this.x = X_POS;
        this.y = Y_POS;
        this.currentState = EnemyState.ANGRY_ONE;
        this.restrictedArea = new Rectangle2D.Double(
            x - MARGIN_X, y - MARGIN_Y, 
            MARGIN_X * 2, MARGIN_Y * 2
        );
        this.readyToShoot = false;
        initAngryStates();
    }

    @Override
    public BufferedImage getImage() {
        return currentState.getImage();
    }

    private void initAngryStates() {
        Collections.addAll(listOfAngryStates, EnemyState.ANGRY_ONE, EnemyState.ANGRY_TWO, EnemyState.ANGRY_READY);
    }

    @Override
    public void updateState(){
        if (readyToShoot) {
            if (listOfAngryStates.contains(currentState)) {
                currentState = EnemyState.ANGRY_READY;
            } else {
                currentState = EnemyState.HAPPY_READY;
            }
        } else {
            switch(currentState) {
                case ANGRY_ONE:
                    currentState = EnemyState.ANGRY_TWO;
                    break;
                case ANGRY_TWO:
                case ANGRY_READY:
                    currentState = EnemyState.ANGRY_ONE;
                    break;
                case HAPPY_ONE:
                    currentState = EnemyState.HAPPY_TWO;
                    break;
                case HAPPY_TWO:
                case HAPPY_READY:
                    currentState = EnemyState.HAPPY_ONE;
                    break;
                default: 
                    break;
            }
        }
    }

    public Rectangle2D getRestricedArea() {
        return this.restrictedArea;
    }

    @Override
    public void updateShootingStatus() {
        if (!readyToShoot) {
            readyToShoot = true;
        } else {
            readyToShoot = false;
        }
        updateState();
    }

    @Override
    public void switchMood() {
        if (listOfAngryStates.contains(currentState)) {
            currentState = EnemyState.HAPPY_ONE;
        } else {
            currentState = EnemyState.ANGRY_ONE;
        }
        readyToShoot = false;
    }

    public void reset() {
        this.currentState = EnemyState.ANGRY_ONE;
        this.readyToShoot = false;
    }

    @Override
    public void setToPaused(GameState gameState) {
        this.previousState = currentState;
        if (gameState == GameState.ACTIVE_ANGRY) {
            currentState = EnemyState.ANGRY_PAUSED;
        } else {
            currentState = EnemyState.HAPPY_PAUSED;
        }
    }

    @Override
    public void resume() {
        this.currentState = previousState;
    }
}
