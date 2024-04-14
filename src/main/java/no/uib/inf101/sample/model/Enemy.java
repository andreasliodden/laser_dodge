package no.uib.inf101.sample.model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public class Enemy {
    private static final double MARGIN_X = 0.05;
    private static final double MARGIN_Y = 0.1;
    
    private final double enemyX, enemyY;
    private final Rectangle2D restrictedArea;
    private BufferedImage enemyImage;
    private boolean readyToShoot;

    private final static BufferedImage firstCPU = Inf101Graphics.loadImageFromResources("/cpu_enemy1.png");
    private final static BufferedImage secondCPU = Inf101Graphics.loadImageFromResources("/cpu_enemy2.png");
    private final static BufferedImage readyCPU = Inf101Graphics.loadImageFromResources("/cpu_ready.png"); 

    Enemy() {
        this.enemyX = 0.50;
        this.enemyY = 0.50;
        this.enemyImage = firstCPU;
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
        return this.enemyImage;
    }

    void updateImage(){
        if (readyToShoot) {
            enemyImage = readyCPU;
        } else if (enemyImage == firstCPU) {
            enemyImage = secondCPU;
        } else {
            enemyImage = firstCPU;
        }
    }

    Rectangle2D getRestricedArea() {
        return this.restrictedArea;
    }

    void hasShot() {
        readyToShoot = false;
        updateImage();
    }

    void setReadyToShoot() {
        readyToShoot = true;
    }
}
