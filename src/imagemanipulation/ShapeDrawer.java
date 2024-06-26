/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemanipulation;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class ShapeDrawer {
    public BufferedImage drawShape(BufferedImage image, String shape) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage shapedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = shapedImage.createGraphics();
        g2d.setClip(getShape(shape, width, height));
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return shapedImage;
    }

    private Shape getShape(String shape, int width, int height) {
        switch (shape) {
            case "Circular":
                return new Ellipse2D.Double(0, 0, width, height);
            case "Rectangular":
                return new Rectangle2D.Double(0, 0, width, height);
            case "Pentagonal":
                return getPolygonShape(5, width, height);
            case "Triangular":
                return getPolygonShape(3, width, height);
            case "Hexagonal":
                return getPolygonShape(6, width, height);
            default:
                return new Rectangle2D.Double(0, 0, width, height);
        }
    }

    private Shape getPolygonShape(int sides, int width, int height) {
        Polygon polygon = new Polygon();
        for (int i = 0; i < sides; i++) {
            int x = (int) (width / 2 + (width / 2 * Math.cos(i * 2 * Math.PI / sides)));
            int y = (int) (height / 2 + (height / 2 * Math.sin(i * 2 * Math.PI / sides)));
            polygon.addPoint(x, y);
        }
        return polygon;
    }
}
