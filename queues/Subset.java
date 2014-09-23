/**
 * Compilation : java -cp stdlib.jar:algs4.jar:. Subset.java
 * Execution   : echo aa bb cc dd ee ff gg hh ee ff ii jj | java Subset 5
 * Dependencies: RandomizedQueue.java
 * 
 * @author Reaz H.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> setOfStrings = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            setOfStrings.enqueue(StdIn.readString());
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(setOfStrings.dequeue());
        }
    }
}