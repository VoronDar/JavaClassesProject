package com.lya_cacoi.lab2;

import java.util.Scanner;

/**
 * Console app for calculating the square of triangle of given points
 * */
public class Lab2 {
    public static void main(String[] args) {

        Point3d point1 = new Point3d();
        Point3d point2 = new Point3d();
        Point3d point3 = new Point3d();

        Scanner scanner = new Scanner("3\n2\n1\n1\n2\n3\n4\n3\n3");
        System.out.println("Input x coord for 1 point:");
        point1.setxCoord(Double.parseDouble(scanner.nextLine()));
        System.out.println("Input y coord for 1 point:");
        point1.setyCoord(scanner.nextDouble());
        System.out.println("Input z coord for 1 point:");
        point1.setzCoord(scanner.nextDouble());
        System.out.println("Input x coord for 2 point:");
        point2.setxCoord(scanner.nextDouble());
        System.out.println("Input y coord for 2 point:");
        point2.setyCoord(scanner.nextDouble());
        System.out.println("Input z coord for 2 point:");
        point2.setzCoord(scanner.nextDouble());
        System.out.println("Input x coord for 3 point:");
        point3.setxCoord(scanner.nextDouble());
        System.out.println("Input y coord for 3 point:");
        point3.setyCoord(scanner.nextDouble());
        System.out.println("Input z coord for 3 point:");
        point3.setzCoord(scanner.nextDouble());

        scanner.close();
        if (point1.equals(point2) || point2.equals(point3) || point1.equals(point3)) {
            System.out.println("some points are equal - the square = 0");
        } else {
            double area = computeArea(point1, point2, point3);
            System.out.print("the square of the triangle of 3 dots: " + area);
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
        return Math.sqrt(
                p
                        * (p - firstSide)
                        * (p - secondSide)
                        * (p - thirdSide)
        );
    }
}
