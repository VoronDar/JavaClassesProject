package com.lya_cacoi.lab4;

import java.awt.geom.Rectangle2D;

/**
 * Generator of mandelbrot fractal
 * */
public class Mandelbrot extends FractalGenerator {

    /**
     * Amount of iterations for calculating the fractal line. Can be risen for the better quality, but it will cause bad time performance
     * */
    private static final int MAX_ITERATIONS = 2000;

    /**
     * initial range: (-2 - 1.5i) - (1 + 1.5i)
     * */
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;
    }

    /**
     * mandelbrot function : z_n = z_{n-1}^2 + c (markdown)
     * z_0 = 0
     * |z| < 2^2
     * z^2 = x^2 + 2xiy - y^2
     * */
    @Override
    public int numIterations(double x, double y) {
        double currentIY = 0;
        double currentX = 0;
        int iterationCount = 0;
        while (iterationCount < MAX_ITERATIONS) {
            iterationCount++;
            double newX = currentX * currentX - currentIY * currentIY + x;
            double newIY = 2 * currentX * currentIY + y;
            currentX = newX;
            currentIY = newIY;

            if (currentX * currentX + currentIY * currentIY > 4)
                break;
        }
        if (iterationCount == MAX_ITERATIONS) {
            return -1;
        } else {
            return iterationCount;
        }
    }

    @Override
    public String toString() {
        return "Mandelbrot";
    }
}
