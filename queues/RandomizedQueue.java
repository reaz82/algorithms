/**
 * Compilation  : javac -cp stdlib.jar:algs4.jar:. RandomizedQueue.java
 * Execution    : java -cp stdlib.jar:algs4.jar:. RandomizedQueue
 * Dependencies : Iterable 
 */
import java.util.Iterator;
/**
 * A <em>randomized queue</em> is similar to a stack or queue, except that the item removed is
 * chosen uniformly at random from items in the data structure. This implementation uses an array
 * as the underlying data structure. 
 * @author Reaz H.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Item[] items;
    /**
     * Constructor creates an empty randomized queue.
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }
    /**
     * Tests whether the Queue is empty. 
     * @return <tt>true</tt> if the size member is 0, otherwise <tt>false</tt>
     */
    public boolean isEmpty() {
        return (this.N == 0); 
    }
    /** 
     * Returns the size of the array.
     * @return size of the array
     */
    public int size() {
        return this.N;
    }
    /**
     * Creates a new array of a larger size and copies the current array into the new array.
     * Essentially, to the client, the array is being resized underneath. 
     * @param new capacity of the array
     */
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }
  
    /**
     * This method adds the item to the back of the array. Addition does not have to be 
     * random.
     * @param an object of specified type
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Can't add null item to the queue");
        if (items.length == N) resize(2*items.length); 
        items[N++] = item;        
    }
    
    /**
     * This method removes an item from the queue at random and returns it to the client
     * @return an element is picked at random from the queue and returned.
     */
    public Item dequeue() {
        // Null items are not allowed!
        if (this.isEmpty()) throw new java.util.NoSuchElementException("Can't remove item from an empty queue");
        
        // Resize the array if it's too small
        if (items.length == N/4) resize(items.length/2);
        
        // Select an index in the array at random
        int randomIndex = StdRandom.uniform(0, N);
        
        // Perform a swap 
        Item deletedItem = items[randomIndex];
        items[randomIndex] = items[N-1];
        items[N-1] = deletedItem;
        // Return this item and decrement N
        return items[--N];
    }
    
    /**
     * This method samples an element at random and returns a copy of the item. The original
     * item remains in the array.
     * @return a sample of the item in the array.
     */
    public Item sample() {
        // Null items are not allowed!
        if (this.isEmpty()) throw new java.util.NoSuchElementException("Can't remove item from an empty queue");
        
        // Resize the array if it's grown small
        if (items.length == N/4) resize(items.length/2);
        
         // Select an index in the array at random
        int sampleIndex = StdRandom.uniform(0, N);
               
        return items[sampleIndex];
    }
    
    public Iterator<Item> iterator() {
        return new RandomIterator();
    
    } // return an independent iterator over items in random order
    
    private class RandomIterator implements Iterator<Item> {
        private int i;
        private Item[] iterator;
        
        public RandomIterator() {
            iterator = (Item[]) new Object[N];
            for (i = 0; i < N; i++) {
                iterator[i] = items[i];
            }
        }
        
        public boolean hasNext() {
            return (i > 0); 
        }
        public Item next() {
            if (this.hasNext()) {
                int iteratorIndex = StdRandom.uniform(0, i);
                Item iteratedItem = iterator[iteratorIndex];
                iterator[iteratorIndex] = iterator[i-1];
                iterator[--i] = null;
                return iteratedItem;
            } else throw new java.util.NoSuchElementException("No elements in queue");
        }
        public void remove() {
            throw new UnsupportedOperationException("Can't perform remove operation on iterator");
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomQofItems = new RandomizedQueue<Integer>();
        StdOut.println("Initializing a randomQ of 100 integers"); 
        for (int i = 0; i < 100; i++) {
            randomQofItems.enqueue(i);
        }
        for (int i = 0; i < 90; i++) {
            StdOut.println(randomQofItems.dequeue()); 
        }
        StdOut.print("Size of randomQ : "); 
        StdOut.println(randomQofItems.size());
        StdOut.println("Printing remaining elements:");
        for (int i: randomQofItems) {
            StdOut.println(i);
        }
    }
        
}