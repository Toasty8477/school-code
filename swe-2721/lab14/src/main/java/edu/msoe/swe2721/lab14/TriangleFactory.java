package edu.msoe.swe2721.lab14;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This class will serve as a factory, creatiung triangles as is applicable.
 */
public class TriangleFactory {

    private List<Triangle> triangles = new LinkedList<>();

    /**
     * This method will parse the input stream until the text quit is received.
     * 
     * @param lineReader This is a scanner that will read in one line of text at a
     *                   time. If three sides are read in, a triangle will be
     *                   instantiated and added to the list.
     */
    public void parseStream(Scanner lineReader) {
        boolean keepGoing = true;
        double a;
        double b;
        double c;

        while (keepGoing && lineReader.hasNext()) {
            String line = lineReader.nextLine();
            if (line.trim().toLowerCase().equals("quit")) {
                keepGoing = false;
            } else {
                String[] sides = line.split(" ");
                // Only do stuff if there is three sides
                if (sides.length == 3) {
                    if (sides[0].matches("[0-9]+")) { // Check if this side is a digit
                        // if side is a digit parse it
                        a = Double.parseDouble(sides[0]);
                    } else {
                        try {
                            // try and parse a number from the string
                            a = NumericParser.parseString(sides[0]);
                        } catch (NumericParseException e) {
                            // if parsing fails make the triangle invalid
                            a = -1;
                        }
                    }
                    if (sides[1].matches("[0-9]+")) { // Check if this side is a digit
                        // if side is a digit parse it
                        b = Double.parseDouble(sides[1]);
                    } else {
                        try {
                            // try and parse a number from the string
                            b = NumericParser.parseString(sides[1]);
                        } catch (NumericParseException e) {
                            // if parsing fails make the triangle invalid
                            b = -1;
                        }
                    }
                    if (sides[2].matches("[0-9]+")) { // Check if this side is a digit
                        // if side is a digit parse it
                        c = Double.parseDouble(sides[2]);
                    } else {
                        try {
                            // try and parse a number from the string
                            c = NumericParser.parseString(sides[2]);
                        } catch (NumericParseException e) {
                            // if parsing fails make the triangle invalid
                            c = -1;
                        }
                    }
                    Triangle triangle;
                    // Finally try to construct a triangle
                    try {
                        triangle = new Triangle(a, b, c);
                    } catch (TriangleConstructionException e) {
                        // Triangle must have negative side lenghts, make the variable null so we can still use it
                        triangle = null;
                    }
                    
                    // Check if the triangle is valid and add it if it is
                    if (triangle != null && triangle.determineIfValid()) {
                        triangles.add(triangle);
                    }
                }
            }
        }
    }

    /**
     * This method will return the number of triangles that have been added into the
     * array.
     * 
     * @return The number of valid read triangles will be returned.
     */
    public int getCount() {
        return triangles.size();
    }

    /**
     * This method will iterate over the valid triangles and obtain their
     * representations
     * 
     * @return The string representing the concatenation of all valid triangles will
     *         be returned.
     */
    public String obtainTriangleText() {
        // Just return an empty string if there's no triangles
        if (triangles.size() == 0) {
            return "";
        }
        // Do the thing
        StringBuilder sb = new StringBuilder();
        for (Triangle triangle : triangles) {
            sb.append(triangle.obtainTextualRepresentation() + "\n");
        }
        // Remove final newline
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     * This method will obtain the total area for all triangles in the system.
     * 
     * @return The total area will be returned. It must be accurate to at least the
     *         nearest 0.001.
     */
    public double obtainTotalArea() {
        // If there are not triangles don't even bother
        if (triangles.size() == 0) {
            return 0;
        }

        double totalArea = 0;
        for (Triangle triangle : triangles) {
            totalArea += triangle.calculateArea();
        }
        totalArea = BigDecimal.valueOf(totalArea).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return totalArea;
    }
}
