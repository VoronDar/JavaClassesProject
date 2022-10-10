package com.lya_cacoi.lab5;

import com.lya_cacoi.lab4.FractalGenerator;
import com.lya_cacoi.lab4.JImageDisplay;
import com.lya_cacoi.lab4.Mandelbrot;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * Fractal selector renderer
 */
public class FractalExplorer {

    /**
     * display size in pixels
     */
    private final int displaySize;

    /**
     * fractal container
     */
    private JImageDisplay displayImage;
    private FractalGenerator fractalGenerator;
    /**
     * visible range of complex plane
     */
    private final Rectangle2D.Double complexPlaneRange;
    /**
     * selector for fractal type
     */
    private JComboBox<FractalGenerator> fractalSelectorComboBox;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

    private FractalExplorer(int sizeDisplay) {
        this.displaySize = sizeDisplay;
        this.fractalGenerator = new Mandelbrot();
        this.complexPlaneRange = new Rectangle2D.Double(0, 0, 0, 0);
        fractalGenerator.getInitialRange(this.complexPlaneRange);
    }

    /**
     * setup UI and UX
     */
    public void createAndShowGUI() {
        // display image setup
        displayImage = new JImageDisplay(displaySize, displaySize);
        displayImage.addMouseListener(new EmphasizeActionListener());

        // reset button setup
        JButton buttonReset = new JButton("Reset display");
        buttonReset.addActionListener(new ResetActionListener());

        // save button setup
        JButton buttonSave = new JButton("Save fractal");
        buttonSave.addActionListener(new SaveActionListener());


        // combobox setup
        JLabel label = new JLabel("Fractal:");
        fractalSelectorComboBox = new JComboBox<>();
        fractalSelectorComboBox.addItem(new Mandelbrot());
        fractalSelectorComboBox.addItem(new Tricorn());
        fractalSelectorComboBox.addItem(new BurningShip());
        fractalSelectorComboBox.addActionListener(new ComboBoxSelectItemActionListener());

        // panels setup
        JPanel jPanelSelector = new JPanel();
        JPanel jPanelButtons = new JPanel();
        jPanelSelector.add(label, BorderLayout.CENTER);
        jPanelSelector.add(fractalSelectorComboBox, BorderLayout.CENTER);
        jPanelButtons.add(buttonReset, BorderLayout.CENTER);
        jPanelButtons.add(buttonSave, BorderLayout.CENTER);

        // frame setup
        JFrame frame = new JFrame("fractal renderer");
        frame.setLayout(new BorderLayout());
        frame.add(displayImage, BorderLayout.CENTER);
        frame.add(jPanelSelector, BorderLayout.NORTH);
        frame.add(jPanelButtons, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * render fractal with the current zoom and position
     */
    private void drawFractal() {
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {
                int count = fractalGenerator.numIterations(
                        FractalGenerator.getCoord(
                                complexPlaneRange.x,
                                complexPlaneRange.x + complexPlaneRange.width,
                                displaySize,
                                x
                        ),
                        FractalGenerator.getCoord(
                                complexPlaneRange.y,
                                complexPlaneRange.y + complexPlaneRange.width,
                                displaySize,
                                y
                        )
                );
                int rgbColor;
                if (count == -1) {
                    rgbColor = 0;
                } else {
                    float hue = 0.7f + (float) count / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                displayImage.drawPixel(x, y, rgbColor);
            }
        }
        displayImage.repaint();
    }

    /**
     * listener on 'reset' button. restore zoom and redraw the fractal.
     */
    private class ResetActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayImage.clearImage();
            fractalGenerator.getInitialRange(complexPlaneRange);
            drawFractal();
        }
    }

    /**
     * listener on 'save' button.
     */
    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int t = fileChooser.showSaveDialog(displayImage);
            if (t == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(displayImage.picture, "png", fileChooser.getSelectedFile());
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(
                            displayImage,
                            ee.getMessage(),
                            "Error saving fractal",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    /**
     * listener on 'combobox' selection. restore zoom and redraw the fractal.
     */
    private class ComboBoxSelectItemActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fractalGenerator = (FractalGenerator) fractalSelectorComboBox.getSelectedItem();
            fractalGenerator.getInitialRange(complexPlaneRange);
            drawFractal();
        }
    }

    /**
     * listener on mouse click. zoom and redraw the fractal
     */
    private class EmphasizeActionListener extends MouseAdapter implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            double x = FractalGenerator.getCoord(
                    complexPlaneRange.x,
                    complexPlaneRange.x + complexPlaneRange.width, displaySize, e.getX()
            );
            double y = FractalGenerator.getCoord(
                    complexPlaneRange.y,
                    complexPlaneRange.y + complexPlaneRange.width,
                    displaySize,
                    e.getY()
            );
            fractalGenerator.recenterAndZoomRange(complexPlaneRange, x, y, 0.5);
            drawFractal();
        }
    }
}

