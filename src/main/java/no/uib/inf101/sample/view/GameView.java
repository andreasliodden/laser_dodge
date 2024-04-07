package no.uib.inf101.sample.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import no.uib.inf101.sample.model.GamePosition;
import no.uib.inf101.sample.model.PlayerModel;
import no.uib.inf101.sample.model.TurretModel;

public class GameView extends JPanel {
    private static final int START_WIDTH = 1000;
    private static final int START_HEIGHT = 800;

    private double scaleX;
    private double scaleY;

    private ColorTheme colorTheme;
    private PlayerModel playerModel;
    private TurretModel turretModel;


    public GameView(PlayerModel playerModel, TurretModel turretModel){
        this.playerModel = playerModel;
        this.turretModel = turretModel;
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
        drawTurret(g2);
    }

    private void initDimensions() {
        scaleX = (double) this.getWidth() / START_WIDTH;
        scaleY = (double) this.getHeight() / START_HEIGHT;
    }
    // Fix problems with image scale

    private void drawPlayer(Graphics2D g2) {
        GamePosition playerPosition = playerModel.getPosition();
        Inf101Graphics.drawImage(g2, playerModel.getImage(), playerPosition.x(), playerPosition.y(), Math.min(scaleX, scaleY) * 5);
    }

    private void drawTurret(Graphics2D g2) {
        turretModel.setPosition(this);
        Inf101Graphics.drawCenteredImage(g2, turretModel.getImage(), this.getWidth() / 2, this.getHeight() / 2, Math.min(scaleX, scaleY) * 0.75);
    }
}
