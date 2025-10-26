/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 12
 * Name: Alexander Horton
 * Last Updated: 11/24/2024
 */

package hortona;

/**
 * Triangle
 */
public class Triangle implements Shape {
    protected final double sideA;
    protected final double sideB;
    protected final double base;
    protected double angleAB;
    protected double angleBaseB;
    protected double angleBaseA;

    /**
     * creates a triangle
     * @param sideA side a
     * @param sideB side b
     * @param base base of triangle
     */
    public Triangle(double sideA, double sideB, double base) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.base = base;
        setAngles();
    }
    @Override
    public double getArea() {
        double s = (sideA + sideB + base) / 2;
        return Math.sqrt(s*(s-sideA)*(s-sideB)*(s-base));
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this instanceof RightTriangle) {
            sb.append(Color.YELLOW);
            sb.append("Right Triangle");
        } else {
            sb.append(Color.MAGENTA);
            sb.append("Triangle");
        }
        sb.append("\nSide A: ");
        if (sideA - (int)sideA != 0) {
            sb.append(FORMATTER.format(sideA));
        } else {
            sb.append((int)sideA);
        }
        sb.append("\nSide B: ");
        if (sideB - (int)sideB != 0) {
            sb.append(FORMATTER.format(sideB));
        } else {
            sb.append((int)sideB);
        }
        sb.append("\nBase: ");
        if (base - (int)base != 0) {
            sb.append(FORMATTER.format(base));
        } else {
            sb.append((int)base);
        }
        sb.append("\nAngleAB: ");
        if (angleAB - (int)angleAB != 0) {
            sb.append(FORMATTER.format(angleAB));
        } else {
            sb.append((int)angleAB);
        }
        sb.append("\nAngleBaseB: ");
        if (angleBaseB - (int)angleBaseB != 0) {
            sb.append(FORMATTER.format(angleBaseB));
        } else {
            sb.append((int)angleBaseB);
        }
        sb.append("\nAngleBaseA: ");
        if (angleBaseA - (int)angleBaseA != 0) {
            sb.append(FORMATTER.format(angleBaseA));
        } else {
            sb.append((int)angleBaseA);
        }
        sb.append("\nArea: ");
        if (getArea() - (int)getArea() != 0) {
            sb.append(FORMATTER.format(getArea()));
        } else {
            sb.append((int)getArea());
        }
        sb.append("\n");
        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Triangle) {
            return this.sideA == ((Triangle) o).sideA &&
                    this.sideB == ((Triangle) o).sideB &&
                    this.base == ((Triangle) o).base &&
                    this.angleAB == ((Triangle) o).angleAB &&
                    this.angleBaseA == ((Triangle) o).angleBaseA &&
                    this.angleBaseB == ((Triangle) o).angleBaseB &&
                    this.getArea() == ((Triangle) o).getArea();
        } else {
            return false;
        }
    }
    private void setAngles() {
        double a = sideA;
        double b = sideB;
        double c = base;
        angleAB = Math.toDegrees(Math.acos(
                (Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2))/(2*a*b)));
        angleBaseA = Math.toDegrees(Math.acos(
                (Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2))/(2*a*c)));
        angleBaseB = Math.toDegrees(Math.acos(
                (Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2))/(2*b*c)));
    }
}
