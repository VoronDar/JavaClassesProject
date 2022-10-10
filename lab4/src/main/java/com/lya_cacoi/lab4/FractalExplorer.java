package com.lya_cacoi.lab4;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;

/**
 * Fractal mandelbrot renderer
 */
public class FractalExplorer {

    /** display size in pixels */
    private final int displaySize;

    /** fractal container */
    private JImageDisplay displayImage;
    private final FractalGenerator fractalGenerator;
    /** visible range of complex plane */
    private final Rectangle2D.Double complexPlaneRange;

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
        JButton button = new JButton("Reset display");
        button.addActionListener(new ResetActionListener());

        // frame setup
        JFrame frame = new JFrame("fractal renderer");
        frame.setLayout(new BorderLayout());
        frame.add(displayImage, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
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

