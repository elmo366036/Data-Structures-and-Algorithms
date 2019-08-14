import java.util.*;

public class CoveringSegments {

    private static int[] optimalPoints(Segment[] segments) {           
        ArrayList<Integer> startcopy1 = new ArrayList<Integer>();
        ArrayList<Integer> endcopy1 = new ArrayList<Integer>();        
        Segment[] sortedSegmentsStart = new Segment[segments.length];
        
        //create start and end array lists
        for (int i = 0; i<segments.length; i++){
            startcopy1.add(segments[i].start);
            endcopy1.add(segments[i].end);
        }

        //sort the array list by end position
        for (int indexCount = 0; indexCount < segments.length; indexCount++){        
            int minPosition = Collections.min(endcopy1);
            int minPositionIndex = endcopy1.indexOf(minPosition);
            sortedSegmentsStart[indexCount] = new Segment(startcopy1.get(minPositionIndex),endcopy1.get(minPositionIndex));
            startcopy1.remove(minPositionIndex);
            endcopy1.remove(minPositionIndex);          
        }              
        
        // create one dimensional array of processed segments
        ArrayList<Integer> points2 = new ArrayList<Integer>();
        int point = sortedSegmentsStart[0].end;
        points2.add(point);
        int points2Index = 0;       
        for (int i = 1; i < sortedSegmentsStart.length; i++) {            
            if (point < sortedSegmentsStart[i].start || point > sortedSegmentsStart[i].end){
                point = sortedSegmentsStart[i].end;
                points2.add(point);}                                   
        } 
        int[] points3 = new int[points2.size()];
        for (int k=0; k<points3.length;k++){
            points3[k]=points2.get(k);}            
        return points3;
    }

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
    
    public static void tester(){
        int n = 3;
        int[] start = new int[] {1,2,3};
        int[] end = new int[] {3,5,6};
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(start[i], end[i]);
        } 
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");        
        }
        System.out.println("");
        
        n = 4;
        start = new int[] {4,1,2,5};
        end = new int[] {7,3,5,6};
        segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(start[i], end[i]);
        } 
        points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");        
        }
        System.out.println("");
    }
}
 
