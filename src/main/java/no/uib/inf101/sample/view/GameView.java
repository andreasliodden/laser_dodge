package no.uib.inf101.sample.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import no.uib.inf101.sample.model.GameModel;

public class GameView extends JPanel {
    private static final int START_WIDTH = 1400;
    private static final int START_HEIGHT = 900;

    private double widthScale;
    private double heightScale;

    private ColorTheme colorTheme;
    private GameModel gameModel;


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
        drawEnemy(g2);
    }

    public void updateWindowScale() {
        widthScale = (double) this.getWidth() / START_WIDTH;
        heightScale = (double) this.getHeight() / START_HEIGHT;
    }

    public double getWindowScale() {
        return Math.max(widthScale, heightScale);
    }

    private void drawPlayer(Graphics2D g2) {
        Inf101Graphics.drawCenteredImage(g2, gameModel.getPlayerImage(), gameModel.getPlayerX(), gameModel.getPlayerY(), this.getWindowScale() * 5);
    }

    private void drawEnemy(Graphics2D g2) {
        Inf101Graphics.drawCenteredImage(g2, gameModel.getEnemyImage(), this.getWidth() / 2, this.getHeight() / 2,  this.getWindowScale() * 5);
    }

}
