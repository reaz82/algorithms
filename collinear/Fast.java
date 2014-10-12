/**
 * Compilation : javac -cp stdlib.jar:algs4.jar:. Brute.java 
 * Execution   : java -cp stdlib.jar:algs4.jar:. Brute <filename>
 * Dependencies: Point.java
 * 
 * @author Reaz H.
 */
import java.util.Arrays;
import java.util.LinkedList;

public class Fast {
  private static void print(LinkedList<Point> segment ) {
    Point first = segment.removeFirst();
    Point last = segment.removeLast();
    first.drawTo(last);
    /*StdOut.print(w);
    StdOut.print(" -> "); 
    StdOut.print(x);
    StdOut.print(" -> "); 
    StdOut.print(y);
    StdOut.print(" -> "); 
    StdOut.println(z); */
  }
  
  public static void main(String[] args) {
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    // Open file
    In input = new In(args[0]);
    // Read in number of points
    Integer u = input.readInt();
    int[] ints = new int[2];
    // Read in points
    ints = input.readAllInts();
    Point[] points = new Point[u];
    for (int i = 0; i < u; i++) {
      points[i] = new Point(ints[i*2],ints[i*2 + 1]);
      points[i].draw();
    }
    double slope, prevSlope = 0.0;
    //LinkedList<Point>[] segments;
    LinkedList<Point> segment, oldsegment;
    for (int i = 0; i < u; i++) {
      segment = new LinkedList<Point>();
      Arrays.sort(points, points[i].SLOPE_ORDER);
      segment.addLast(points[i]);
      for (int j = 1; j < u; j++) { // ignore the root point p.
        slope = points[i].slopeTo(points[j]); 
        if (slope == Double.NEGATIVE_INFINITY) continue; // ignore degenerate point
        if (slope == prevSlope) { // keep track of this point. Never true for first non-first degenerate point.
          segment.addLast(points[j]);
        } else { // evaluate the previous segment and start a new segment.
          //if (segment.size() > 3) print(segment);
          segment = new LinkedList<Point>();
          segment.addLast(points[j]); 
        }
        prevSlope = slope;
      }
    }
  }
}