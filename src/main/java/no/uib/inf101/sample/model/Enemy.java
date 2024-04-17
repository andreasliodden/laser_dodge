package no.uib.inf101.sample.model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Enemy extends Entity {
    private static final double MARGIN_X = 0.05;
    private static final double MARGIN_Y = 0.1;
    private final Rectangle2D restrictedArea;
    private EnemyState enemyState;
    private boolean readyToShoot;
    private ArrayList<EnemyState> angryStates = new ArrayList<>();
    

    Enemy() {
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

    private void initAngryStates() {
        Collections.addAll(angryStates, EnemyState.ANGRY_ONE, EnemyState.ANGRY_TWO, EnemyState.ANGRY_READY);
    }

    BufferedImage getImage() {
        return enemyState.getImage();
    }

    void updateState(){
        if (readyToShoot) {
            if (angryStates.contains(enemyState)) {
                enemyState = EnemyState.ANGRY_READY;
            } else {
                enemyState = EnemyState.FRIENDLY_READY;
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
                case FRIENDLY_ONE:
                    enemyState = EnemyState.FRIENDLY_TWO;
                    break;
                case FRIENDLY_TWO:
                case FRIENDLY_READY:
                    enemyState = EnemyState.FRIENDLY_ONE;
                    break;
                default: 
                    break;
            }
        }
    }

    Rectangle2D getRestricedArea() {
        return this.restrictedArea;
    }

    void updateShootingStatus() {
        if (readyToShoot == false) {
            readyToShoot = true;
        } else {
            readyToShoot = false;
        }
        updateState();
    }

    void switchGameState() {
        if (angryStates.contains(enemyState)) {
            enemyState = EnemyState.FRIENDLY_ONE;
        } else {
            enemyState = EnemyState.ANGRY_ONE;
        }
        readyToShoot = false;
    }
}
