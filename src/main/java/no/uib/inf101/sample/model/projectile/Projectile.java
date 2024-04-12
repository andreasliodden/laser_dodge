package no.uib.inf101.sample.model.projectile;

public class Projectile {
    private double y;
    private double x;
    private double velocityX;
    private double velocityY;
    
    private Projectile(double velocityX, double velocityY) {
        this.x = 0.5;
        this.y = 0.5;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    static Projectile createNewProjectile(double velocityX, double velocityY) {
        return new Projectile(velocityX, velocityY);
    }

    public void move() {
        double nextX = x + velocityX;
        double nextY = y + velocityY;

        if(isLegalPosition(nextX, nextY)) {
            x = nextX;
            y = nextY;
        } else {
            if (nextX < 0 || nextX > 1) {
                velocityX = -velocityX;
            } else {
                velocityY = -velocityY;
            }
        }
    }

    private boolean isLegalPosition(double x, double y) {
        return (x >= 0 && x <= 1 && y >= 0 && y <= 1);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
