package no.uib.inf101.sample.model.enemy;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import no.uib.inf101.sample.controller.ControllableEnemy;
import no.uib.inf101.sample.model.entity.Entity;
import no.uib.inf101.sample.view.viewable.ViewableEnemy;


/**
 * Represents the enemy in the game. 
 * It extends the Entity class and implements the ViewableEnemy and ControllableEnemy interfaces.
 */

public class Enemy extends Entity implements ViewableEnemy, ControllableEnemy {
    private static final double MARGIN_X = 0.05;
    private static final double MARGIN_Y = 0.1;
    private static final double X_POS = 0.5;
    private static final double Y_POS = 0.5;

    private final Rectangle2D restrictedArea;
    private EnemyState currentState, previousState;
    private boolean readyToShoot, isPaused;
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
        this.isPaused = false;
        initAngryStates();
    }

    @Override
    public EnemyState getCurrentState() {
        return this.currentState;
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

    /**
     * Gets the restricted area surrounding the enemy.
     * The player has no access to this area. 
     * 
     * @return a Rectangle2D-object representing the restricted area
     */
    public Rectangle2D getRestricedArea() {
        return this.restrictedArea;
    }

    @Override
    public void updateShootingStatus() {
        readyToShoot = !readyToShoot;
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

    @Override
    public void updatePause() {
        if (isPaused) {
            this.currentState = previousState;
            isPaused = false;
        } else {
            this.previousState = currentState;
            if (listOfAngryStates.contains(currentState)) {
                currentState = EnemyState.ANGRY_PAUSED;
            } else {
                currentState = EnemyState.HAPPY_PAUSED;
            }
            isPaused = true;
        }
    }

    public void reset() {
        this.currentState = EnemyState.ANGRY_ONE;
        this.readyToShoot = false;
    }
}
