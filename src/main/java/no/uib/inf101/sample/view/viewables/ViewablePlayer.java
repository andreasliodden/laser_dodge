package no.uib.inf101.sample.view.viewables;

import java.awt.image.BufferedImage;

public interface ViewablePlayer extends ViewableEntity {
    BufferedImage getImage();
    int getHealth();
    int getMaxHealth();
}
