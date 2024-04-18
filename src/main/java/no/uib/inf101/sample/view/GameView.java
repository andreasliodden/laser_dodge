package no.uib.inf101.sample.view;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import no.uib.inf101.sample.model.GameState;
import no.uib.inf101.sample.view.interfaces.ViewableEnemy;
import no.uib.inf101.sample.view.interfaces.ViewableGameModel;
import no.uib.inf101.sample.view.interfaces.ViewablePlayer;
import no.uib.inf101.sample.view.interfaces.ViewableProjectile;

public class GameView extends JPanel {
    private static final int START_WIDTH = 1700;
    private static final int START_HEIGHT = 1000;
    private static final BufferedImage APPLE = Inf101Graphics.loadImageFromResources("apple.png");
    private static final BufferedImage GOLDEN_APPLE = Inf101Graphics.loadImageFromResources("golden_apple.png");

    private ColorTheme colorTheme;
    private ViewableGameModel gameModel;
    private ViewableEnemy enemy;
    private ViewablePlayer player;
    private double windowRatio;

    public GameView(ViewableGameModel gameModel){
        this.gameModel = gameModel;
        this.player = gameModel.getViewablePlayer();
        this.enemy = gameModel.getViewableEnemy();
        this.colorTheme = new DefaultColorTheme();
        this.setPreferredSize(new Dimension(
                START_WIDTH, START_HEIGHT));
        this.setBackground(colorTheme.getAngryBackground());
        this.isDoubleBuffered();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameModel.getCurrentState() == GameState.ACTIVE_FRIENDLY) {
            if (this.getBackground() == colorTheme.getAngryBackground()) {
                this.setBackground(colorTheme.getFriendlyBackground());
            }
            drawGameInfo(g2, Color.BLACK);
            drawApples(g2);
        } else {
            if (this.getBackground() == colorTheme.getFriendlyBackground()) {
                this.setBackground(colorTheme.getAngryBackground());
            } else if (gameModel.goldenAppleExists()) {
                drawGoldenApple(g2);
            }
            drawGameInfo(g2, Color.WHITE);
            drawProjectile(g2);
        }
        drawPlayer(g2);
        drawEnemy(g2);
    }

    private void drawGoldenApple(Graphics2D g2) {
        drawImage(g2, GOLDEN_APPLE, gameModel.getGappleX(), gameModel.getGappleY(), 3);
    }

    private void drawApples(Graphics2D g2) {
        for (int i = 0; i < gameModel.getNumberOfProjectiles(); i++) {
            ViewableProjectile projectile = gameModel.getProjectile(i);
            drawImage(g2, APPLE, projectile.getX(), projectile.getY(), 3);
        }
    }

    private void drawGameInfo(Graphics2D g2, Color textColor) {
        g2.setColor(textColor);
        g2.setFont(getFont(30));
        Inf101Graphics.drawCenteredString(g2, "HEALTH:", 0, 0, this.getWidth(), this.getHeight() * 0.075);
        Inf101Graphics.drawCenteredString(g2, "SCORE: " + gameModel.getScore(), 0, 0, this.getWidth() * 0.35, this.getHeight() * 0.15);

        String message;
        if (gameModel.getCurrentState() == GameState.ACTIVE_ENEMY) {
            if (gameModel.getGappleCountdown() > 0) {
                message = "GAPPLE SPAWNS IN: " + gameModel.getGappleCountdown();
            } else {
                message = "GAPPLE IS SPAWNED";
            }
        } else {
            message = "HEALING FRENZY";
        }
        Inf101Graphics.drawCenteredString(g2, message, this.getWidth() * 0.65, 0, this.getWidth() * 0.35 , this.getHeight() * 0.15);
        Rectangle2D healthBar = new Rectangle2D.Double(
                    this.getWidth() * 0.35, this.getHeight() * 0.075,
                    this.getWidth() * 0.3, this.getHeight() * 0.05
                );
        
        Rectangle2D health = new Rectangle2D.Double(
                    this.getWidth() * 0.35, this.getHeight() * 0.075,
                    (this.getWidth() * 0.3 * player.getHealth()) / player.getMaxHealth(),
                    this.getHeight() * 0.05
                );
                
        g2.setColor(getHealthColor());
        g2.fill(health);
        g2.setColor(textColor);
        g2.setStroke(new BasicStroke(2));
        g2.draw(healthBar);
        g2.setColor(Color.WHITE);
        Inf101Graphics.drawCenteredString(g2, "" + player.getHealth(), healthBar);

    }

    private Color getHealthColor() {
        int maxGreenValue = 255;
        double multiplier = maxGreenValue / player.getMaxHealth();
        int greenValue = player.getHealth() * (int) multiplier;
        int redValue = maxGreenValue - greenValue;
        return new Color(redValue, Math.min(200, greenValue), 0);
    }

    public void updateWindowRatio() {
        windowRatio = this.getWidth() / this.getHeight();
    }

    private void drawPlayer(Graphics2D g2) {
        drawImage(g2, player.getImage(), player.getX(), player.getY(), 5);
    }

    private void drawEnemy(Graphics2D g2) {
        drawImage(g2, enemy.getImage(), enemy.getX(), enemy.getY(), 6);
    }

    private void drawProjectile(Graphics2D g2) {
        double width = 0.02 * this.getWidth();
        double height = width * windowRatio;
        Rectangle2D projectileBox, trailBox;
        double resizingFactor;
        int colorFactor;
        
        for (int i = 0; i < gameModel.getNumberOfProjectiles(); i++) {
            ViewableProjectile projectile = gameModel.getProjectile(i);
            colorFactor = 0;
            ArrayList<Point2D> trail = projectile.getTrail();
            projectileBox = new Rectangle2D.Double(
                    projectile.getX() * (this.getWidth() - width), 
                    projectile.getY() * (this.getHeight() - height),
                    width, height
                );
            g2.setColor(Color.RED);
            g2.fill(projectileBox);

            for (int j = trail.size() - 1; j >= 0; j--) {
                resizingFactor = 1 - ((trail.size() - 1) - j) * 0.04;
                Point2D point = trail.get(j);
                trailBox = new Rectangle2D.Double(
                    point.getX() * (this.getWidth() - width * resizingFactor),
                    point.getY() * (this.getHeight() - height * resizingFactor),
                    width * resizingFactor, height * resizingFactor
                );
                g2.setColor(new Color(Math.max(0, 255 - colorFactor), 0, 0));
                g2.fill(trailBox);
                colorFactor += 20;
            }
            
        }
    }

    private Font getFont(int fontSize) {
        fontSize *= Math.min(this.getWidth(), this.getHeight()) * 0.001;
        return new Font("Monospaced", Font.BOLD, fontSize);
    }

    private void drawImage(Graphics2D g2, BufferedImage image, double entityX, double entityY, int imageScale) {
        int imageWidth = (int) (image.getWidth() * this.getWidth() * imageScale) / START_WIDTH;
        int imageHeight = (int) (imageWidth * windowRatio);
        int x = (int) (entityX * (this.getWidth() - imageWidth));
        int y = (int) (entityY * (this.getHeight() - imageHeight));
        g2.drawImage(image, x, y, imageWidth, imageHeight, null);
    }
}





