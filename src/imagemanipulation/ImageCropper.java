/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemanipulation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCropper {
    public BufferedImage cropImage(BufferedImage image, Rectangle cropArea) {
        return image.getSubimage(cropArea.x, cropArea.y, cropArea.width, cropArea.height);
    }
}
