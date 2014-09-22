/**
 * Compilation : java -cp stdlib.jar:algs4.jar:. Subset.java
 * Execution   : echo aa bb cc dd ee ff gg hh ee ff ii jj | java Subset 5
 * Dependencies: RandomizedQueue.java
 * 
 * @author Reaz H.
 */
public class Subset {
    public static void main (String[] args) {
        int k = 1;
        RandomizedQueue<String> SetOfStrings = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            SetOfStrings.enqueue(StdIn.readString());
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(SetOfStrings.dequeue());
        }
    }
}