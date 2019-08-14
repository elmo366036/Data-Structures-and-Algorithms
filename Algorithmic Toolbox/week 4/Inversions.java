import java.util.*;
import java.math.*;

public class Inversions {

    private static long getNumberOfInversionsNaive(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        for (int i = 0; i < a.length; i++){
            for (int j = i + 1; j < a.length ; j++){
                if (a[i] > a[j]){
                    numberOfInversions++;}
                }
            }
        return numberOfInversions;
    }
    
    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        /*
        if (right <= left + 1) {
            return numberOfInversions;
        }
        */
        //if (a.length <= 1){return 0;}
        if (a.length <= 5){
            for (int i = 0; i < a.length; i++){
                for (int j = i + 1; j < a.length ; j++){
                    if (a[i] > a[j]){
                        numberOfInversions++;}
                }
            }
            return numberOfInversions;
        }
        
        int ave = (left + right) / 2;       
        int[] leftArray = Arrays.copyOfRange(a, 0, ave + 1);
        int[] rightArray = Arrays.copyOfRange(a, ave + 1, a.length);
        // brute force counting for each half
        for (int i = 0; i < leftArray.length; i++){
            for (int j = i + 1; j < leftArray.length ; j++){
                if (leftArray[i] > leftArray[j]){
                    numberOfInversions++;}
                if (i < rightArray.length && j < rightArray.length){
                    if (rightArray[i] > rightArray[j]){
                        numberOfInversions++;}
                }
            }
        }
        //System.out.println("number of Inversions from left and right "+numberOfInversions);
        //numberOfInversions += getNumberOfInversions(leftArray, b, left, ave);
        //numberOfInversions += getNumberOfInversions(rightArray, b, ave, right);
        
        //merge and sort
        randomizedQuickSort(leftArray,0,leftArray.length-1);
        randomizedQuickSort(rightArray,0,rightArray.length-1);
        //System.out.println("Left Array length "+leftArray.length);
        for (int i = 0; i < rightArray.length; i++){
            //System.out.print(leftArray[i]+" ");
            int searchResult = Arrays.binarySearch(leftArray,rightArray[i]);
            //System.out.println(rightArray[i]+" "+searchResult);
            if (searchResult < 0){
                int insertionPoint = (searchResult + 1) * -1;
                //System.out.println("search "+rightArray[i]+" in leftArray   result "+ insertionPoint+"\t count "+(leftArray.length - insertionPoint));
                numberOfInversions += (leftArray.length - insertionPoint);
                }
            else {
                //System.out.println("search "+rightArray[i]+" in leftArray   result "+ searchResult);
                int initSearchResult = searchResult;
                //if (i != rightArray.length - 1){
                    //find the last instance of searchResult
                    for (int t = searchResult + 1; t < leftArray.length; t++){
                        if (leftArray[t] == leftArray[initSearchResult]) {searchResult++;}
                        else {break;}
                    }
                //}
                //System.out.println("searchResult "+searchResult);
                numberOfInversions += (leftArray.length - 1 - searchResult);
                }            
        }
        //System.out.println(numberOfInversions);
        return numberOfInversions;
    }
    
    private static int[] partition3(int[] a, int l, int r) {
        int x = a[l];
        int j = l;
        int k = l;
        for (int i = l + 1; i <= r; i++) {
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
    
    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = (r-l)/2 + l;
        int t = a[l];
        a[l] = a[k];
        a[k] = t;
        while (l<r){        
            int[] p = partition3(a,l,r);
            if ((p[0]-l)<(r-p[0])){
                randomizedQuickSort(a,l,p[0]-1);
                l=p[0]+1;}
            else {
                randomizedQuickSort(a,p[1]+1,r);
                r=p[1]-1;}
            }
    } 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        System.out.println(getNumberOfInversionsNaive(a, b, 0, a.length - 1));
        //System.out.println(mergeSort(a, b, 0, a.length - 1));
    }
    
        public static void staticTester(){        
        int n = 5;
        /*int[] aa = new int[] {2,3,9,2,9};
        int[] bb = new int[n];
        int[] cc = new int[] {2,3,9,2,9};
        int[] dd = new int[] {2,3,9,2,9};*/
        int[] aa = new int[] {2,3,9,6,4};
        int[] bb = new int[n];
        int[] cc = new int[] {2,3,9,6,4};
        int[] dd = new int[] {2,3,9,6,4};       
        long cResult = getNumberOfInversionsNaive(cc, bb, 0, aa.length-1);
        long dResult = mergeSort(dd, bb, 0, aa.length-1);
        System.out.println();
        for (int j = 0;j<n;j++){
            System.out.print(aa[j]+" ");}
        System.out.println();
        System.out.println("Result Naive "+cResult);
        System.out.println("Result Fast "+dResult);             
        System.out.println();
        System.out.println("Static Test Complete");
    }
        
    public static void stressTester(){    
        //variable test cases
        Random random = new Random();
        Random rand = new Random();
        int maxArraySize = 10;
        int runTime = 1000;
        int maxDigitSize = 9;
      
        for (int k = 1; k <= runTime; k++){       
            int nn = rand.nextInt(maxArraySize)+1;
            int[] a = new int[nn];
            int[] b = new int[nn];
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
            long cResult = getNumberOfInversionsNaive(c, b, 0, nn-1);
            long dResult = mergeSort(d, b, 0, nn-1);
            //compare c and d
            boolean pass = true;           
            if (cResult != dResult){pass = false;}
            if (!pass){
                System.out.println("");
                System.out.println("ERROR");
                for (int j = 0;j<nn;j++){
                    System.out.print(a[j]+" ");}
                System.out.println(" Naive Result "+cResult+"\t Fast Result "+dResult);                
                break;
                }                               
            if (pass){
                //System.out.println("");
                System.out.println("PASS ("+k+" out of "+runTime+")");
                //for (int i = 0; i < n; i++) {
                //    System.out.print(c[i] + " ");
                //}
            }           
        }  
        System.out.println();
        System.out.println("Test Complete");
    }

