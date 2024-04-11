package no.uib.inf101.sample.model;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public class Enemy {
    private static final double MARGIN_X = 0.06;
    private static final double MARGIN_Y = 0.12;
    private double enemyX, enemyY;
    private BufferedImage enemyImage;
    private Rectangle2D restrictedArea;

    private BufferedImage firstCPU = Inf101Graphics.loadImageFromResources("/cpu_enemy1.png");
    private BufferedImage secondCPU = Inf101Graphics.loadImageFromResources("/cpu_enemy2.png");

    public Enemy() {
        this.enemyX = 0.50;
        this.enemyY = 0.50;
        this.enemyImage = firstCPU;
        this.restrictedArea = new Rectangle2D.Double(
            enemyX - MARGIN_X, enemyY - MARGIN_Y, 
            MARGIN_X * 2, MARGIN_Y * 2
        );
    }

    public double getX() {
        return enemyX;
    }

    public double getY() {
        return enemyY;
    }

    public BufferedImage getImage() {
        return this.enemyImage;
    }

    public void getNextImage(){
        if (enemyImage == firstCPU) {
            enemyImage = secondCPU;
        } else {
            enemyImage = firstCPU;
        }
    }

    public Rectangle2D getRestricedArea() {
        return this.restrictedArea;
    }
}
