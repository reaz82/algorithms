/**
 * Compilation : javac -cp stdlib.jar:algs4.jar:. Brute.java 
 * Execution   : java -cp stdlib.jar:algs4.jar:. Brute <filename>
 * Dependencies: Point.java
 * 
 * @author Reaz H.
 */
import java.lang.Package;
import java.util.Arrays;
import java.util.LinkedList;

public class Fast {
  private static void print(Point base, LinkedList<Point> segment) {
    StdOut.print(base);
    Point next = base;
    while (segment.size() > 0) {
      StdOut.print(" -> ");
      next = segment.removeLast();
      StdOut.print(next);
    }
    StdOut.println();
    base.drawTo(next);
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

    double slope = Double.NEGATIVE_INFINITY;
    double prevSlope = Double.NEGATIVE_INFINITY;

    Arrays.sort(points, 0, numberOfPoints); // Sort all points in natural order; Temp array does not need
                                            // to be sorted as it will be continually sorted in the inner loop

    LinkedList<Point> segment;              // A linked list can be used to hold the line segment.

    for (int i = 0; i < numberOfPoints; i++) {

      segment = new LinkedList<Point>();
      /*StdOut.print("Base Point: ");
      StdOut.println(points[i]);*/

      Arrays.sort(temp, 0, numberOfPoints, points[i].SLOPE_ORDER); // Sort all the points in slope order

      boolean discard = false;
      int count = 0;

      for (int j = 0; j < numberOfPoints; j++) {
        slope = points[i].slopeTo(temp[j]);
        /*StdOut.print("Current Point: ");
        StdOut.print(temp[j]);
        StdOut.print(" Slope: ");
        StdOut.print(slope);*/
        if (slope == Double.NEGATIVE_INFINITY) { // temp[0] is always points[i]. This should catch it.
          //StdOut.println(" ignored ");
          continue;
        }

        if (slope == prevSlope) { // If the slope to current point is the same as the previous point
                                  // the points are collinear.
          if (points[i].compareTo(temp[j]) > 0) { // However, if the point is smaller that the base point
              discard = true;                     // we should discard this point and future points with
              //StdOut.println(" ignored ");
              continue;                           // same slope.
          } else {
            if (discard != true) {                // Otherwise if we aren't already discarding this line
              segment.addLast(temp[j]);           // segment, we will add it to our segment
              //StdOut.println(" added ");
            } else {
              //StdOut.println(" ignored ");
              continue;
            }
          }

        } else {                                  // We have encountered a point with a new slope
          discard = false;

          if (segment.size() > 2) {                        // Does the previous line segment have at least 4 collinear points?
            //StdOut.println(" Segment found!");
            print (points[i], segment);
          }

          if (points[i].compareTo(temp[j]) > 0) { // If the current point is smaller than the base point
            discard = true;                       // we want to ignore this point and future points with the same slope
            //StdOut.println(" ignored ");
            continue;
          }

          if (discard != true) {
            segment = new LinkedList<Point>();     // We need to start a new line segment
            segment.addLast(temp[j]);              // with the current point as the initial point
            //StdOut.println(" added ");
          }
        }                        // if (current point's slope to base point is the same as the previous point
        prevSlope = slope;
      }
    }
  }
}