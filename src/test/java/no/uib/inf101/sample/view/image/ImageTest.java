package no.uib.inf101.sample.view.image;

import java.awt.image.BufferedImage;

public class ImageTest {
    protected boolean imagesAreEqual(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() == image2.getWidth() && image1.getHeight() == image2.getHeight()) {
            for (int pixelRow = 0; pixelRow < image1.getHeight(); pixelRow++) {
                for (int pixelCol = 0; pixelCol < image1.getWidth(); pixelCol++) {
                    // Return false if the RGB-code for the current pixel is not equal
                    if (image1.getRGB(pixelRow, pixelCol) != image2.getRGB(pixelRow, pixelCol)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
