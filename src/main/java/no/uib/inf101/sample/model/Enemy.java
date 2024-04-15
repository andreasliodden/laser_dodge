package no.uib.inf101.sample.model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Enemy {
    private static final double MARGIN_X = 0.05;
    private static final double MARGIN_Y = 0.1;
    
    private final double enemyX, enemyY;
    private final Rectangle2D restrictedArea;
    private EnemyState enemyState;
    private boolean readyToShoot;

    Enemy() {
        this.enemyX = 0.50;
        this.enemyY = 0.50;
        this.enemyState = EnemyState.ANGRY_ONE;
        this.restrictedArea = new Rectangle2D.Double(
            enemyX - MARGIN_X, enemyY - MARGIN_Y, 
            MARGIN_X * 2, MARGIN_Y * 2
        );
        this.readyToShoot = false;
    }

    double getX() {
        return this.enemyX;
    }

    double getY() {
        return this.enemyY;
    }

    BufferedImage getImage() {
        return enemyState.getImage();
    }

    void updateImage(){
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
            updateImage();
        }
    }

    void updateState() {
        if (enemyState == EnemyState.ANGRY_ONE || enemyState == EnemyState.ANGRY_TWO || enemyState == EnemyState.ANGRY_READY) {
            enemyState = EnemyState.HAPPY_ONE;
        } else {
            enemyState = EnemyState.ANGRY_ONE;
        }
    }
}
