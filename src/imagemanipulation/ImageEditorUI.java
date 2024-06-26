/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagemanipulation;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageEditorUI extends JFrame {
    private JButton uploadButton;
    private JButton saveButton;
    private JButton cropButton;
    private JButton rotateButton;
    private JComboBox<String> shapeComboBox;
    private JComboBox<String> shadowComboBox;
    private JComboBox<String> frameComboBox;
    private JSpinner borderThicknessSpinner;
    private JPanel imagePanel;

    private ImageHandler imageHandler;
    private ShapeDrawer shapeDrawer;
    private ImageCropper imageCropper;
    private ShadowApplier shadowApplier;
    private FrameApplier frameApplier;
    private ImageSaver imageSaver;

    private BufferedImage currentImage;
    private BufferedImage originalImage;

    public ImageEditorUI() {
        setTitle("Image Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();

        imageHandler = new ImageHandler();
        shapeDrawer = new ShapeDrawer();
        imageCropper = new ImageCropper();
        shadowApplier = new ShadowApplier();
        frameApplier = new FrameApplier();
        imageSaver = new ImageSaver();
    }

    private void initUI() {
        uploadButton = new JButton("Upload de Imagem");
        saveButton = new JButton("Salvar Imagem");
        cropButton = new JButton("Recortar Imagem");
        rotateButton = new JButton("Rotacionar Imagem");
        shapeComboBox = new JComboBox<>(new String[]{"Nenhum", "Circular", "Retangular", "Pentagonal", "Triangular", "Hexagonal"});
        shadowComboBox = new JComboBox<>(new String[]{"Nenhum", "Light Shadow", "Medium Shadow", "Heavy Shadow"});
        frameComboBox = new JComboBox<>(new String[]{
                "Nenhum", "Simple Black Frame", "Red Frame", "Blue Frame", "Green Frame", "Yellow Frame",
                "Gray Frame", "White Frame", "Pink Frame", "Orange Frame", "Light Wood Frame", "Dark Wood Frame",
                "Mahogany Frame", "Oak Wood Frame", "Walnut Wood Frame", "Cherry Wood Frame", "Fancy Gold Frame",
                "Silver Frame", "Bronze Frame", "Modern Black Frame", "Classic White Frame"
        });
        borderThicknessSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentImage != null) {
                    g.drawImage(currentImage, 0, 0, this);
                }
            }
        };

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageHandler.uploadImage();
                currentImage = imageHandler.getImage();
                originalImage = imageHandler.getImage();
                imagePanel.repaint();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImage != null) {
                    JFileChooser fileChooser = new JFileChooser();
                    int option = fileChooser.showSaveDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String format = imageHandler.getImageFormat();
                        imageSaver.saveImage(currentImage, format, file.getAbsolutePath() + "." + format);
                        JOptionPane.showMessageDialog(null, "Imagem salva com sucesso!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sem imagem!");
                }
            }
        });

        cropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImage != null) {
                    Rectangle cropArea = new Rectangle(50, 50, currentImage.getWidth() - 100, currentImage.getHeight() - 100);
                    currentImage = imageCropper.cropImage(currentImage, cropArea);
                    imagePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Sem imagem!");
                }
            }
        });

        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImage != null) {
                    currentImage = rotateImage(currentImage, 90);
                    imagePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Sem imagem!");
                }
            }
        });

        shapeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImage != null) {
                    String selectedShape = (String) shapeComboBox.getSelectedItem();
                    if ("Nenhum".equals(selectedShape)) {
                        currentImage = originalImage;
                    } else {
                        currentImage = shapeDrawer.drawShape(currentImage, selectedShape);
                    }
                    imagePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Sem imagem!");
                }
            }
        });

        shadowComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImage != null) {
                    String selectedShadow = (String) shadowComboBox.getSelectedItem();
                    if ("Nenhum".equals(selectedShadow)) {
                        currentImage = originalImage;
                    } else {
                        currentImage = shadowApplier.applyShadow(currentImage, selectedShadow);
                    }
                    imagePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Sem imagem!");
                }
            }
        });

        frameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImage != null) {
                    String selectedFrame = (String) frameComboBox.getSelectedItem();
                    String selectedShape = (String) shapeComboBox.getSelectedItem();
                    if ("Nenhum".equals(selectedFrame)) {
                        currentImage = originalImage;
                    } else {
                        int thickness = (int) borderThicknessSpinner.getValue();
                        currentImage = frameApplier.applyFrame(currentImage, selectedFrame, selectedShape, thickness);
                    }
                    imagePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Sem imagem!");
                }
            }
        });

        borderThicknessSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (currentImage != null) {
                    String selectedFrame = (String) frameComboBox.getSelectedItem();
                    String selectedShape = (String) shapeComboBox.getSelectedItem();
                    if (!"Nenhum".equals(selectedFrame)) {
                        int thickness = (int) borderThicknessSpinner.getValue();
                        currentImage = frameApplier.applyFrame(currentImage, selectedFrame, selectedShape, thickness);
                        imagePanel.repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sem imagem!");
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(uploadButton);
        controlPanel.add(saveButton);
        controlPanel.add(cropButton);
        controlPanel.add(rotateButton);
        controlPanel.add(shapeComboBox);
        controlPanel.add(shadowComboBox);
        controlPanel.add(frameComboBox);
        controlPanel.add(new JLabel("Borda:"));
        controlPanel.add(borderThicknessSpinner);

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(imagePanel), BorderLayout.CENTER);
    }

    private BufferedImage rotateImage(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(Math.toRadians(angle), width / 2, height / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageEditorUI().setVisible(true);
            }
        });
    }
}
