
import java.util.Arrays;
import java.util.Comparator;

/*
 * Copyright (C) 2016 Michael <GrubenM@GMail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Michael <GrubenM@GMail.com>
 */
public class FastCollinearPoints {
    private LineSegment[] segments;
    private int segmentHead;
    
    /**
     * Finds all line segments containing 4 or more points
     * @param points 
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
        segments = new LineSegment[points.length];
        segmentHead = 0;
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i]);
            Arrays.sort(points, points[i].slopeOrder());
            for (Point p: points) System.out.print(p);
            System.out.println();
            for (int j = 1; j < points.length; j++) {
                System.out.println("Anchor of " + points[0]);
                double slopeA = points[0].slopeTo(points[j]);
                int c = 0;
                System.out.print("Examining " + points[j]);
                System.out.println(" slope of " + slopeA);
                while (++j < points.length && 
                        slopeA == points[0].slopeTo(points[j])) {
                    c++;
                    System.out.print("Slope matched slope to " + points[j]);
                    System.out.println(" slope of " + points[0].slopeTo(points[j]));
                }
                j--;
                System.out.println(c);
                if (c >= 2) {
                    System.out.println("Adding " + points[0] + " and " + points[j]);
                    segments[segmentHead] = new LineSegment(points[0], points[j]);
                    segmentHead++;
                    j += c;
                }
            }
        }
    }
    
    /**
     * The number of line segments
     * @return 
     */
    public int numberOfSegments() {
        return segmentHead;
    }
    
    /**
     * The line segments
     * @return 
     */
    public LineSegment[] segments() {
        return segments;
    }
    
    public static void main(String[] args) {
        Point[] points = new Point[9];
        points[0] = new Point(1, 0);
        points[1] = new Point(1, 1);
        points[2] = new Point(1, 2);
        points[3] = new Point(1, 3);
        points[4] = new Point(1, 4);
        points[5] = new Point(2, 1);
        points[6] = new Point(3, 1);
        points[7] = new Point(0, 1);
        points[8] = new Point(-1, 1);
        FastCollinearPoints f = new FastCollinearPoints(points);
        for (LineSegment l: f.segments())
            System.out.println(l);
    }
}
