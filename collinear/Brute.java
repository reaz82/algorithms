/**
 * Compilation : javac -cp stdlib.jar:algs4.jar:. Brute.java 
 * Execution   : java -cp stdlib.jar:algs4.jar:. Brute <filename>
 * Dependencies: Point.java
 * 
 * @author Reaz H.
 */
import java.util.Arrays;
public class Brute {

  private static void print(Point w, Point x, Point y, Point z) {
    StdOut.print(w);
    StdOut.print(" -> "); 
    StdOut.print(x);
    StdOut.print(" -> "); 
    StdOut.print(y);
    StdOut.print(" -> "); 
    StdOut.println(z); 
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
    Arrays.sort(points, 0, u); 
    double slopeA, slopeB, slopeC;
    for (int i = 0; i < u; i++) {
      for (int j = i+1; j < u; j++) {
        slopeA = points[i].slopeTo(points[j]); 
        for (int k = j+1; k < u; k++) {
          slopeB = points[i].slopeTo(points[k]);
          for (int l = k+1; l < u; l++) {
            slopeC = points[i].slopeTo(points[l]);
            if ((slopeA == slopeB) && (slopeB == slopeC)) {
              print(points[i], points[j], points[k], points[l]); 
              points[i].drawTo(points[l]);
            }
          }
        }
      }
    } 
  }
}