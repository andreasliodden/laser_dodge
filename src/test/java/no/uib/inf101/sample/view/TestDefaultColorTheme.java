package no.uib.inf101.sample.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDefaultColorTheme {
    private ColorTheme colorTheme;
    
    @BeforeEach
    private void initColorTheme() {
        colorTheme = new DefaultColorTheme();
    }

    @Test
    public void getBackgroundColors() {
        Color angryBackground = new Color(30, 30, 30);
        Color happyBackground = new Color(190, 190, 190);

        assertEquals(angryBackground, colorTheme.getAngryBackground());
        assertEquals(happyBackground, colorTheme.getHappyBackground());
    }

    @Test
    public void getHappyTextColor() {
        Color happyColor = new Color(0, 150, 0);

        assertEquals(happyColor, colorTheme.getHappyTextColor());
    }
}
