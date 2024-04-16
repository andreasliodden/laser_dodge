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
import no.uib.inf101.sample.model.GameState;

public class GameView extends JPanel {
    private static final int START_WIDTH = 1700;
    private static final int START_HEIGHT = 1000;
    private static final BufferedImage APPLE = Inf101Graphics.loadImageFromResources("apple.png");

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

        if (gameModel.getCurrentState() == GameState.EATING) {
            g2.setColor(new Color(190, 190, 190));
            g2.fill(this.getBounds());
            drawHealthBar(g2, Color.BLACK);
            drawPlayer(g2);
            drawApples(g2);
            drawEnemy(g2);
        } else {
            drawHealthBar(g2, Color.WHITE);
            drawPlayer(g2);
            drawProjectile(g2);
            drawEnemy(g2);
        }
    }

    private void drawApples(Graphics2D g2) {
        for (int i = 0; i < gameModel.getNumberOfProjectiles(); i++) {
            drawImage(g2, APPLE, gameModel.getProjectileX(i), gameModel.getProjectileY(i), 3);
        }
    }

    private void drawHealthBar(Graphics2D g2, Color textColor) {
        g2.setColor(textColor);
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
        g2.setColor(textColor);
        g2.setStroke(new BasicStroke(2));
        g2.draw(healthBar);
        g2.setColor(Color.WHITE);
        Inf101Graphics.drawCenteredString(g2, "" + gameModel.getPlayerHealth(), healthBar);

    }

    private Color getHealthColor() {
        int multiplier = 4;
        int greenValue = gameModel.getPlayerHealth() * multiplier;
        int maxGreenValue = 255;
        int redValue = maxGreenValue - greenValue;
        return new Color(redValue, greenValue, 0);
    }

    public void updateWindowRatio() {
        windowRatio = this.getWidth() / this.getHeight();
    }

    private void drawPlayer(Graphics2D g2) {
        drawImage(g2, gameModel.getPlayerImage(), gameModel.getPlayerX(), gameModel.getPlayerY(), 5);
    }

    private void drawEnemy(Graphics2D g2) {
        drawImage(g2, gameModel.getEnemyImage(), gameModel.getEnemyX(), gameModel.getEnemyY(), 6);
    }

    private void drawProjectile(Graphics2D g2) {
        double width = 0.02 * this.getWidth();
        double height = width * windowRatio;
        Rectangle2D projectileBox, trailBox;
        double resizingFactor;
        int colorFactor;
        
        for (int i = 0; i < gameModel.getNumberOfProjectiles(); i++) {
            colorFactor = 0;
            ArrayList<Point2D> trail = gameModel.getProjectileTrail(i);
            projectileBox = new Rectangle2D.Double(
                    gameModel.getProjectileX(i) * (this.getWidth() - width), 
                    gameModel.getProjectileY(i) * (this.getHeight() - height),
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





