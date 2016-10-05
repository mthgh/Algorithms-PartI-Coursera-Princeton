import java.util.Arrays;

public class BruteCollinearPoints {
   private final LineSegment[] Seg;
   private int p;
       
   public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
       // corner cases
       if (points==null) throw new NullPointerException();
       int N = points.length;
       for (int i = 0; i<N; i++) {
           if (points[i]==null) throw new NullPointerException();
           for (int j = i+1; j<N; j++) {
               if ( points[i].compareTo(points[j]) == 0 ) throw new IllegalArgumentException();
           }
       }
       // compute all line segments
       if (N < 4) { Seg = null;
                    p = 0;}
       else {
           LineSegment[] Seg_ini = new LineSegment[N*N*N*N];
           p = 0;
           for (int i = 0; i < N; i++) {
               for (int j = i+1; j<N; j++) {
                   for (int k = j + 1; k<N; k++) {
                       for (int t = k+1; t<N; t++) {
                           if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && 
                              points[i].slopeTo(points[j]) == points[i].slopeTo(points[t])) {
                              Point[] gPt = new Point[] {points[i], points[j], points[k], points[t]};
                              Arrays.sort(gPt);
                              Seg_ini[p] = new LineSegment(gPt[0], gPt[3]);
                              p++;
                           }
                       }
                   }
               }
           }
           Seg = new LineSegment[p];
           for(int m = 0; m<p; m++) { Seg[m]=Seg_ini[m];}
       }
   }
   public  int numberOfSegments() {        // the number of line segments
      return p;
   }
   public LineSegment[] segments() {               // the line segments
      return Seg;
   }
}