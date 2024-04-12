package no.uib.inf101.sample.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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

        drawPlayer(g2);
        drawProjectile(g2);
        drawEnemy(g2);
    }

    public void updateWindowRatio() {
        windowRatio = this.getWidth() / this.getHeight();
    }

    private void drawPlayer(Graphics2D g2) {
        BufferedImage playerImage = gameModel.getPlayerImage();
        int imageWidth = (int) (playerImage.getWidth() * this.getWidth() * IMAGE_SCALE) / START_WIDTH;
        int imageHeight = (int) (imageWidth * windowRatio);
        int imageX = (int) (gameModel.getPlayerX() * this.getWidth());
        int imageY = (int) (gameModel.getPlayerY() * this.getHeight());
        g2.drawImage(playerImage, imageX, imageY, imageWidth, imageHeight, null);
    }

    private void drawEnemy(Graphics2D g2) {
        BufferedImage enemyImage = gameModel.getEnemyImage();
        int imageWidth = (int) (enemyImage.getWidth() * this.getWidth() * IMAGE_SCALE) / START_WIDTH;
        int imageHeight = (int) (imageWidth * windowRatio);
        int imageX = (int) (gameModel.getEnemyX() * (this.getWidth() - imageWidth));
        int imageY = (int) (gameModel.getEnemyY() * (this.getHeight() - imageHeight));

        g2.drawImage(enemyImage, imageX, imageY, imageWidth, imageHeight, null);
    }

    private void drawProjectile(Graphics2D g2) {
        double width = 0.02 * this.getWidth();
        double height = width * windowRatio;
        Rectangle2D projectileBox = new Rectangle2D.Double(
                (gameModel.getProjectileX() * this.getWidth()) - (width / 2), 
                (gameModel.getProjectileY() * this.getHeight()) - (height / 2),
                width, 
                height);
    
        g2.setColor(Color.RED);
        g2.fill(projectileBox);
    }

}
