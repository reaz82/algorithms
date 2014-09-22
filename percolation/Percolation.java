/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:  java Percolation.java 
 *  Dependencies: WeightedQuickUnionUF.java 
 *
 *  Percolation.
 *
 ****************************************************************************/

/**
 * The <tt> Percolation </tt> class represents a Percolation data structure. 
 * <p>
 * This implementation uses the WeightedQuickUnionUF data to model the 
 * percolation effects. Initializing Percolation takes 
 * <p>
 * @author Reaz Hasan
 *
 */

public class Percolation {
    private WeightedQuickUnionUF gridConnections; // Stores connectivity information
    private boolean[][] grid;  // Stores open/blocked state for each node
    private int N;             // Stores dimension of the grid for boundary checks.
    private boolean isPercolating;
    /**
     * Initializes a Percolation object. It is a NxN grid which tracks 
     * whether the site is open or closed. 
     * @throws java.lang.IllegalArgumentException if N < 0
     * @param N the dimensions of the NxN grid
     */
    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException("Grid size has to be 1x1 or more!");
        }      
        // Extra element reserved for TOP node.
        gridConnections = new WeightedQuickUnionUF((N*N) + 2);
        // Construct the grid. 0: Blocked, 1: Open
        grid = new boolean[N][N];
        this.N = N;              
    };
    
    private boolean isConnectedToTop(int i, int j) {
        int root = gridConnections.find(toLinear(i, j));
        // If root is in [0,N-1] root is at the top of grid.
        if ((root/N) == 0) return true;
        else return false;
    }
    
    private boolean isConnectedToBottom(int i, int j) {
        int root = gridConnections.find(toLinear(i, j));
        // If root is in [(N*N - N),(N*N - 1)] root is at the bottom.
        if ((root/N) == (N-1)) return true;
        else return false;
    }
    
    /**
     * This method checks whether the row and column indices supplied to 
     * the API are within bounds. 
     * @throws java.lang.IndexOutOfBoundsException if {i||j} < 1 or {i||j} > N
     * @param i row indexed at 1
     * @param j column index at 1
     */
    private void checkBounds(int i, int j) {
         // Check for valid indices
        if ((i < 1) || (i > N)) {
            throw new IndexOutOfBoundsException("Row (i) is out of bounds"); 
        } 
        if ((j < 1) || (j > N)) {
            throw new IndexOutOfBoundsException("Column (j) is out of bounds");
        }
    }
    
    /**
     * This method converts row-column co-ordinates to the 0-indexed
     * linear array used by WeightedQuickUnionUF data structure.
     * @param i row indexed at 1
     * @param j column indexed at 1
     * @return index into WeightedQuickUnionUF.id indexed at 0
     */
    private int toLinear(int i, int j) {
        return (((i-1)*N) + (j -1));
    }
    
    /**
     * This method opens the cell indexed by i and j. Also it calls union on its
     * neighboring cells if they are open. 
     * @throws java.lang.IndexOutOfBoundsException if {i,j} < 1 or {i,j} > N
     * @param i row indexed at 1
     * @param j column index at 1
     */
    public void open(int i, int j) {
        int row, column;
        boolean connectedToTop = false;
        boolean connectedToBottom = false;
        checkBounds(i, j);
        // Index at 0 
        row = i-1;
        column = j-1;
        
        // If already open return and save some time.
        if (grid[row][column]) return;
        
        // Set the cell to open. 
        grid[row][column] = true;
        
        // Connect top sites to TOP virtual node
        //if (i == 1) gridConnections.union(toLinear(i, j), (N*N));
        if (i == 1) connectedToTop = true;
        if (i == N) connectedToBottom = true;
        // Connect adjacent sites if they are open. 
        if (((i-1) > 0) && isOpen(i-1, j)) {
            if (isConnectedToTop(i-1, j)) connectedToTop = true;
            gridConnections.union(toLinear(i, j), toLinear(i-1, j));
        }
        
        if (((j-1) > 0) && isOpen(i, j-1)) {
            if (isConnectedToTop(i, j-1)) connectedToTop = true;
            if (isConnectedToBottom(i, j-1)) connectedToBottom = true;
            gridConnections.union(toLinear(i, j), toLinear(i, j-1));
        }
        
        if (((j+1) <= N) && isOpen(i, j+1)) {
            if (isConnectedToTop(i, j+1)) connectedToTop = true;
            if (isConnectedToBottom(i, j+1)) connectedToBottom = true;
            gridConnections.union(toLinear(i, j), toLinear(i, j+1));
        }
        
        if (((i+1) <= N) && isOpen(i+1, j)) {
            if (isConnectedToBottom(i+1, j)) connectedToBottom = true;
            gridConnections.union(toLinear(i, j), toLinear(i+1, j));
        }
        
        //if (i == N) gridConnections.union(toLinear(i, j), ((N*N) + 1));
        if (connectedToBottom && connectedToTop) this.isPercolating = true;
        
    };
    
    /**
     * Returns the status of the grid cell. 
     * @param i row indexed at 1
     * @param j column index at 1
     * @return <tt>true</tt> if the site is open or <tt>false</tt> if blocked.
     */
    public boolean isOpen(int i, int j) {
        int row, column;
        checkBounds(i, j);
        // Index at 0 
        row = i - 1;
        column = j - 1;
        
        // Return status. 0: blocked, 1: open.
        return grid[row][column];
    };
    
    /**
     * Returns whether the cell is connected from the top of the grid
     * @param i row index
     * @param j column address
     * @return <tt>true</tt> if the adjacent cells are open and <tt>false</tt>
     * otherwise.
     */
    public boolean isFull(int i, int j) {
      
        checkBounds(i, j);
              
        // Base case. Top row cells are full if they are open.
        if ((i == 1) && isOpen(i, j)) return true;
        
        // If current cell is open and is connected to TOP node it isFull.
        //if (isOpen(i, j) && gridConnections.connected(toLinear(i, j), N*N)) return true;
        if (isOpen(i, j) && (isConnectedToTop(i, j))) return true;       
        // Fall through case. Cell is blocked.
        return false;
    };
    
    /**
     * @return <tt>true</tt> if the grid has a path that connects the top with
     * the bottom, <tt>false</tt> otherwise.
     */
    public boolean percolates() {
        //return gridConnections.connected((N*N), (N*N)+1);
        return this.isPercolating;
    };
    
    /**
     * test client, optional
     */
    public static void main(String[] args) {
    
    
    
    }
}