package no.uib.inf101.sample.model.projectile;

public class GoldenApple extends RandomMovingEntity {

    private GoldenApple(double x, double y, double velocityX, double velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    static GoldenApple createGoldenApple(double x, double y, double velocityX, double velocityY) {
        return new GoldenApple(x, y, velocityX, velocityY);
    }
}
 