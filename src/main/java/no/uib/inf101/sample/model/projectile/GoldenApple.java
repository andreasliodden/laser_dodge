package no.uib.inf101.sample.model.projectile;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public class GoldenApple extends RandomMovingEntity {
    private BufferedImage image;

    private GoldenApple(double x, double y, double velocityX, double velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.image = Inf101Graphics.loadImageFromResources("golden_apple.png");
    }

    static GoldenApple createGoldenApple(double x, double y, double velocityX, double velocityY) {
        return new GoldenApple(x, y, velocityX, velocityY);
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
 