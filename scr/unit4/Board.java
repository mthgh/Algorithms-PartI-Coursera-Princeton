import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
//asume user input array is N*N (N>=1), and value is from 0 to N*N-1
public class Board {
    private int[][] blocks;
    private int N;
    
    public Board(int[][] blocks) {                              // construct a board from an N-by-N array of blocks
       this.blocks = blocks;
       this.N = blocks.length;
    }                                                         // (where blocks[i][j] = block in row i, column j)
    
    public int dimension() {                // board dimension N
        return N;
    }
    
    public int hamming() {                   // number of blocks out of place
       int ct = 0;
       for (int i = 0; i<N; i++) {
            for (int j = 0; j<N; j++) {
                if (blocks[i][j]!=0 && blocks[i][j] != i*N + (j+1)) ct++;
            }
        }
       return ct;
    }
    
    public int manhattan() {               // sum of Manhattan distances between blocks and goal
        int Msum = 0;
        for (int i = 0; i<N; i++) {
            for (int j = 0; j<N; j++) {
                if (blocks[i][j]!=0 && blocks[i][j] != i*N + (j+1)) {
                    int k = blocks[i][j];
                    int i1 = (int)Math.ceil(k*1.0/N)-1;
                    int j1 = k-i1*N-1;
                    Msum = Msum + Math.abs(i1-i) + Math.abs(j1-j);
                }
            }
        }
      return Msum;
    }
    
    public boolean isGoal() {               // is this board the goal board?
        if (hamming()==0) return true;
        else return false;
    }
    
    private int[][] copy() {
        int[][] cBlocks = new int[N][N];
        for (int i = 0; i<N; i++) {
            for (int j = 0; j<N; j++) {
                cBlocks[i][j] = blocks[i][j];
            }
        }
        return cBlocks;
    }
    
    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks      
        int[][] sBlocks = copy();
        outerloop:
        for (int i = 0; i<N; i++) {
            for (int j = 0; j+1<N; j++) {
                if (sBlocks[i][j]!=0 && sBlocks[i][j+1]!=0) {
                    sBlocks[i][j] = blocks[i][j+1];
                    sBlocks[i][j+1] = blocks[i][j];
                    break outerloop;
                }
            }
        }   
        Board sBoard = new Board(sBlocks);
        return sBoard;
    }
    
    public boolean equals(Object y) {       // does this board equal y?
       Board that = (Board) y;
       if (this==that) return true;
       if (that==null) return false;
       if (this.dimension() != that.dimension()) return false;
       if (this.getClass() != that.getClass()) return false;
       for (int i = 0; i<this.dimension(); i++) {
            for (int j = 0; j<this.dimension(); j++) {
                if (this.blocks[i][j]!=that.blocks[i][j]) return false;
            }
        }
       return true;
    }
    
    private int[][] swap(int i1, int j1, int i2, int j2) {
        int[][] cBlocks= copy();
        cBlocks[i1][j1] = blocks[i2][j2];
        cBlocks[i2][j2] = blocks[i1][j1];
        return cBlocks;
    }
    
    public Iterable<Board> neighbors() {    // all neighboring boards
        Stack<Board> s = new Stack<Board>();
        outerloop:
        for (int i = 0; i<N; i++) {
            for (int j = 0; j<N; j++) {
                if (blocks[i][j]==0) {
                    for (int t = -1; t<3; t=t+2) {
                        if (i+t<N && i+t>=0) s.push(new Board(swap(i+t, j, i, j)));
                    }
                    for (int t = -1; t<3; t=t+2)  {
                        if (j+t<N && j+t>=0) s.push(new Board(swap(i, j+t, i, j)));
                    }  
                    break outerloop;
                    }  
                }
            }
       return s;
    }
    
    public String toString() {              // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
        s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        
        int[][] blocks = new int[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        
        int[][] cblocks = new int[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                cblocks[i][j] = blocks[i][j];
            }
        }
        cblocks[N-1][N-1] =  blocks[N-1][N-2];
        cblocks[N-1][N-2] = blocks[N-1][N-1];
        
        Board b = new Board(blocks);
        Board cb = new Board(cblocks);
        
        StdOut.println(b.toString());
        
        for(Board obj : b.neighbors()) {
            StdOut.println(obj.toString());
        }
        StdOut.println(b.equals(cb));
        StdOut.println(b.twin().toString());
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println(b.isGoal());
    }
}