/*************************************************************************
 * Name: Reaz H.
 * Email: rhasan@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
  public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
  
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
      if (that.y == this.y) {
        if (that.x == this.x) return Double.NEGATIVE_INFINITY; 
        else return 0.0;
      }
      if (that.x == this.x) return Double.POSITIVE_INFINITY;
      return ((double) (that.y - this.y)/(double) (that.x - this.x));
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
      if (this.y == that.y) return (this.x - that.x);
      else return (this.y - that.y); 
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private final class SlopeOrder implements Comparator<Point> {
      public int compare(Point v, Point w) 
      { 
        if (Point.this.slopeTo(v) < Point.this.slopeTo(w)) return -1;
        else if (Point.this.slopeTo(v) > Point.this.slopeTo(w)) return 1;
        else return 0;
      }
    }
    
    // unit test
    public static void main(String[] args) {
        
        Point u = new Point(19000, 10000);
        Point v = new Point(18000, 10000);
        Point w = new Point(32000, 10000);
        Point x = new Point(21000, 10000);
        Point y = new Point(1234, 5678); 
        Point z = new Point(14000, 10000); 
       
        StdOut.println(u.slopeTo(v));
        StdOut.println(u.slopeTo(w));
        StdOut.println(u.slopeTo(x));
        StdOut.println(u.slopeTo(y));
        StdOut.println(u.slopeTo(z));
    }
}
