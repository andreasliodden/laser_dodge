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
    private static final int IMAGE_SCALE = 6;

    private ColorTheme colorTheme;
    private PlayerModel playerModel;


    public GameView(PlayerModel playerModel, TurretModel turretModel){
        this.playerModel = playerModel;
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
    }

    private void drawPlayer(Graphics2D g2) {
        GamePosition playerPosition = playerModel.getPosition();
        Inf101Graphics.drawImage(g2, playerModel.getPlayerImage(), playerPosition.x(), playerPosition.y(), IMAGE_SCALE);
    }
}
