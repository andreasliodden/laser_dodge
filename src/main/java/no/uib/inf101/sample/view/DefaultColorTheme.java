package no.uib.inf101.sample.view;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme{
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color HEALTH_BAR = Color.GREEN;

    @Override
    public Color getBackgroundColor() {
       return BACKGROUND_COLOR;
    }

    @Override
    public Color getHealthBarColor() {
       return HEALTH_BAR;
    }
    
}
