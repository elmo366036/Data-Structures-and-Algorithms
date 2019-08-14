import java.util.*;
import java.io.*;
import java.math.*;

public class MajorityElement {
    private static int getMajorityElement(int[] a, int left, int right) {
        Arrays.sort(a);
        int aLength = a.length;
        /* make sure array is sorted
        for (int i=0;i<aLength;i++){
            System.out.print(a[i]+" ");}
        */    
        //simple cases - small arrays
        if (aLength == 0){return -1;}
        if (aLength == 1){return 1;}
        if (aLength == 3){
            if (a[0] == a[1] || a[1] == a[2]){return 1;}
            else {return -1;}}
        if (aLength == 2){
            if (a[0] == a[1]){return 1;}
            else {return -1;}}

        //large arrays
        double midDouble = Math.floor((double)aLength/2);
        int mid = (int) midDouble;
        int count = 1;

        if (a[mid-1] != a[mid] && a[mid] != a[mid+1]){return -1;}
        //if array is odd        
        if (aLength % 2 != 0){
            for (int i = 1;i <= mid; i++){
                if (a[mid-i] == a[mid]){count++;}
                if (a[mid+i] == a[mid]){count++;}
                if (count > mid){return 1;}
                if (a[mid-i] != a[mid] && a[mid+i] != a[mid]){return -1;} 
            }            
        }
        //else it is even
        else {              
            for (int i = 1;i <= mid; i++){
                if (a[mid-i] == a[mid]){count++;}
                if (mid+i < aLength){
                    if (a[mid+i] == a[mid]){count++;}}
                if (count > mid){return 1;}
                if (mid+i < aLength){
                    if (a[mid-i] != a[mid] && a[mid+i] != a[mid]){return -1;}}
                }               
            } 
        return -1;             
        /*
  
        if (left == right) {
            return -1;
        }
        if (left + 1 == right) {
            return a[left];
        }
        //write your code here
        return -1;
        */
    }
    
    public static int majorityElementNaive(int[] a){
        Arrays.sort(a);
        int n = a.length;
        int[] maxCount = new int[n];
        int counter = 1;
        int max = 1;
        for (int i = 1;i<a.length;i++){            
            if (a[i] == a[i-1]){
                counter++;
                maxCount[i] = counter;}
            else {
                counter = 1;
                }
        }        
        
        for (int i = 0; i<maxCount.length;i++){
            //System.out.print(maxCount[i]+" ");
            if (maxCount[i] > max){
                max = maxCount[i];}
            }
        
        //System.out.println ("max "+max+"\t length "+n/2);
        //System.out.println("");
        if (max <= n/2){return -1;}
        else {return 1;}
    }
       
    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (getMajorityElement(a, 0, a.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
    
    public static void tester(){
        int n = 0;
        //static test cases
        /*
        n= 10;
        int[] a = new int[]{2,13,2,93333,2,2,2,2,7999999,6234};
        for (int j = 0;j<n;j++){
            System.out.print(a[j]+" ");}
        System.out.println("");
        if (getMajorityElement(a, 0, a.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }                       
        
        n = 1;
        int[] b = new int[]{2};
        if (getMajorityElement(b, 0, b.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        n = 2;
        int[] c = new int[]{2,1};
        if (getMajorityElement(c, 0, c.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }        
        
        n = 2;        
        int[] d = new int[]{2,2};
        if (getMajorityElement(d, 0, d.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }         
        n = 5;

        int[] e = new int[]{2,3,9,2,2};
        if (getMajorityElement(e, 0, e.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        n = 5;
        int[] f = new int[]{8,3,9,2,2};
        if (getMajorityElement(f, 0, f.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        n = 6;
        int[] g = new int[]{8,3,9,2,2,2};
        if (getMajorityElement(g, 0, g.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
        
        n = 6;
        int[] h8 = new int[]{8,3,9,2,2,3};
        if (getMajorityElement(h8, 0, h8.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
        
        n = 6;
        int[] i9 = new int[]{8,2,9,2,2,2};
        if (getMajorityElement(i9, 0, i9.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }         
        
        n = 10;
        int[] j10 = new int[]{512766168,717383758,5,126144732,5,573799007,5,5,5,405079722};
        for (int j = 0;j<n;j++){
            System.out.print(j10[j]+" ");}
        System.out.println("");        
        if (getMajorityElement(j10, 0, j10.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
        */
       
       //variable test cases
        Random rand = new Random();
        
        for (int k = 1 ; k<1000 ; k++){       
            n = rand.nextInt(8)+2;
            int[] aa = new int[n];
            aa[0] = rand.nextInt(1000)+1;
        
            for (int i = 1;i<n;i++){
                int coinFlip = rand.nextInt(2);
                if (coinFlip == 0){
                    aa[i]=aa[0];}
                    else {aa[i] = rand.nextInt(1000)+1;}
                }        
        
            int c = majorityElementNaive(aa);
            int d = getMajorityElement(aa, 0, aa.length);
            
            if (c != d){
                System.out.println("");
                System.out.println("ERROR "+"\t naive "+c+"\t fast "+d);
                for (int j = 0;j<n;j++){
                    System.out.print(aa[j]+" ");}}
                    
            if (c == d){
                System.out.println("PASS ("+k+" out of 999)");
            }
        }    
        /*
        if (majorityElementNaive(aa) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
        if (getMajorityElement(aa, 0, aa.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }            
       */
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

