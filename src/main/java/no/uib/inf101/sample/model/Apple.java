package no.uib.inf101.sample.model;

import java.awt.image.BufferedImage;

import no.uib.inf101.sample.view.Inf101Graphics;

public class Apple extends Entity {
    private BufferedImage image;

    private Apple(double randX, double randY) {
        this.x = randX;
        this.y = randY;
        this.image = Inf101Graphics.loadImageFromResources("apple.png");
    }

    static Apple createNewApple() {
        double randX = Math.random();
        double randY = Math.random();
        return new Apple(randX, randY);
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
