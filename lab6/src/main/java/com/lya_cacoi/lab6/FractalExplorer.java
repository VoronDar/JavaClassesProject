package com.lya_cacoi.lab6;

import com.lya_cacoi.lab4.FractalGenerator;
import com.lya_cacoi.lab4.JImageDisplay;
import com.lya_cacoi.lab4.Mandelbrot;
import com.lya_cacoi.lab5.BurningShip;
import com.lya_cacoi.lab5.Tricorn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    private JButton buttonSave;
    private JButton buttonReset;
    /**
     * visible range of complex plane
     */
    private final Rectangle2D.Double complexPlaneRange;
    /**
     * selector for fractal type
     */
    private JComboBox<FractalGenerator> fractalSelectorComboBox;

    /**
     * helper value showing the amount of rows waiting for render.
     * */
    private int rowsRemains = 0;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

    private FractalExplorer(int displaySize) {
        this.displaySize = displaySize;
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
        buttonReset = new JButton("Reset display");
        buttonReset.addActionListener(new ResetActionListener());

        // save button setup
        buttonSave = new JButton("Save fractal");
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
        enableUI(false);
        rowsRemains = displaySize;
        for (int y = 0; y < displaySize; y++) {
            FractalWorker drawRow = new FractalWorker(y);
            drawRow.execute();
        }
    }

    /**
     * all clickable elements becomes enabled when val = true and disabled when val = false
     * */
    public void enableUI(boolean val) {
        buttonSave.setEnabled(val);
        buttonReset.setEnabled(val);
        fractalSelectorComboBox.setEnabled(val);
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
     * listener on mouse click. zoom and redraw the fractal. Ignore clicks in process of fractal rendering
     */
    private class EmphasizeActionListener extends MouseAdapter implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (rowsRemains > 0) {
                return;
            }
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


    /**
     * Calculates colors of all pixel in the row for he selected complexPlaneRange and fractal type in background thread.
     * */
    class FractalWorker extends SwingWorker<Object, Object> {
        /** row index to render */
        private final int selectedY;
        /** already calculated colors for the row */
        private ArrayList<Integer> rowColors;

        public FractalWorker(int selectedY) {
            this.selectedY = selectedY;
        }

        @Override
        public Object doInBackground() {
            rowColors = new ArrayList<>(displaySize);

            for (int x = 0; x < displaySize; x++) {
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
                                selectedY
                        )
                );
                int rgbColor;
                if (count == -1) {
                    rgbColor = 0;
                } else {
                    float hue = 0.7f + (float) count / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                rowColors.add(rgbColor);
            }
            return null;
        }

        @Override
        public void done() {
            for (int x = 0; x < displaySize; x++) {
                displayImage.drawPixel(x, selectedY, rowColors.get(x));
            }
            displayImage.repaint(0, 0, selectedY, displaySize, 1);
            rowsRemains--;
            if (rowsRemains == 0)
                enableUI(true);
        }
    }
}

