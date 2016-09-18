
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
public class Point implements Comparable<Point> {
    /**
     * "Constructs the point (x, y)"
     * @param x
     * @param y 
     */
    public Point(int x, int y) {
        
    }
    
    /**
     * "Draws this point"
     */
    public void draw() {
        
    }
    
    /**
     * "Draws the line segment from this point to that point"
     * @param that 
     */
    public void drawTo(Point that) {
        
    }
    
    @Override
    /**
     * "String representation"
     * @return 
     */
    public String toString() {
        
    }
    
    @Override
    /**
     * "Compare two points by y-coordinates, breaking ties by x-coordinates"
     * @param that
     * @return 
     */
    public int compareTo(Point that) {
        
    }
    
    /**
     * "The slope between this point and that point"
     * @param that
     * @return 
     */
    public double slopeTo(Point that) {
        
    }
    
    /**
     * "Compare two points by slopes they make with this point"
     * @return 
     */
    public Comparator<Point> slopeOrder() {
        
    }
}