import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> set = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString(); 
            set.enqueue(s);
        }
        for (int i=0; i<k; i++) {
            StdOut.print(set.dequeue());            
        }        
    }       
}