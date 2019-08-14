import java.io.*;
import java.util.*;

public class Sorting {
    private static Random random = new Random();

    private static int[] partition3(int[] a, int l, int r) {
        //  System.out.println("l "+l+"\t r" +r);
        int x = a[l];
        int j = l;
        int k = l;
        for (int i = l + 1; i <= r; i++) {
          //System.out.println("r "+r+"\t i "+ i +"\t j "+j+"\t k "+k);
            if (a[i] <= x) {                
                j++;
                k++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
            else if (a[i] == x) {
                k++;    
            }          
        }
        int v = a[l];
        a[l] = a[j];
        a[j] = v;
        int[] m = {j,k};
        return m;      
    }

    private static int partition2(int[] a, int l, int r) {
        int x = a[l];
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            //System.out.println("x "+x+"\t r "+r+"\t i "+ i +"\t j "+j);
            if (a[i] <= x) {
                j++;
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[l];
        a[l] = a[j];
        a[j] = t;
        //System.out.println("j "+j);
        return j;
    }

    private static void randomizedQuickSortNaive(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }        
        //int k = random.nextInt(r - l + 1) + l;
        int k = (r-l)/2 + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;         
        while (l<r){                 
            //System.out.println("Naive "+l+" "+r);
            int m = partition2(a, l, r);
            //System.out.println("m "+m);
            if ((m-l)<(r-m)){
                randomizedQuickSortNaive(a, l, m - 1);
                l=m+1;}
            else {
                randomizedQuickSortNaive(a, m + 1, r);
                r=m-1;}
            }
    }
    
    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        //int k = random.nextInt(r - l + 1) + l;
        int k = (r-l)/2 + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;
        while (l<r){        
            //System.out.println("Fast "+l+" "+r);
            int[] p = partition3(a,l,r);
            //System.out.println("p[] "+p[0]+" "+p[1]);
            if ((p[0]-l)<(r-p[0])){
                //System.out.println("1");
                randomizedQuickSort(a,l,p[0]-1);
                l=p[0]+1;}
            else {
                //System.out.println("2");
                randomizedQuickSort(a,p[1]+1,r);
                r=p[1]-1;}
            }
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        randomizedQuickSort(a, 0, n - 1);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
    }
    
    public static void staticTester(){        
        int n = 7;
        int[] aa = new int[] {7,7,7,9,9,5,5};
        int[] cc = new int[] {7,7,7,9,9,5,5};
        int[] dd = new int[] {7,7,7,9,9,5,5};      
        randomizedQuickSortNaive(cc, 0, n - 1);
        randomizedQuickSort(dd, 0, n - 1);        
        for (int j = 0;j<n;j++){
            System.out.print(aa[j]+" ");}
        System.out.println();
        for (int j = 0;j<n;j++){
            System.out.print(cc[j]+" ");}                
        System.out.println();
        for (int j = 0;j<n;j++){
            System.out.print(dd[j]+" ");} 
        System.out.println();
        System.out.println("Static Test Complete");
    }
        
    public static void stressTester(){    
        //variable test cases
        Random random = new Random();
        Random rand = new Random();
        int maxArraySize = 100000;
        int runTime = 1000;
        int maxDigitSize = 9;
      
        for (int k = 1; k <= runTime; k++){       
            int nn = rand.nextInt(maxArraySize)+1;
            int[] a = new int[nn];
            int[] c = new int[nn];
            int[] d = new int[nn];
            a[0] = rand.nextInt(maxDigitSize)+1;
            c[0] = a[0];
            d[0] = a[0];
            //create imput array a and output arrays c and d
            for (int i = 1; i < nn; i++){
                int coinFlip = rand.nextInt(2);
                if (coinFlip == 0){
                        a[i] = a[i-1];
                        c[i] = a[i];
                        d[i] = a[i];
                }
                else {
                    a[i] = rand.nextInt(maxDigitSize)+1;
                    c[i] = a[i];
                    d[i] = a[i];
                    }                 
            }
            //sort the two output arrays c and d
            randomizedQuickSortNaive(c, 0, nn - 1);
            randomizedQuickSort(d, 0,nn - 1);
            //compare c and d
            boolean pass = true;
            int prevc = -1;
            int prevd= -1;
            for (int z = 0; z < nn; z++){
                if (prevc > c[z]){pass = false;}
                if (prevd > d[z]){pass = false;}
                prevc=c[z];
                prevd=d[z];                
                if (c[z] != d[z]){pass = false;}
            }
            if (!pass){
                System.out.println("");
                System.out.println("ERROR");
                for (int j = 0;j<nn;j++){
                    System.out.print(a[j]+" ");}
                System.out.println();
                for (int j = 0;j<nn;j++){
                    System.out.print(c[j]+" ");}                
                System.out.println();
                for (int j = 0;j<nn;j++){
                    System.out.print(d[j]+" ");}                
                break;
                }                    
            /*
            if (pass){
                System.out.println("");
                System.out.println("PASS ("+k+" out of "+runTime+")");
                for (int i = 0; i < n; i++) {
                    System.out.print(c[i] + " ");
                }
            }
            System.out.println("");
            */
        }  
        System.out.println();
        System.out.println("Test Complete");
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

