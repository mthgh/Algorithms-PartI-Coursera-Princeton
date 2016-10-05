import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
   private SET<Point2D> points;
   public PointSET() { points = new SET<Point2D>();}                              // construct an empty set of points 
   public boolean isEmpty() { return points.isEmpty();}                     // is the set empty? 
   public int size()  { return points.size();}                       // number of points in the set 
   public void insert(Point2D p) {                                   // add the point to the set (if it is not already in the set)
       if (p == null) throw new NullPointerException();             
       points.add(p);
   }             
   public boolean contains(Point2D p) {                               // does the set contain point p?
       if (p == null) throw new NullPointerException();
       return points.contains(p);
   }             
   public void draw() {                                                  // draw all points to standard draw
       for (Point2D p: points) { p.draw();}
   }                         
   public Iterable<Point2D> range(RectHV rect) {                           // all points that are inside the rectangle
       Stack<Point2D> s = new Stack<Point2D>();
       for (Point2D p: points) {
           if ( rect.contains(p) ) s.push(p);
       }
       return s;
   }               
   public Point2D nearest(Point2D p) {                                    // a nearest neighbor in the set to point p; null if the set is empty 
       if (p == null) throw new NullPointerException();
       double dmin = Double.MAX_VALUE;
       Point2D pmin = null;
       for (Point2D t: points) {
           double dist = t.distanceTo(p);
           if (Double.compare(dist, dmin) < 0 && dist>0) { 
               dmin = dist; 
               pmin = t;
           }
       }
       return pmin;
   }
   
   public static void main(String[] args) {
        PointSET pset = new PointSET();
        Point2D p = new Point2D(0.2, 0.3);
        RectHV rect = new RectHV(0.2, 0.2, 0.6, 0.6);
        pset.insert(p);
        for (int i = 0; i < 1000; i++)
            pset.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
        rect.draw();
        StdDraw.circle(p.x(), p.y(), p.distanceTo(pset.nearest(p)));
        pset.draw();
        StdDraw.show(0);
        StdOut.println("Nearest to " + p.toString() + " = " + pset.nearest(p));
        for (Point2D point : pset.range(rect))
            StdOut.println("In Range: " + point.toString());
    }
}