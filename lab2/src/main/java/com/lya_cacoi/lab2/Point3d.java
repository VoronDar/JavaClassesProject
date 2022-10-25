package com.lya_cacoi.lab2;

import java.util.Locale;
import java.util.Objects;

/**
 * 3d point class.
 * Warning: the class is mutable
 * Two Pont3d objects are equals if their getX(), getY(), zCoord fields are equal.
 */
public class Point3d extends Point2d {
    /**
     * zCoord
     * */
    private double zCoord;

    public Point3d(double xCoord, double yCoord, double zCoord) {
        super(xCoord, yCoord);
        this.zCoord = zCoord;
    }

    /**
     * Default value for all fields is 0.0
     */
    public Point3d() {
        this(0, 0, 0);
    }

    public void setZ(double zCoord) {
        this.zCoord = zCoord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3d)) return false;
        Point3d point3d = (Point3d) o;
        return point3d.getX() == getX()
                && point3d.getY() == getY()
                && point3d.zCoord == zCoord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), zCoord);
    }

    /**
     * returns the distance between two points rounded to 2 decimal places
     *
     * @throws IllegalStateException if the point parameter is null
     */
    public double distanceTo(Point3d point) {
        if (point == null) throw new IllegalStateException("point parameter must be not null");
        double distance = Math.sqrt(
                Math.pow(point.getX() - getX(), 2)
                        + Math.pow(point.getY() - getY(), 2)
                        + Math.pow(point.zCoord - zCoord, 2)
        );
        return Double.parseDouble(String.format(Locale.ROOT, "%.2f", distance));
    }

    @Override
    public String toString() {
        return "Point3d{" +
                "getX()=" + getX() +
                ", getY()=" + getY() +
                ", zCoord=" + zCoord +
                '}';
    }
}
