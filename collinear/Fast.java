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
  private static void print(LinkedList<Point> segment) {
    Point first = segment.removeFirst();
    Point next = first;
    StdOut.print(first);
    while (segment.size() > 0) {
      StdOut.print(" -> ");
      next = segment.removeLast();
      StdOut.print(next);
    }
    StdOut.println();
    first.drawTo(next);
  }
  
  public static void main(String[] args) {
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    // Open file
    In input = new In(args[0]);
    // Read in number of points
    int numberOfPoints = input.readInt();
    int[] ints = new int[2];
    // Read in points
    ints = input.readAllInts();
    Point[] points = new Point[numberOfPoints];
    Point[] temp = new Point[numberOfPoints];
    for (int i = 0; i < numberOfPoints; i++) {
      points[i] = new Point(ints[i*2], ints[i*2 + 1]);
      temp[i] = new Point(ints[i*2], ints[i*2 + 1]);
      points[i].draw();
    }

    double slope = 0.0;
    double prevSlope = 0.0;
    Arrays.sort(points, 0, numberOfPoints);
    LinkedList<Point> segment, oldsegment;
    for (int i = 0; i < numberOfPoints; i++) {

      segment = new LinkedList<Point>();
      //StdOut.print("Base Point: ");
      //StdOut.println(points[i]);
      Arrays.sort(temp, 0, numberOfPoints, points[i].SLOPE_ORDER);
      segment.addLast(points[i]);

      for (int j = 0; j < numberOfPoints; j++) { // ignore the root point p.
        slope = points[i].slopeTo(temp[j]);
        //StdOut.print("Compare point: ");
        //StdOut.print(temp[j]);
        //StdOut.print(" : ");
        //StdOut.println(slope);
        if (slope == Double.NEGATIVE_INFINITY) continue; // ignore degenerate point
        if (slope == prevSlope) { // keep track of this point. Never true for first non-first degenerate point.
          if (temp[0].compareTo(temp[j]) < 0) {
            segment.addLast(temp[j]);
          } else break;
        } else { // if the slope is not equal to previous then we no longer have a connecting points
                 // if the segment has more then 3 points then we have a line segment or else we discard.
          if (segment.size() > 3) {
            print(segment);
          }
          continue;
        }
        prevSlope = slope;
      }
    }
  }
}