// Merge two sorted subarrays arr[low .. mid] and arr[mid + 1 .. high]
    public static long merge(int[] arr, int[] aux, int low, int mid, int high)
    {
        int k = low, i = low, j = mid + 1;
        long inversionCount = 0;
        // While there are elements in the left and right runs
        while (i <= mid && j <= high)
        {
            if (arr[i] <= arr[j]) { //<=
                aux[k++] = arr[i++];
            }
            else {
                aux[k++] = arr[j++];
                inversionCount += (mid - i + 1);    // NOTE +1
            }
        }
        // Copy remaining elements
        while (i <= mid)
            {aux[k++] = arr[i++];}
        // Don't need to copy second half
        // copy back to the original array to reflect sorted order
        for (i = low; i <= high; i++) {
            arr[i] = aux[i];
        }
        return inversionCount;
    }

    // Sort array arr [low..high] using auxiliary array aux[]
    public static long mergeSort(int[] arr, int[] aux, int low, int high)
    {
        // Base case
        if (high == low) {    // if run size == 1
            return 0;
        }
        // find mid point
        int mid = (low + ((high - low) >> 1));
        long inversionCount = 0;
        // recursively split runs into two halves until run size == 1,
        // then merge them and return back up the call chain
        inversionCount += mergeSort(arr, aux, low, mid);      // split / merge left  half
        inversionCount += mergeSort(arr, aux, mid + 1, high); // split / merge right half
        inversionCount += merge(arr, aux, low, mid, high);    // merge the two half runs
        return inversionCount;
    }
/*
    public static void main(String[] args)
    {
        int[] arr = { 1, 9, 6, 4, 5 };
        int[] aux = Arrays.copyOf(arr, arr.length);

        // get inversion count by performing merge sort on arr
        System.out.println("Inversion count is " + 
                         mergeSort(arr, aux, 0, arr.length - 1));
    }
*/
    
}

