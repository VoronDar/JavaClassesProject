package com.lya_cacoi.lab2;

import java.util.Locale;
import java.util.Objects;

/**
 * 3d point class.
 * Warning: the class is mutable
 * Two Pont3d objects are equals if their xCoord, yCoord, zCoord fields are equal.
 */
public class Point3d implements Comparable<Point3d> {
    private double xCoord;
    private double yCoord;
    private double zCoord;

    public Point3d(double xCoord, double yCoord, double zCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }

    /**
     * Default value for all fields is 0.0
     */
    public Point3d() {
        this(0, 0, 0);
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public double getzCoord() {
        return zCoord;
    }

    public void setzCoord(double zCoord) {
        this.zCoord = zCoord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3d)) return false;
        Point3d point3d = (Point3d) o;
        return point3d.xCoord == xCoord
                && point3d.yCoord == yCoord
                && point3d.zCoord == zCoord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoord, yCoord, zCoord);
    }

    @Override
    public int compareTo(Point3d point3d) {
        double thisAbsoluteValue = xCoord + yCoord + zCoord;
        double theirAbsoluteValue = point3d.xCoord + point3d.yCoord + point3d.zCoord;
        return Double.compare(thisAbsoluteValue, theirAbsoluteValue);
    }

    /**
     * returns the distance between two points rounded to 2 decimal places
     *
     * @throws IllegalStateException if the point parameter is null
     */
    public double distanceTo(Point3d point) {
        if (point == null) throw new IllegalStateException("point parameter must be not null");
        double distance = Math.sqrt(
                Math.pow(point.xCoord - xCoord, 2)
                        + Math.pow(point.yCoord - yCoord, 2)
                        + Math.pow(point.zCoord - zCoord, 2)
        );
        return Double.parseDouble(String.format(Locale.ROOT, "%.2f", distance));
    }

    @Override
    public String toString() {
        return "Point3d{" +
                "xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", zCoord=" + zCoord +
                '}';
    }
}
