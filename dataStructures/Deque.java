/** 
 * Compilation  : javac -cp stdlib.jar:algs4.jar:. Deque.java
 * Execution    : java -cp stdlib.jar:algs4.jar:. Deque.java
 * Dependencies : Iterator
 * 
 * Deque.
 */
import java.util.Iterator;

/**
 * Deque Class:
 * A <em>double-ended queue</em> or <em>deque</em> (pronounced "deck") is a 
 * generalization of a stack and a queue that supports inserting and removing 
 * items from either the front or the back of the data structure. 
 * 
 * @author Reaz H. 
 */
 
public class Deque<Item> implements Iterable<Item> {
    /**
     * The Deque needs to keep track of its size and the nodes at its front
     * and back. 
     */
    private Node<Item> front; // The first node
    private Node<Item> back;  // The last node
    private int size;         // The total number of nodes
    
    /**
     * The deque is made up of nodes. Each node in the deque has two 
     * references that make the deque a doubly linked list. The client
     * can traverse forwards and backwards. 
     */
    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
    }
    
    /** 
     * Initiliazes an empty Deque object. The object has no nodes. Front node 
     * reference should be the same as back node reference. All should point to
     * null. Size is 0.
     */
    public Deque() {
    }
    
    /** 
     * Returns true if the size of the deque is zero
     * @return <tt>true</tt> if the size of the deque is zero, <tt>false</tt> 
     * otherwise. 
     */
    public boolean isEmpty() { 
        return (this.size == 0);
    }
    
    /** 
     * Returns the number of nodes in the deque object. The size is initialized
     * in the constructor and update for every add or remove operation.
     * @return the size of the deque 
     */
    public int size() {
        return this.size;
    }
    
    /**
     * Adds an item to the front of the deque object. Increments size;
     * @param reference of type <Item>
     */
    public void addFirst(Item item) {
        if (item == null) throw new java.util.NoSuchElementException("Unable to add null element"); 
        // Copy reference to old front node
        Node<Item> oldFront = this.front;
        // Create new node based on item
        front = new Node<Item>();
        front.item = item;
        // First element is a little special. Front and back should point to the same
        // node. The following prevents traversing non-existent nodes.
        if (this.size > 0) {
            oldFront.previous = front;
            front.next = oldFront;
        } else {
            back = front;
        }
        this.size++;
    }
    /**
     * Adds an item to the back of the deque object. Increments size;
     * @param Object of type <Item>
     */
    public void addLast(Item item) {
        if (item == null) throw new java.util.NoSuchElementException("Unable to add null element");
        // Copy reference to old back node
        Node<Item> oldBack = this.back;
        // Create new node based on item
        back = new Node<Item>();
        back.item = item;
        // Update previous nodes and make back node point to previous. Special 
        // case is the first node.
        if (this.size > 0) {
            oldBack.next = back;
            back.previous = oldBack;
        } else {
            front = back;
        }
        this.size++;
    }
    public Item removeFirst() {
        if (this.isEmpty()) throw new UnsupportedOperationException("Can't remove from an empty deque"); 
        // Copy a reference to the first node.
        Node<Item> first = this.front;
        // Perform the node removal
        // 1. Make the front's next node.previous point to null, if available
        // 2. Make the front node point to the front.next node
        if (front.next != null) {
            this.front.next.previous = null;
        }
        front = front.next;
        // Decrement the size property
        this.size--;
        // Return the item to the client
        return first.item;
    }
    public Item removeLast() {
        if (this.isEmpty()) throw new UnsupportedOperationException("Can't remove from an empty deque");
        // Copy a reference to the last node.
        Node<Item> last = this.back;
        // Perform the node removal
        // 1. Make the second to last node point to null
        // 2. Make the back node reference point to the second to last node
        if (back.previous != null) {
            this.back.previous.next = null;
        }
        back = back.previous;
        // Decrement the size property
        this.size--;
        // Return the item to the client
        return last.item;
    }
    public Iterator<Item> iterator() {
        
        return new itemIterator();
    }
    /** 
     * The Deque implements Iterable class which means it needs to be 
     * able to return an iterator.
     */
    private class itemIterator implements Iterator<Item> {
        private Node<Item> iterator = front;
        /**
        * This method is a required method of the Iterator interface. It is 
        * used to determine whether the end of the iteration has been reached
        * @return <tt>false</tt> if front points to null, <tt>true</tt> otherwise.
        */  
        public boolean hasNext() {  return (iterator != null); }

        /**
        * This method is a required method of the Iterator interface. It is 
        * used to return the front item of the Deque. 
        * @return The item in the front node of the deque.
        */  

        public Item next() { 
            Item item;
            item = iterator.item;
            if (hasNext()) {
                iterator = iterator.next;
            } 
            return item; 
        } 
        public void remove() {
            throw new UnsupportedOperationException("Can't perform remove operation on iterator");
        }
    }

    public static void main(String[] args) {
        Deque<String> DequeOfStrings;
        DequeOfStrings = new Deque<String>();
        
        StdOut.println("Testing Stack behavior of the Deque");
        StdOut.println("Pushing Data into the Deque from the front");
        DequeOfStrings.addFirst("?");
        DequeOfStrings.addFirst("you ");
        DequeOfStrings.addFirst("are ");
        DequeOfStrings.addFirst("How ");
        StdOut.println("Popping Data out of the Deque from the front");
        StdOut.print(DequeOfStrings.removeFirst());
        StdOut.print(DequeOfStrings.removeFirst());
        StdOut.print(DequeOfStrings.removeFirst());
        StdOut.println(DequeOfStrings.removeFirst());
        
        StdOut.println("Pushing Data into the Deque from the back");
        DequeOfStrings.addLast("?");
        DequeOfStrings.addLast("you ");
        DequeOfStrings.addLast("are ");
        DequeOfStrings.addLast("How ");
        StdOut.println("Popping Data out of the Deque from the back");
        StdOut.print(DequeOfStrings.removeLast());
        StdOut.print(DequeOfStrings.removeLast());
        StdOut.print(DequeOfStrings.removeLast());
        StdOut.println(DequeOfStrings.removeLast());
        
        StdOut.println("Testing Queue behavior of the Deque");
        StdOut.println("Inserting one item into queue and retrieving it immediately");
        DequeOfStrings.addFirst("Item 1");
        StdOut.println(DequeOfStrings.removeLast());
        DequeOfStrings.addLast("Item 2");
        StdOut.println(DequeOfStrings.removeFirst());
        
        StdOut.println("Inserting 4 items into the front and retrieving it from the back");
        DequeOfStrings.addFirst("Item 1");
        DequeOfStrings.addFirst("Item 2");
        DequeOfStrings.addFirst("Item 3");
        DequeOfStrings.addFirst("Item 4");
        StdOut.println(DequeOfStrings.removeLast());
        StdOut.println(DequeOfStrings.removeLast());
        StdOut.println(DequeOfStrings.removeLast());
        StdOut.println(DequeOfStrings.removeLast());
        
        StdOut.println("Inserting 4 items into the back and retrieving it from the front");
        DequeOfStrings.addLast("Item 1");
        DequeOfStrings.addLast("Item 2");
        DequeOfStrings.addLast("Item 3");
        DequeOfStrings.addLast("Item 4");
        StdOut.println(DequeOfStrings.removeFirst());
        StdOut.println(DequeOfStrings.removeFirst());
        StdOut.println(DequeOfStrings.removeFirst());
        StdOut.println(DequeOfStrings.removeFirst());
        
        StdOut.println("Inserting 4 items into the back and iterating from the front");
        DequeOfStrings.addLast("Item 1");
        DequeOfStrings.addLast("Item 2");
        DequeOfStrings.addLast("Item 3");
        DequeOfStrings.addLast("Item 4");

        for (String s: DequeOfStrings) StdOut.println(s);
        
    
    }
}
