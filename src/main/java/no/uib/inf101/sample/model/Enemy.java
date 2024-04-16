package no.uib.inf101.sample.model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private static final double MARGIN_X = 0.05;
    private static final double MARGIN_Y = 0.1;
    private final Rectangle2D restrictedArea;
    private EnemyState enemyState;
    private boolean readyToShoot;

    Enemy() {
        this.x = 0.50;
        this.y = 0.50;
        this.enemyState = EnemyState.ANGRY_ONE;
        this.restrictedArea = new Rectangle2D.Double(
            x - MARGIN_X, y - MARGIN_Y, 
            MARGIN_X * 2, MARGIN_Y * 2
        );
        this.readyToShoot = false;
    }

    BufferedImage getImage() {
        return enemyState.getImage();
    }

    void updateState(){
        if (readyToShoot) {
            if (enemyState == EnemyState.ANGRY_ONE || enemyState == EnemyState.ANGRY_TWO) {
                enemyState = EnemyState.ANGRY_READY;
            } else if (enemyState != EnemyState.ANGRY_READY) {
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

    Rectangle2D getRestricedArea() {
        return this.restrictedArea;
    }

    void updateShootingStatus() {
        if (readyToShoot == false) {
            readyToShoot = true;
        } else {
            readyToShoot = false;
            updateState();
        }
    }

    void switchGameState() {
        if (enemyState == EnemyState.ANGRY_ONE || enemyState == EnemyState.ANGRY_TWO || enemyState == EnemyState.ANGRY_READY) {
            enemyState = EnemyState.HAPPY_ONE;
        } else {
            enemyState = EnemyState.ANGRY_ONE;
        }
    }
}
