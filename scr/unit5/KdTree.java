import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree {
   
   private Node root;
    
   private static class Node {
       private Point2D pt;
       private RectHV rect;
       private Node left;
       private Node right;
       private boolean vertical;
       private int size;
       private Node(Point2D pt) {
           this.pt = pt;
       }
   }
    
   public KdTree() {                              // construct an empty set of points      
       root = null;
   }
   
   public boolean isEmpty() {                     // is the set empty? 
       return root == null;
   }
   
   public int size() { return size(root); }                       // number of points in the set 
   
   private int size(Node node) {
       if (node == null) return 0;
       else return node.size;
   }
   
   private boolean goleft(Double x1, Double y1, Double x2, Double y2, boolean comp_x) {
       if ( comp_x && Double.compare(x1, x2) <= 0 ) return false;
       else if ( comp_x && Double.compare(x1, x2) > 0 ) return true;
       else if ( !comp_x && Double.compare(y1, y2) <= 0 ) return false;
       else return true;
   }
   
   public void insert(Point2D p) { root = insert(p, root, 0.0, 1.0, 0.0, 1.0, true); }           // add the point to the set (if it is not already in the set)
   
   private Node insert(Point2D p, Node node, double xmin, double xmax, double ymin, double ymax, boolean comp_x) {
       if (node == null) { 
           Node newNode = new Node(p);
           newNode.rect = new RectHV(xmin, ymin, xmax, ymax);
           if (comp_x) {newNode.vertical = true;}
           else {newNode.vertical = false;}
           newNode.size = 1;
           return newNode;
       }
       
       if (node.pt.equals(p)) return node;
       
       boolean left = goleft(node.pt.x(), node.pt.y(), p.x(), p.y(), comp_x);
       if (comp_x && left) node.left = insert(p, node.left, xmin, node.pt.x(), ymin, ymax, false);
       else if (comp_x && !left) node.right = insert(p, node.right, node.pt.x(), xmax, ymin, ymax, false);
       else if (!comp_x && left) node.left = insert(p, node.left, xmin, xmax, ymin, node.pt.y(), true);
       else node.right = insert(p, node.right, xmin, xmax, node.pt.y(), ymax, true);
       
       node.size = 1 + size(node.right) + size(node.left);
       return node;
   }
     
   public boolean contains(Point2D p) {           // does the set contain point p? 
       Node node = root;
       while (node != null) {
           if ( node.pt.equals(p) ) return true;
           else if (goleft(node.pt.x(),node.pt.y(), p.x(), p.y(), node.vertical)) node = node.left;
           else node = node.right;
       }
       return false;
   }
     
   public void draw() { draw(root);}                      // draw all points to standard draw 
      
   private void draw(Node node) {
       if ( node != null) {
           StdDraw.setPenColor(StdDraw.BLACK);
           StdDraw.setPenRadius(.02);
           node.pt.draw();
           StdDraw.setPenRadius(.01);
           if (node.vertical) { 
               StdDraw.setPenColor(StdDraw.RED); 
               StdDraw.line(node.pt.x(), node.rect.ymin(), node.pt.x(), node.rect.ymax());
           }
           if (!node.vertical) {
               StdDraw.setPenColor(StdDraw.BLUE);
               StdDraw.line(node.rect.xmin(), node.pt.y(), node.rect.xmax(), node.pt.y());
           }
           
           draw(node.left);
           draw(node.right);
       }
   }
   
   public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle 
       Stack<Point2D> s = new Stack<Point2D>();
       range(s, rect, root);
       return s;
   }
   
   private void range(Stack<Point2D> s, RectHV rect, Node node) {
           if (node != null) {
               if (rect.contains(node.pt)) {s.push(node.pt);}
               if (rect.intersects(node.rect)) { range(s, rect, node.left); range(s, rect, node.right);}
           }
       }
  
   public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
       Stack<Node> sn = new Stack<Node>();
       Node node = root;
       double dmin = Double.MAX_VALUE;
       Point2D result = null;
   
       while ( node != null) {
           if (dmin < node.rect.distanceSquaredTo(p)) break;
           double squDist = p.distanceSquaredTo(node.pt);
           if (Double.compare(squDist, dmin) < 0) { dmin = squDist; result = node.pt; }
           boolean left = goleft(node.pt.x(), node.pt.y(), p.x(), p.y(), node.vertical);
           if ( left ) { 
               if (node.right != null) sn.push(node.right);
               node = node.left;
           }
           if ( !left ) {
               if (node.left != null) sn.push(node.left);
               node = node.right;              
           }        
       }
       
       while ( !sn.isEmpty() ) {
           Node snNode = sn.pop();
           while ( snNode != null ) {
               if (dmin < snNode.rect.distanceSquaredTo(p)) break;
               double squDist = p.distanceSquaredTo(snNode.pt);
               if (Double.compare(squDist, dmin) < 0) { dmin = squDist; result = snNode.pt; }
               boolean left = goleft(snNode.pt.x(), snNode.pt.y(), p.x(), p.y(), snNode.vertical);
               if ( left ) { 
                   if (snNode.right != null) sn.push(snNode.right);
                   snNode = snNode.left;
               }
               if ( !left ) {
                   if (snNode.left != null) sn.push(snNode.left);
                   snNode = snNode.right;              
               }
           }        
       }
       return result;
   }
   
   public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        KdTree kdtree = new KdTree();
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        StdDraw.show();
        kdtree.draw();
        brute.draw();        
    }
}