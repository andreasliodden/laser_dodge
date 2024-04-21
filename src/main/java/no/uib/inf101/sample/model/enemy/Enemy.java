package no.uib.inf101.sample.model.enemy;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import no.uib.inf101.sample.controller.ControllableEnemy;
import no.uib.inf101.sample.model.Entity;
import no.uib.inf101.sample.view.interfaces.ViewableEnemy;

public class Enemy extends Entity implements ViewableEnemy, ControllableEnemy {
    private static final double MARGIN_X = 0.05;
    private static final double MARGIN_Y = 0.1;
    private final Rectangle2D restrictedArea;
    private EnemyState enemyState;
    private boolean readyToShoot;
    private ArrayList<EnemyState> angryStates = new ArrayList<>();
    

    public Enemy() {
        this.x = 0.50;
        this.y = 0.50;
        this.enemyState = EnemyState.ANGRY_ONE;
        this.restrictedArea = new Rectangle2D.Double(
            x - MARGIN_X, y - MARGIN_Y, 
            MARGIN_X * 2, MARGIN_Y * 2
        );
        this.readyToShoot = false;
        initAngryStates();
    }

    @Override
    public BufferedImage getImage() {
        return enemyState.getImage();
    }

    private void initAngryStates() {
        Collections.addAll(angryStates, EnemyState.ANGRY_ONE, EnemyState.ANGRY_TWO, EnemyState.ANGRY_READY);
    }

    @Override
    public void updateState(){
        if (readyToShoot) {
            if (angryStates.contains(enemyState)) {
                enemyState = EnemyState.ANGRY_READY;
            } else {
                enemyState = EnemyState.HAPPY_READY;
            }
        } else {
            switch(enemyState) {
                case ANGRY_ONE:
                    enemyState = EnemyState.ANGRY_TWO;
                    break;
                case ANGRY_TWO:
                case ANGRY_READY:
                    enemyState = EnemyState.ANGRY_ONE;
                    break;
                case HAPPY_ONE:
                    enemyState = EnemyState.HAPPY_TWO;
                    break;
                case HAPPY_TWO:
                case HAPPY_READY:
                    enemyState = EnemyState.HAPPY_ONE;
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
        if (readyToShoot == false) {
            readyToShoot = true;
        } else {
            readyToShoot = false;
        }
        updateState();
    }

    @Override
    public void switchMood() {
        if (angryStates.contains(enemyState)) {
            enemyState = EnemyState.HAPPY_ONE;
        } else {
            enemyState = EnemyState.ANGRY_ONE;
        }
        readyToShoot = false;
    }

    public void reset() {
        this.enemyState = EnemyState.ANGRY_ONE;
        this.readyToShoot = false;
    }
}
