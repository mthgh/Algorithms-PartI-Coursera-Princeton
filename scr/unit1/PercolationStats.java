import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private final double[] stats;
    private Double Mean = null;
    private Double std = null;
    private final int T;
    
    public PercolationStats(int N, int T) {  //perform T independent experiments on an N-by-N grid
        if (N<=0 || T<=0) throw new IllegalArgumentException();
        this.T = T;
        stats = new double[T];
        for (int n=0;n<T;n++) {
            Percolation p = new Percolation(N);
            
            int[] nums = new int[N*N];
            for (int t=0;t<N*N;t++) { nums[t]=t;}
            StdRandom.shuffle(nums);
            
            int count=0; 
            while (! p.percolates()) {
            int index = nums[count];
            int i = index/N + 1;
            int j = index-(i-1)*N + 1;
            p.open(i, j);
            count++;
            }
            stats[n]=count*1.0/(N*N);
        }                       
    }
    
    public double mean() {     // sample mean of percolation threshold
        if (Mean==null) Mean = StdStats.mean(stats);
        return Mean;
    }
    
    public double stddev() {     // sample standard deviation of percolation threshold
        if (std==null) std = StdStats.stddev(stats);
        return std;
    }
    
    public double confidenceLo() {  // low endpoint of 95% confidence interval
        if (Mean == null) Mean = mean();
        if (std == null) std = stddev();
        return Mean-(1.96*std/Math.sqrt(T));
    }
    public double confidenceHi() {    // high endpoint of 95% confidence interval
        if (Mean == null) Mean = mean();
        if (std == null) std = stddev();
        return Mean+(1.96*std/Math.sqrt(T));        
    }
    
    public static void main(String[] args) {   // test client
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        StdOut.print("mean = ");
        StdOut.print(ps.mean());
        StdOut.print("\nstddev = ");  
        StdOut.print(ps.stddev());
        StdOut.print("\n95% confidence interval = ");
        StdOut.print(ps.confidenceLo());
        StdOut.print(", ");
        StdOut.print(ps.confidenceHi());
        StdOut.print("\n");
    }  
}