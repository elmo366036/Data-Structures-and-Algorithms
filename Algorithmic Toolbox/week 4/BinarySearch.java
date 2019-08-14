import java.io.*;
import java.util.*;

public class BinarySearch {

    static int binarySearch(int[] a, int x) {
        int left = 0, right = a.length-1;
        //write your code here
        
        while (left <= right){
            //System.out.println("left "+left+"\t right "+right);
            double temp = left + ((double)right-(double)left)/2;
            double mid = Math.floor(temp);
            //System.out.println("temp "+temp+"\t mid "+mid+"\t mid as int "+(int)mid);
            if (x == a[(int)mid])
                {return (int)mid;}
            else if (x < a[(int)mid]){
                    right = (int)mid - 1;}
            else {left = (int)mid + 1;}                     
        }                           
        return -1;
    }

    static int linearSearch(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
          b[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            //replace with the call to binarySearch when implemented
            System.out.print(binarySearch(a, b[i]) + " ");
        }
    }
    
    public static void tester(){
    int n = 10;
    int[] a = new int[]{1,5,8,12, 13};
    int[] b = new int[]{8,1,23,1,11};
    //int[] b = new int[]{23};
    for (int i = 0; i < 5; i++) {
        System.out.print(binarySearch(a, b[i]) + " ");
        }
    }
    
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
