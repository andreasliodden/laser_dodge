package no.uib.inf101.sample.model;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import no.uib.inf101.sample.view.Inf101Graphics;

public class GoldenApple extends Entity{
    private BufferedImage image;
    private double velocityX, velocityY;
    private ArrayList<Point2D> trail = new ArrayList<>();

    private static final double SPEED = 0.003;
    private static final double MIN_POSITION = 0;
    private static final double MAX_POSITION = 1;

    private GoldenApple(double x, double y, double velocityX, double velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.image = Inf101Graphics.loadImageFromResources("golden_apple.png");
    }

    static GoldenApple createNewApple() {
        Random rand = new Random();
        double x = Math.random();
        double y = Math.random();
        double angle = rand.nextDouble() * 2 * Math.PI;
        double velocityX = SPEED * Math.cos(angle);
        double velocityY = SPEED * Math.sin(angle);

        return new GoldenApple(x, y, velocityX, velocityY);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void move() {
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        updatePosition(nextX, nextY);
        addPositionToTrail();
    }

    @Override
    protected boolean updatePosition(double nextX, double nextY) {
        if(super.updatePosition(nextX, nextY)) {
            return true;
        }
        else {
            if (nextX < MIN_POSITION) {
                velocityX = -velocityX;
            } else if (nextX > MAX_POSITION) {
                velocityX = -velocityX;
            } else if (nextY < MIN_POSITION) {
                velocityY = -velocityY;
            } else if (nextY > MAX_POSITION) {
                velocityY = -velocityY;
            }
        return false;
        }
    }

    private void addPositionToTrail() {
        trail.add(new Point2D.Double(x, y));

        if (trail.size() > 20) {
            trail.remove(0);
        }
    }

    ArrayList<Point2D> getTrail() {
        return this.trail;
    }
}
 