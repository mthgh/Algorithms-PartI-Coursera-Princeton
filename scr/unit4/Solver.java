import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    
    private class Node {
        public Board curr;
        public int steps;
        public Node pre;
        public int priority;
        
        public Node(Board curr, int steps, Node pre) { 
            this.curr = curr;
            this.steps = steps;
            this.pre = pre;
            this.priority = steps + curr.hamming();
        }
    }
    
    private class NodeComparator implements Comparator<Node> {
        public int compare(Node node1, Node node2) {
            return node1.priority-node2.priority;
        } 
    }
    
    private final MinPQ<Node> pq1 = new MinPQ<Node>(new NodeComparator());
    private final MinPQ<Node> pq2 = new MinPQ<Node>(new NodeComparator()); 
    private Node last1;
    private final Boolean solve;
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial==null) throw new NullPointerException();
        Node first1 = new Node(initial, 0, null);      
        Node first2 = new Node(initial.twin(), 0, null);      
        pq1.insert(first1);
        pq2.insert(first2);
        
        while ( true ) {
            last1 = pq1.delMin();
            Node last2 = pq2.delMin();
            if ( last1.curr.isGoal() ) {solve=true; break;}
            if ( last2.curr.isGoal() ) {solve=false; break;}
            
            for(Board b1 : last1.curr.neighbors()) {
                if ( last1.pre==null || (!b1.equals(last1.pre.curr)) ) {
                    Node next1 = new Node(b1, last1.steps+1, last1);
                    pq1.insert(next1);
                }
            }
            
            for (Board b2 : last2.curr.neighbors()) {
                if ( last2.pre==null || (!b2.equals(last2.pre.curr))) {
                    Node next2 = new Node(b2, last2.steps + 1, last2);
                    pq2.insert(next2);
                }
            }
        }
    }
    
    public boolean isSolvable() {           // is the initial board solvable?
        return solve;
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (!solve) return -1;
        return last1.steps;        
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!solve) return null;
        Stack<Board> s = new Stack<Board>();
        Node now = last1;
        while (now != null) {
            s.push(now.curr);
            now = now.pre;
        }
        return s;
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
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } 
}