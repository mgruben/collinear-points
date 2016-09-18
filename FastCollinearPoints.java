
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
    private int segmentCount;
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
        segmentCount = 0;
        segments = new LineSegment[points.length / 4];
        segmentHead = 0;
        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points, points[i].slopeOrder());
            for (int j = i + 1; j < points.length; j++) {
                double slopeA = points[i].slopeTo(points[j]);
                int c = 0;
                while (j < points.length - 1 && 
                        slopeA == points[i].slopeTo(points[++j])) c++;
                System.out.println(c);
                if (c >= 2) {
                    segments[segmentHead] = new LineSegment(points[i], points[j]);
                    segmentCount++;
                    segmentHead++;
                }
            }
        }
    }
    
    /**
     * The number of line segments
     * @return 
     */
    public int numberOfSegments() {
        return segmentCount;
    }
    
    /**
     * The line segments
     * @return 
     */
    public LineSegment[] segments() {
        return segments;
    }
    
    public static void main(String[] args) {
        Point[] points = new Point[5];
        points[0] = new Point(1, 0);
        points[1] = new Point(1, 1);
        points[2] = new Point(1, 2);
        points[3] = new Point(1, 3);
        points[4] = new Point(1, 4);
        FastCollinearPoints f = new FastCollinearPoints(points);
        System.out.println(f.segments[0]);
    }
}
