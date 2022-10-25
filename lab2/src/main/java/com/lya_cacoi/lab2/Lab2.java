package com.lya_cacoi.lab2;

import java.util.Locale;
import java.util.Scanner;

/**
 * Console app for calculating the square of triangle of given points
 * */
public class Lab2 {
    public static void main(String[] args) {
        Point3d point1 = new Point3d();
        Point3d point2 = new Point3d();
        Point3d point3 = new Point3d();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input x coord for 1 point:");
        point1.setX(scanner.nextDouble());
        System.out.print("Input y coord for 1 point:");
        point1.setY(scanner.nextDouble());
        System.out.print("Input z coord for 1 point:");
        point1.setZ(scanner.nextDouble());
        System.out.print("Input x coord for 2 point:");
        point2.setX(scanner.nextDouble());
        System.out.print("Input y coord for 2 point:");
        point2.setY(scanner.nextDouble());
        System.out.print("Input z coord for 2 point:");
        point2.setZ(scanner.nextDouble());
        System.out.print("Input x coord for 3 point:");
        point3.setX(scanner.nextDouble());
        System.out.print("Input y coord for 3 point:");
        point3.setY(scanner.nextDouble());
        System.out.print("Input z coord for 3 point:");
        point3.setZ(scanner.nextDouble());


        scanner.close();
        if (point1.equals(point2) || point2.equals(point3) || point1.equals(point3)) {
            System.out.println("some points are equal - the square = 0");
        } else {
            double area = computeArea(point1, point2, point3);
            System.out.println("the square of the triangle of 3 dots: " + area);
        }

    }

    /**
     * @return the square of the triangle of given points
     * @throws IllegalStateException if any of the parameters is null
     * */
    public static double computeArea(Point3d point1, Point3d point2, Point3d point3) {
        if (point1 == null || point2 == null || point3 == null) {
            throw new IllegalStateException(
                    "all of the given params must be null. " +
                            "Got point1: " + point1 + ", point2: " + point2 + ", point3: " + point3
            );
        }

        double firstSide = point1.distanceTo(point2);
        double secondSide = point2.distanceTo(point3);
        double thirdSide = point1.distanceTo(point3);
        double p = (firstSide + secondSide + thirdSide) / 2.0;
        double result =  Math.sqrt(
                p
                        * (p - firstSide)
                        * (p - secondSide)
                        * (p - thirdSide)
        );
        return Double.parseDouble(String.format(Locale.ROOT, "%.2f", result));
    }
}
