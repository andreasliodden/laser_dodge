package no.uib.inf101.sample.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import no.uib.inf101.sample.model.PlayerModel;
import no.uib.inf101.sample.model.EnemyModel;

public class GameView extends JPanel {
    private static final int START_WIDTH = 1600;
    private static final int START_HEIGHT = 900;

    private double scaleX;
    private double scaleY;

    private ColorTheme colorTheme;
    private PlayerModel playerModel;
    private EnemyModel enemyModel;


    public GameView(PlayerModel playerModel, EnemyModel enemyModel){
        this.playerModel = playerModel;
        this.enemyModel = enemyModel;
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

        initDimensions();

        drawPlayer(g2);
        drawEnemy(g2);
    }

    private void initDimensions() {
        scaleX = (double) this.getWidth() / START_WIDTH;
        scaleY = (double) this.getHeight() / START_HEIGHT;
    }
    // Fix problems with image scale

    private void drawPlayer(Graphics2D g2) {
        Inf101Graphics.drawCenteredImage(g2, playerModel.getImage(), playerModel.getX(), playerModel.getY(), Math.min(scaleX, scaleY) * 5);
    }

    private void drawEnemy(Graphics2D g2) {
        enemyModel.setPosition(this);
        Inf101Graphics.drawCenteredImage(g2, enemyModel.getImage(), this.getWidth() / 2, this.getHeight() / 2, Math.min(scaleX, scaleY) * 5);
    }
}
