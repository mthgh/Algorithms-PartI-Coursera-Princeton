import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] queue;
   private int N;
    
   public RandomizedQueue() {                 // construct an empty randomized queue
      queue = (Item[]) new Object[1];
      N = 0;
   }
   public boolean isEmpty() { return N == 0; }              // is the queue empty?
   
   public int size() { return N; }                   // return the number of items on the queue

   private void resize(int capacity) {
       Item copy[] = (Item[]) new Object[capacity];
       for (int i=0; i<N; i++) copy[i]=queue[i];
       queue = copy;
   }
   
   public void enqueue(Item item) {           // add the item
      if (item == null) throw new NullPointerException();
      if (N==queue.length) resize(N*2);
      queue[N]=item;
      N=N+1;
   }
   public Item dequeue() {                 // remove and return a random item
      if (isEmpty()) throw new NoSuchElementException();
      int p = StdRandom.uniform(N);
      Item r = queue[p];
      queue[p] = queue[N-1];
      queue[N-1] = null;
      N = N-1;
      if (N>0 && N==queue.length/4) resize(queue.length/2);
      return r;
   }
   
   public Item sample() {                    // return (but do not remove) a random item
      if (isEmpty()) throw new NoSuchElementException();
      int p = StdRandom.uniform(N);
      return queue[p];
   }
   
   public Iterator<Item> iterator() { return new RandomArrayIterator();}       // return an independent iterator over items in random order   
   private class RandomArrayIterator implements Iterator<Item> {      
       private int i;
       private Item[] random;
       public RandomArrayIterator() { 
           i = 0;
           random = (Item[]) new Object[N];
           for (int t=0; t<N; t++) { random[t] = queue[t];}
           StdRandom.shuffle(random); 
       }
       public boolean hasNext() { return i<N; }
       public void remove() { throw new UnsupportedOperationException();}
       public Item next() { 
           if (!hasNext()) { throw new NoSuchElementException();}
           return random[i++];
       }       
   }
   public static void main(String[] args) { 
         RandomizedQueue<String> q = new RandomizedQueue<String>();
         while (!StdIn.isEmpty()) {  
             String s = StdIn.readString();
             if (s.equals("-")) { StdOut.print(q.dequeue());StdOut.print(q.size());}
             else {q.enqueue(s); StdOut.print(q.size());}//iterator().next());}                         
         }         
     }
}