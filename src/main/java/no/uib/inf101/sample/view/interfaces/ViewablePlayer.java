package no.uib.inf101.sample.view.interfaces;

import java.awt.image.BufferedImage;

public interface ViewablePlayer extends ViewableEntity {
    BufferedImage getImage();
    int getHealth();
}
