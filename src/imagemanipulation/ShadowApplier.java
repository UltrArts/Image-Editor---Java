/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemanipulation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShadowApplier {
    public BufferedImage applyShadow(BufferedImage image, String shadowType) {
        int shadowSize;
        Color shadowColor;

        switch (shadowType) {
            case "Light Shadow":
                shadowSize = 5;
                shadowColor = new Color(0, 0, 0, 50);
                break;
            case "Medium Shadow":
                shadowSize = 10;
                shadowColor = new Color(0, 0, 0, 100);
                break;
            case "Heavy Shadow":
                shadowSize = 20;
                shadowColor = new Color(0, 0, 0, 150);
                break;
            default:
                return image;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage shadowedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = shadowedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(shadowColor);

        g2d.fillRoundRect(0, 0, width, height, shadowSize, shadowSize);
        g2d.dispose();
        return shadowedImage;
    }
}
