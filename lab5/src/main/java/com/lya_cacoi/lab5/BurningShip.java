package com.lya_cacoi.lab5;

import com.lya_cacoi.lab4.FractalGenerator;

import java.awt.geom.Rectangle2D;

/**
 * BurningShip fractal generator
 */
public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    /** initial range:  (-2, -2.5) to (2, 1.5)*/
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.height = 4;
        range.width = 4;
    }

    /**
     * burning ship function: z_n = (|Re(z_{n-1})| + i |Im(z_{n-1})|)^2 + c.
     * Re(z) = x; |Re(z)| = |x|
     * Im(z) =y; |Im(z)| = |y|; i |Im(z)| = i|y|
     * (|x| + i|y|) = x^2 + 2|x||y| - y^2
     * */
    @Override
    public int numIterations(double x, double y) {
        double currentIY = 0;
        double currentX = 0;
        int iterationsCount = 0;
        while (iterationsCount < MAX_ITERATIONS) {
            iterationsCount++;
            double newX = currentX * currentX - currentIY * currentIY + x;
            double newIY = Math.abs(2 * currentX * currentIY) + y;
            currentX = newX;
            currentIY = newIY;
            if ((currentX * currentX + currentIY * currentIY) > 4)
                break;
        }
        if (iterationsCount == MAX_ITERATIONS) {
            return -1;
        } else {
            return iterationsCount;
        }
    }

    @Override
    public String toString() {
        return "Burning ship";
    }
}
