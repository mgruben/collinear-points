
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
    private Point[] pts; // mutates for each new "origin"
    
    /**
     * Finds all line segments containing 4 or more points
     * @param points 
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        
        /**
         * We have three points arrays: points, ptsUnsorted, and pts.
         * 
         * Checking for null points goes way faster if we've already sorted
         * the points array, but the API requires that we can't mutate the
         * incoming points.  Hence we make ptsNullCheck, and sort that.
         * 
         * Additionally, when we're iterating over each point in our main
         * for loop below, we need to have consistent indexing, or the
         * loop will miss some points and/or consider some points more than
         * once.  Hence we create pts, and re-sort that for each new point
         * under consideration, according to the next point in ptsNullCheck.
         */
        Point[] ptsNullCheck = points.clone();
        Arrays.sort(ptsNullCheck);
        for (int i = 0; i < ptsNullCheck.length - 1; i++) {
            if (ptsNullCheck[i] == null) throw new java.lang.NullPointerException();
            if (ptsNullCheck[i].compareTo(ptsNullCheck[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException();
        }
        
        // Store the segments we've found in a ResizingArray
        segments = new LineSegment[1];
        segmentSize = 0;
        
        pts = ptsNullCheck.clone();
        
        for (int p = 0; p < pts.length; p++) {
            Arrays.sort(pts, ptsNullCheck[p].slopeOrder());
            /**
             * pts[0] is the current point, the slope to which was used to sort
             * all other points in the array.  Thus, we start j at 1 so we don't
             * compare the current point to itself, which wouldn't yield
             * meaningful information.
             */
            for (int j = 1; j < pts.length; j++) {
                /**
                 * Store collinear points in a ResizingArray.
                 * It will probably need to hold 4 Points.
                 */
                collinear = new Point[4];
                collinearSize = 0;
                
                /**
                 * Make our first comparison, and add these points to collinear
                 * (any two points are collinear)
                 */
                double slopeA = pts[0].slopeTo(pts[j]);
                enqueue(pts[0]);
                enqueue(pts[j]);
                int c = 1;
                
                while (++j < pts.length && 
                        slopeA == pts[0].slopeTo(pts[j])) {
                    c++;
                    enqueue(pts[j]);
                }
                j--;
                if (c >= 3) {
                    Point[] toAdd = new Point[collinearSize];
                    for (int k = 0; k < collinearSize; k++)
                        toAdd[k] = collinear[k];
                    Arrays.sort(toAdd);
                    if (!exists(c, j)) {
                        enqueue(new LineSegment(toAdd[0],
                                toAdd[collinearSize - 1]));
                    }
                }
            }
        }
    }
    
    /**
     * Scans pts to determine whether the Line Segment is already present
     */
    private boolean exists(int c, int j) {
        for (int k = 0; k < c; k++)
            if (pts[0].compareTo(pts[j - k]) > 0)
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
        if (segmentSize == segments.length)
            resize(2 * segments.length, segments);
        segments[segmentSize++] = l;
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
        In in = new In("collinear/input9.txt");
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
