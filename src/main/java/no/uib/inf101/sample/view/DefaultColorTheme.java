package no.uib.inf101.sample.view;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme{ 
    private static final Color ENEMY_BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color FRIENDLY_BACKGROUND_COLOR = new Color(190, 190, 190);
    private static final Color HAPPY_COLOR = new Color(0, 150, 0);


    @Override
    public Color getAngryBackground() {
       return ENEMY_BACKGROUND_COLOR;
    }

    @Override
    public Color getFriendlyBackground() {
       return FRIENDLY_BACKGROUND_COLOR;
    }

   @Override
   public Color getFriendlyColor() {
      return HAPPY_COLOR;
   }   
}
