/*
 * Course: CSC1110 - 111
 * Fall 2024
 * Homework 12
 * Name: Alexander Horton
 * Last Updated: 11/24/2024
 */

package hortona;

/**
 * parallelogram
 */
public class Parallelogram extends Quadrilateral {
    /**
     * creates a parallelogram
     * @param longerSide longer side length
     * @param shorterSide shorter side length
     * @param angle acute angle
     */
    public Parallelogram(double longerSide, double shorterSide, double angle) {
        sideA = longerSide;
        sideC = longerSide;
        sideB = shorterSide;
        sideD = shorterSide;
        angleAB = angle;
        angleCD = angle;
        angleBC = (2*RIGHT_ANGLE) - angle;
        angleDA = (2*RIGHT_ANGLE) - angle;
    }

    @Override
    public double getArea() {
        return sideA * getHeight();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Color.BLUE);
        sb.append("Parallelogram");
        sb.append("\nLonger Side: ");
        if (sideA - (int)sideA != 0) {
            sb.append(FORMATTER.format(sideA));
        } else {
            sb.append((int)sideA);
        }
        sb.append("\nShorter Side: ");
        if (sideB - (int)sideB != 0) {
            sb.append(FORMATTER.format(sideB));
        } else {
            sb.append((int)sideB);
        }
        sb.append("\nAcute Angle: ");
        if (angleCD - (int)angleCD != 0) {
            sb.append(FORMATTER.format(angleCD));
        } else {
            sb.append((int)angleCD);
        }
        sb.append("\nObtuse Angle: ");
        if (angleBC - (int)angleBC != 0) {
            sb.append(FORMATTER.format(angleBC));
        } else {
            sb.append((int)angleBC);
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
        if (o instanceof Parallelogram) {
            return sideA == ((Parallelogram) o).sideA &&
                    sideB == ((Parallelogram) o).sideB &&
                    sideC == ((Parallelogram) o).sideC &&
                    sideD == ((Parallelogram) o).sideD &&
                    angleAB == ((Parallelogram) o).angleAB &&
                    angleBC == ((Parallelogram) o).angleBC &&
                    angleCD == ((Parallelogram) o).angleCD &&
                    angleDA == ((Parallelogram) o).angleDA &&
                    getArea() == ((Parallelogram) o).getArea();
        } else {
            return false;
        }
    }

    private double getHeight() {
        return sideB * Math.sin(Math.toRadians(angleAB));
    }
}
