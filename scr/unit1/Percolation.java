import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final boolean[] opened;
    private final int N;
    
    public Percolation(int N) {                         // create N-by-N grid, with all sites blocked
        if (N<=0) throw new IllegalArgumentException(); 
        this.N = N;
        grid = new WeightedQuickUnionUF(N*N);
        opened = new boolean[N*N];
        for (int n=1;n<N;n++) {
            grid.union(0,n);
            grid.union(N*N-N, N*N-n);
        }
    }
    
    public void open(int i, int j) {        // open site (row i, column j) if it is not open already
        if (i<1 || i>N || j<1 || j>N) throw new IndexOutOfBoundsException();
        
        int index = (i-1)*N + j-1;
        if (opened[index]) return;
        opened[index] = true;
        
        int[] i_neigh = new int[] {index-1, index+1, index-N, index+N};       
        for (int t: i_neigh) {
            int row = t/N;
            int col = t%N;
            if (row>=0 && row<N && col>=0 && col<N && opened[t]) grid.union(index, t);
        }
    }
    
    public boolean isOpen(int i, int j) {            // is site(row i, column j) open?
        int index = (i-1)*N + j-1;
        return opened[index];
    }
    
    public boolean isFull(int i, int j) { // is site(row i, column j) full?
        if (i<1 || i>N || j<1 || j>N) throw new IndexOutOfBoundsException();      
        if (i==1) {
            if (opened[j-1]) return true;
            else return false;
        }
        int index = (i-1)*N + j-1;
        if (!opened[index]) return false;               
        for (int n=0;n<N;n++) {
            if (grid.connected(index, (i-2)*N+n) && opened[(i-2)*N+n]) {
                return isFull(i-1, n+1);
            }
        }
        return false;
    }
    
    public boolean percolates() { // does the system percolate?
        for (int n=1;n<N+1;n++) {
            if (isFull(N, n)) return true;
        }
        return false;
    }
    
    private void draw() {
        for (int m=0; m<N; m++) {
            for (int n=0; n<N; n++) {
                int index = m*N + n;
                if (!opened[index]) StdDraw.setPenColor(StdDraw.BLACK);
                else if (!isFull(m+1, n+1)) StdDraw.setPenColor(StdDraw.YELLOW);
                else StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(.02);
                //StdDraw.filledSquare(n, m, 0.5);
                StdDraw.text(n, m, Integer.toString(grid.find(index)));
            }
        }
    }
    
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Percolation p = new Percolation(N);
        while (!StdIn.isEmpty()) { 
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            p.open(i, j);
            StdDraw.clear();
            StdDraw.setScale(-1, N+1);
            p.draw();          
            StdDraw.show();
            StdOut.print(p.percolates());
        } 
    }   
}