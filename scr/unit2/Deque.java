import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    
     private class Node
    {
        Item item;
        Node next;
        Node prev;
    }
     
     public Deque() {
         first = null;
         last = first;
     }                                      // construct an empty deque
    
     public boolean isEmpty()  {            // is the deque empty?
         return first==null;
     }
   
     public int size() {                    // return the number of items on the deque
         int i = 0;
         Node current = first;
         while (current != null) {
             i++;
             current=current.next;
         }
         return i;
     }
     
     public void addFirst(Item item) {        // add the item to the front
         if (item==null) { throw new NullPointerException(); }
         Node oldfirst = first;
         first = new Node();
         first.item = item;
         first.prev = null;
         first.next = oldfirst;
         if (size()==1) {  last = first; }
         else {  oldfirst.prev = first;}
     }
     
     public void addLast(Item item) {                                // add the item to the end
         if (item == null) { throw new NullPointerException(); }
         Node oldlast = last;
         last = new Node();
         last.item = item;
         last.next = null;
         if (isEmpty()) { last.prev = null; first = last;}
         else {last.prev = oldlast;oldlast.next = last;}          
     }
     
     public Item removeFirst() {               // remove and return the item from the front
         if (isEmpty()) { throw new NoSuchElementException(); }
         Node oldfirst = first;
         Item item = oldfirst.item;
         first = first.next;         
         oldfirst.next = null;
         if ( size()==0 ) { last = first;}
         else first.prev = null;
         return item;
     } 
     
     public Item removeLast() {               // remove and return the item from the end
         if (isEmpty()) { throw new NoSuchElementException(); }
         Node oldlast = last;         
         Item item = oldlast.item;
         last = last.prev;
         oldlast.prev = null;
         if (size()==1) { first = last;}
         else last.next=null;         
         return item;       
     }
     public Iterator<Item> iterator() { return new ListIterator(); }      // return an iterator over items in order from front to end
     
     private class ListIterator implements Iterator<Item> {
         private Node current = first;
         
         public boolean hasNext() {  return current != null;}
         public void remove() {  throw new UnsupportedOperationException(); }
         public Item next() {
             if ( !hasNext() ) { throw new NoSuchElementException(); }
             Item item = current.item;
             current = current.next;
             return item;
         }
     }
     
     public static void main(String[] args) { 
         Deque<String> deck = new Deque<String>();
         while (!StdIn.isEmpty()) {  
             String s = StdIn.readString();
             if (s.equals("-")) { StdOut.print(deck.removeFirst());StdOut.print(deck.size());}
             else {deck.addLast(s); StdOut.print(deck.size());}                         
         }         
     }
}
