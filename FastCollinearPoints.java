
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

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
    private int segmentSize;
    private Point[] collinear;
    private int collinearSize;
    private Point[] pts;
    
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
        pts = new Point[points.length];
        for (int i = 0; i < points.length; i++) pts[i] = points[i];
        
        segments = new LineSegment[1];
        segmentSize = 0;
        for (int i = 0; i < pts.length; i++) {
            System.out.println(pts[i]);
            Arrays.sort(pts, points[i].slopeOrder());
            for (Point p: pts) System.out.print(p);
            System.out.println();
            for (int j = 1; j < pts.length; j++) {
                collinear = new Point[4];
                collinearSize = 0;
                System.out.println("Anchor of " + pts[0]);
                double slopeA = pts[0].slopeTo(pts[j]);
                enqueue(pts[0]);
                enqueue(pts[j]);
                int c = 0;
                System.out.print("Examining " + pts[j]);
                System.out.println(" slope of " + slopeA);
                while (++j < pts.length && 
                        slopeA == pts[0].slopeTo(pts[j])) {
                    c++;
                    System.out.print("Slope matched slope to " + pts[j]);
                    System.out.println(" slope of " + pts[0].slopeTo(pts[j]));
                    enqueue(pts[j]);
                }
                j--;
                System.out.println(c);
                if (c >= 2) {
                    Point[] toAdd = new Point[collinearSize];
                    for (int k = 0; k < collinearSize; k++) toAdd[k] = collinear[k];
                    Arrays.sort(toAdd);
                    if (!exists(i, j)) {
                        System.out.println("Adding " + toAdd[0] + " and "
                                + toAdd[collinearSize - 1]);
                        enqueue(new LineSegment(toAdd[0],
                                toAdd[collinearSize - 1]));
                    }
                    else System.out.println("Duplicate!! Not adding");
                }
            }
        }
    }
    
    /**
     * Scans pts to determine whether the Line Segment is already present
     */
    private boolean exists(int i, int j) {
        for (int k = 0; k < pts.length - i; k++)
            if (pts[0].compareTo(pts[i + k]) == 0)
                return true;
        return false;
    }
    /**
     * "Add the item"
     * "Throw a java.lang.NullPointerException if the client attempts to add a
     * null item"
     * 
     * Quietly refuses to add duplicate LineSegments.
     * 
     * Also doubles the length of the array when it is full.
     */
    private void enqueue(LineSegment l)
    {
        if (l == null) throw new java.lang.NullPointerException();
        boolean duplicate = false;
        String el = l.toString();
        for (int i = 0; i < segmentSize; i++) {
            if (el.equals(segments[i].toString())) {
                duplicate = true;
                break;
            }
        }
        if (!duplicate) {
            if (segmentSize == segments.length)
                resize(2 * segments.length, segments);
            segments[segmentSize++] = l;
        }
    }
    
    /**
     * "Add the item"
     * "Throw a java.lang.NullPointerException if the client attempts to add a
     * null item"
     * 
     * Also doubles the length of the array when it is full.
     */
    private void enqueue(Point p)
    {
        if (p == null) throw new java.lang.NullPointerException();
        if (collinearSize == collinear.length)
            resize(2 * collinear.length, collinear);
        collinear[collinearSize++] = p;
    }
    
    /**
     * Resizes the array segments to [capacity].
     * 
     * This is a quadratic operation in the length of a,
     * and so should only be performed sparingly.
     * 
     * Amortizing this cost over the number of operations which
     * can be performed in the new array, however,
     * the ResizingArray is constant.
     */
    private void resize(int capacity, LineSegment[] l) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < segmentSize; i++) copy[i] = l[i];
        segments = copy;
    }
    
        /**
     * Resizes the array segments to [capacity].
     * 
     * This is a quadratic operation in the length of a,
     * and so should only be performed sparingly.
     * 
     * Amortizing this cost over the number of operations which
     * can be performed in the new array, however,
     * the ResizingArray is constant.
     */
    private void resize(int capacity, Point[] p) {
        Point[] copy = new Point[capacity];
        for (int i = 0; i < collinearSize; i++) copy[i] = p[i];
        collinear = copy;
    }
    
    /**
     * The number of line segments
     * @return 
     */
    public int numberOfSegments() {
        return segmentSize;
    }
    
    /**
     * The line segments
     * @return 
     */
    public LineSegment[] segments() {
        LineSegment[] shrunk = new LineSegment[segmentSize];
        for (int i = 0; i < segmentSize; i++) shrunk[i] = segments[i];
        return shrunk;
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("collinear/horizontal25.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
