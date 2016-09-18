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
public class BruteCollinearPoints {
    private int segments;

    /**
     * Finds all line segments containing 4 points
     * @param points 
     */
    public BruteCollinearPoints(Point[] points) {
        segments = 0;
        for (int i = 0; i < points.length - 3; i++) {
            double slopeA = points[i].slopeTo(points[i + 1]);
            double slopeB = points[i].slopeTo(points[i + 2]);
            double slopeC = points[i].slopeTo(points[i + 3]);
            if (slopeA == slopeB && slopeB == slopeC) segments++;
        }
    }
    
    /**
     * The number of line segments
     * @return 
     */
    public int numberOfSegments() {
        
    }
    
    /**
     * The line segments
     * @return 
     */
    public LineSegment[] segments() {
        
    }
}
