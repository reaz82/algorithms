/**
 * Created by rhasan on 2/21/15.
 */
public class Board {
    private final int[][] blocks;
    private final int N;

    public Board(int[][] blocks) {                  // Construct a NxN board where board[i][j] = row i, column j
        this.N = blocks.length;
        this.blocks = new int[N][N];
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    public int dimension() {                        // Dimension of the board
        return N;
    }

    public int hamming() {                          // Return the number of blocks out of place
        int hammingSum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if ((i == (N - 1)) && (j == (N - 1)) && (blocks[i][j] != 0)) {
                    hammingSum++;
                }
                else if (blocks[i][j] == 0) {
                    continue;
                }
                else if ((blocks[i][j] != ((i * N) + j + 1))) {
                    hammingSum++;

                }
            }
        }
        return hammingSum;
    }
    public int manhattan() {                        // Return the sum of manhattan distance between blocks and goal
        return 0;
    }
    public boolean isGoal() {                       // is this board the goal board?
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i == (N-1)) && (j == (N-1)))   // Last position of puzzle should always be a zero
                    return (blocks[i][j]== 0);      // 0 means true; any non-zero number means not Goal.

                if (blocks[i][j] != ((i * N) + j + 1)) // This segment is executed unless we are at the last block.
                    return false;                      // See code segment above.
            }
        }
        return true;
    }
    /*public Board twin() {                           // A board obtained by swapping two adjacent  blocks in the same row

    }*/
    public boolean equals(Object y) {               // Does this board equal y?
        return false;
    }
    //public Iterable<Board> neighbors() {}           // All neighboring boards
    public String toString() {                      // String representation of the board
        String boardString = new String();
        boardString = "" + this.N + "\n";
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                boardString =  boardString + this.blocks[i][j] + " ";
            }
            boardString = boardString + "\n";
        }
        return boardString;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.println(initial);
        int hammingSum = initial.hamming();
        StdOut.println(hammingSum);
        if (initial.isGoal())
            StdOut.println("Puzzle is solved!");
        else
            StdOut.println("Puzzle needs work!");
    }
}
