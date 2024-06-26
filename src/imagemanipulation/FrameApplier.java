/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemanipulation;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FrameApplier {
    private Map<String, Color> frameColors;

    public FrameApplier() {
        frameColors = new HashMap<>();

        // Adicionando várias cores de moldura
        frameColors.put("Simple Black Frame", Color.BLACK);
        frameColors.put("Red Frame", Color.RED);
        frameColors.put("Blue Frame", Color.BLUE);
        frameColors.put("Green Frame", Color.GREEN);
        frameColors.put("Yellow Frame", Color.YELLOW);
        frameColors.put("Gray Frame", Color.GRAY);
        frameColors.put("White Frame", Color.WHITE);
        frameColors.put("Pink Frame", Color.PINK);
        frameColors.put("Orange Frame", Color.ORANGE);
        frameColors.put("Light Wood Frame", new Color(210, 180, 140));
        frameColors.put("Dark Wood Frame", new Color(101, 67, 33));
        frameColors.put("Mahogany Frame", new Color(192, 64, 0));
        frameColors.put("Oak Wood Frame", new Color(201, 160, 120));
        frameColors.put("Walnut Wood Frame", new Color(102, 51, 0));
        frameColors.put("Cherry Wood Frame", new Color(139, 69, 19));
        frameColors.put("Fancy Gold Frame", new Color(255, 215, 0));
        frameColors.put("Silver Frame", new Color(192, 192, 192));
        frameColors.put("Bronze Frame", new Color(205, 127, 50));
        frameColors.put("Modern Black Frame", Color.BLACK);
        frameColors.put("Classic White Frame", Color.WHITE);
    }

    public BufferedImage applyFrame(BufferedImage image, String frameType, String shapeType, int thickness) {
        Color frameColor = frameColors.get(frameType);
        if (frameColor == null) {
            frameColor = Color.BLACK; // Default color if not found
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage framedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = framedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.setColor(frameColor);

        switch (shapeType) {
            case "Circular":
                drawCircularFrame(g2d, width, height, thickness);
                break;
            case "Retangular":
                drawRectangularFrame(g2d, width, height, thickness);
                break;
            case "Pentagonal":
                drawPentagonalFrame(g2d, width, height, thickness);
                break;
            case "Triangular":
                drawTriangularFrame(g2d, width, height, thickness);
                break;
            case "Hexagonal":
                drawHexagonalFrame(g2d, width, height, thickness);
                break;
            default:
                drawRectangularFrame(g2d, width, height, thickness);
                break;
        }

        g2d.dispose();
        return framedImage;
    }

    private void drawRectangularFrame(Graphics2D g2d, int width, int height, int thickness) {
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRect(0, 0, width, height);
    }

    private void drawCircularFrame(Graphics2D g2d, int width, int height, int thickness) {
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new Ellipse2D.Double(0, 0, width, height));
    }

    private void drawPentagonalFrame(Graphics2D g2d, int width, int height, int thickness) {
        GeneralPath pentagon = new GeneralPath();
        double angle = Math.toRadians(360.0 / 5);
        int cx = width / 2;
        int cy = height / 2;
        int radius = Math.min(width, height) / 2 - thickness;

        pentagon.moveTo(cx + radius, cy);
        for (int i = 1; i < 5; i++) {
            pentagon.lineTo(cx + radius * Math.cos(i * angle), cy + radius * Math.sin(i * angle));
        }
        pentagon.closePath();
        
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(pentagon);
    }

    private void drawTriangularFrame(Graphics2D g2d, int width, int height, int thickness) {
        GeneralPath triangle = new GeneralPath();
        int cx = width / 2;
        int cy = height / 2;
        int radius = Math.min(width, height) / 2 - thickness;

        triangle.moveTo(cx, cy - radius);
        triangle.lineTo(cx - radius, cy + radius);
        triangle.lineTo(cx + radius, cy + radius);
        triangle.closePath();

        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(triangle);
    }

    private void drawHexagonalFrame(Graphics2D g2d, int width, int height, int thickness) {
        GeneralPath hexagon = new GeneralPath();
        double angle = Math.toRadians(360.0 / 6);
        int cx = width / 2;
        int cy = height / 2;
        int radius = Math.min(width, height) / 2 - thickness;

        hexagon.moveTo(cx + radius, cy);
        for (int i = 1; i < 6; i++) {
            hexagon.lineTo(cx + radius * Math.cos(i * angle), cy + radius * Math.sin(i * angle));
        }
        hexagon.closePath();

        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(hexagon);
    }
}
