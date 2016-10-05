import java.util.Arrays;

public class FastCollinearPoints {
   /* define segment array and size of segment array */
   private final LineSegment[] seg;
   private int p;
   /*************************************************/
   
   public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
       
       /************* corner cases **************************************************************/
       if (points == null) throw new NullPointerException();
       int N = points.length;
       Point[] temp = new Point[N];
       for (int i=0; i<N;i++) {
           if (points[i] == null) throw new NullPointerException();
           for (int j=i+1; j<N; j++) {
               if ( points[i].compareTo(points[j]) == 0 ) throw new IllegalArgumentException();
           }
           // copy points to temp for later sort, so that original points won't be interupted.
           temp[i] = points[i];
       }
       /**************************************************************************************/
       
       /******* compute size and segment *****************************************************/
       
       //initiate size and segment
       p = 0;
       LineSegment[] seg_ini = new LineSegment[N];
       
       //i iterate over each point, and for each point, sort by slope to other points
       for (int i=0; i<N; i++) {
           Arrays.sort(temp, points[i].slopeOrder());
           // with sorted array, start from first point to see if collinear
           int j = 1;
           while (j<N-2) {
               // the following loop compute how many points are colliear starting from j
               int t=j;
               while (t<N-1) {
                   if (temp[0].slopeTo(temp[t])==temp[0].slopeTo(temp[t+1])) { t++; }
                   else { break;}
               }
               // number of points that are colliear (should be count+2)
               int count = t-j;
               //if number of points colliear less than 4, start from next j.
               if (count<2) {j++;}
               //otherwise, at least 4 points colliear
               else {
                   // compute points that collinear
                   Point[] gPt = new Point[count+2];
                   gPt[0] = temp[0];
                   for (int r=1; r<count+2; r++) {gPt[r]=temp[j+r-1];}
                   // sort points and create segment
                   Arrays.sort(gPt);
                   LineSegment line = new LineSegment(gPt[0], gPt[count+1]);
                   // check if segment already in segment list, if so, ignore, otherwise, add the new segment
                   boolean inside = false;
                   for (int q=0; q<p; q++) {
                       if ((seg_ini[q].toString()).equals(line.toString())) inside = true;
                   }
                   if (inside == false) {seg_ini[p] = line; p++;}
                   // continue to interate j
                   j=j+count+1;
               }               
           }
       }
       // copy seg_ini to seg to remove possible extra null values.
       seg = new LineSegment[p];
       for(int m = 0; m<p; m++) { seg[m]=seg_ini[m];}
       
       /**************************************************************************************/
   }
      
   public int numberOfSegments() {       // the number of line segments
       return p;
   }
   public LineSegment[] segments() {                // the line segments
       return seg;
   }
}