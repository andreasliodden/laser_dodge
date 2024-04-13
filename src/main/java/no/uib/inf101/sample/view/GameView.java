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
import no.uib.inf101.sample.model.GameModel;

public class GameView extends JPanel {
    private static final int START_WIDTH = 1700;
    private static final int START_HEIGHT = 1000;
    private static final int IMAGE_SCALE = 5;

    private ColorTheme colorTheme;
    private GameModel gameModel;

    private double windowRatio;



    public GameView(GameModel gameModel){
        this.gameModel = gameModel;
        this.colorTheme = new DefaultColorTheme();
        this.setPreferredSize(new Dimension(
                START_WIDTH, START_HEIGHT));
        this.setBackground(colorTheme.getBackgroundColor());
        this.isDoubleBuffered();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawHealthBar(g2);
        drawPlayer(g2);
        drawProjectile(g2);
        drawEnemy(g2);
    }

    private void drawHealthBar(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(getFont(30));
        Inf101Graphics.drawCenteredString(g2, "HEALTH:", 0, 0, this.getWidth(), this.getHeight() * 0.075);
        Rectangle2D healthBar = new Rectangle2D.Double(
                    this.getWidth() * 0.4, this.getHeight() * 0.075,
                    this.getWidth() * 0.2, this.getHeight() * 0.05
                );
        
        Rectangle2D health = new Rectangle2D.Double(
                    this.getWidth() * 0.4, this.getHeight() * 0.075,
                    (this.getWidth() * 0.2 * gameModel.getPlayerHealth()) / 50,
                    this.getHeight() * 0.05
                );
        g2.setColor(getHealthColor());
        g2.fill(health);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.draw(healthBar);
        Inf101Graphics.drawCenteredString(g2, "" + gameModel.getPlayerHealth(), healthBar);

    }

    private Color getHealthColor() {
        int health = gameModel.getPlayerHealth();
        int greenValue = health * 4;
        int maxGreenValue = 255;
        int redValue = maxGreenValue - greenValue;
        return new Color(redValue, greenValue, 0);
    }

    public void updateWindowRatio() {
        windowRatio = this.getWidth() / this.getHeight();
    }

    private void drawPlayer(Graphics2D g2) {
        BufferedImage playerImage = gameModel.getPlayerImage();
        int imageWidth = (int) (playerImage.getWidth() * this.getWidth() * IMAGE_SCALE) / START_WIDTH;
        int imageHeight = (int) (imageWidth * windowRatio);
        int playerX = (int) (gameModel.getPlayerX() * (this.getWidth() - imageWidth));
        int playerY = (int) (gameModel.getPlayerY() * (this.getHeight() - imageHeight));
        g2.drawImage(playerImage, playerX, playerY, imageWidth, imageHeight, null);
    }

    private void drawEnemy(Graphics2D g2) {
        BufferedImage enemyImage = gameModel.getEnemyImage();
        int imageWidth = (int) (enemyImage.getWidth() * this.getWidth() * IMAGE_SCALE) / START_WIDTH;
        int imageHeight = (int) (imageWidth * windowRatio);
        int enemyX = (int) (gameModel.getEnemyX() * (this.getWidth() - imageWidth));
        int enemyY = (int) (gameModel.getEnemyY() * (this.getHeight() - imageHeight));

        g2.drawImage(enemyImage, enemyX, enemyY, imageWidth, imageHeight, null);
    }

    private void drawProjectile(Graphics2D g2) {
        double width = 0.02 * this.getWidth();
        double height = width * windowRatio;
        Rectangle2D projectileBox;
        Rectangle2D trailBox;
        double factor;
        int counter;
        
        for (int i = 0; i < gameModel.getNumberOfProjectiles(); i++) {
            counter = 0;
            ArrayList<Point2D> trail = gameModel.getProjectileTrail(i);
            projectileBox = new Rectangle2D.Double(
                    gameModel.getProjectileX(i) * (this.getWidth() - width), 
                    gameModel.getProjectileY(i) * (this.getHeight() - height),
                    width, height
                );
            g2.setColor(Color.RED);
            g2.fill(projectileBox);

            for (int j = trail.size() - 1; j >= 0; j--) {
                factor = Math.max(0, 1 - (trail.size() - 1 - j) * 0.05);
                Point2D point = trail.get(j);
                trailBox = new Rectangle2D.Double(
                    point.getX() * (this.getWidth() - width * factor),
                    point.getY() * (this.getHeight() - height * factor),
                    width * factor, height * factor
                );
                g2.setColor(new Color(Math.max(0, 255 - counter * 30), 0, 0));
                g2.fill(trailBox);
                counter++;
            }
        }
    }

    private Font getFont(int fontSize) {
        fontSize *= Math.min(this.getWidth(), this.getHeight()) * 0.001;
        return new Font("Monospaced", Font.BOLD, fontSize);
    }

}





