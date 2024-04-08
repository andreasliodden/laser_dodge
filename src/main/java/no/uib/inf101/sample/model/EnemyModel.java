package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.GameView;
import no.uib.inf101.sample.view.Inf101Graphics;

public class EnemyModel {
    private double enemyX;
    private double enemyY;
    private BufferedImage enemyImage;

    private BufferedImage firstCPU = Inf101Graphics.loadImageFromResources("/cpu_enemy1.png");
    private BufferedImage secondCPU = Inf101Graphics.loadImageFromResources("/cpu_enemy2.png");

    public EnemyModel() {
        this.enemyImage = firstCPU;
    }

    public void setPosition(GameView gameView) {
        this.enemyX = (gameView.getWidth() - enemyImage.getWidth()) / 2;
        this.enemyY = (gameView.getHeight() - enemyImage.getHeight()) / 2;
    }

    public double getX() {
        return this.enemyX;
    }

    public double getY() {
        return this.enemyY;
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
}
