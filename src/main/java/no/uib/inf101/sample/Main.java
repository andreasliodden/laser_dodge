package no.uib.inf101.sample;

import javax.swing.JFrame;

import no.uib.inf101.sample.controller.GameController;
import no.uib.inf101.sample.model.GameModel;
import no.uib.inf101.sample.model.projectile.RandomProjectileFactory;
import no.uib.inf101.sample.model.projectile.ProjectileFactory;
import no.uib.inf101.sample.view.GameView;

public class Main {
    private static final String WINDOW_TITLE = "Laser Dodge";
    private static final int FRAME_X = 110;
    private static final int FRAME_Y = 40;
    public static void main(String[] args) {
        ProjectileFactory factory = new RandomProjectileFactory();
        GameModel gameModel = new GameModel(factory);
        GameView gameView = new GameView(gameModel);
        new GameController(gameModel, gameView);
    
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(FRAME_X, FRAME_Y);
        frame.setContentPane(gameView);
        frame.pack();
        frame.setVisible(true);
    }
}
