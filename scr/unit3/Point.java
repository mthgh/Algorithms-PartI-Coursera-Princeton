import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else if (this.y == that.y) return 0.0;
        else return (that.y-this.y)*1.0/(that.x-this.x);
    }

    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        else if (this.x == that.x && this.y == that.y) return 0;
        else return 1;
    }

    public Comparator<Point> slopeOrder() { return new SlopeComparator();}
    
    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point one, Point two) {
            double firstSlope = slopeTo(one);
            double secondSlope = slopeTo(two);
            return Double.compare(firstSlope, secondSlope);
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point a = new Point(1, 2);
        Point b = new Point(2, 3);
        System.out.println(a.toString());
        System.out.println(a.compareTo(b));
        System.out.println(a.slopeTo(b));
        System.out.println(a.slopeOrder());
    }
}