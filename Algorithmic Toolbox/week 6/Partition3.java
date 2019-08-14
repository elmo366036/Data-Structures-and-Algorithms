import java.util.*;
import java.io.*;

public class Partition3 {
    private static int partition3(int[] A) {
        int total = 0;
        int length = A.length;
        for (int i = 0; i < length; i++){
            total += A[i];
        }
        
        //corner cases
        if (length < 3){return 0;}
        if (total % 3 != 0){return 0;}    
        
        //we want a large digit first to force a minimum set
        //swap first and last digits if the last digit is greater than the first
        if (A[length-1] > A[0]){
            int temp = A[length-1];
            A[length-1] = A[0];
            A[0] = temp;
        }    
        
        int totalDivThree = total/3;
        ArrayList<int[][]> partition = optimalWeight(totalDivThree, A);
        int optimalWeightArray[][] = partition.get(0);
        int keepArray[][] = partition.get(1);
        int optimalWeight = optimalWeightArray[length][totalDivThree];               
        if (optimalWeight != totalDivThree){return 0;}                        
        //printMatrix(optimalWeightArray);
        //printMatrix(keepArray);              
        ArrayList<Integer> parsedNumbers = new ArrayList<Integer>();        
        parsedNumbers = printKeep(keepArray,totalDivThree, A); 

        //remove set from A and test remainder
        int[] APrime = new int[length - parsedNumbers.size()];
        int z = 0;
        for (int i = 0; i < length; i++){
            //System.out.println("parsedNumber contains "+A[i]+" "+parsedNumbers.contains(A[i]));
            if (!parsedNumbers.contains(A[i])){
                APrime[z] = A[i];
                z++;
            }
            else {
                parsedNumbers.remove(parsedNumbers.indexOf(A[i]));
            }
        }
        partition = optimalWeight(totalDivThree, APrime);
        optimalWeightArray = partition.get(0);
        keepArray = partition.get(1);
        optimalWeight = optimalWeightArray[APrime.length][totalDivThree];
        if (optimalWeight != totalDivThree){return 0;}                        
    
        return 1;
    }

    static ArrayList<int[][]> optimalWeight(int W, int[] w) {
        ArrayList<int[][]> result = new ArrayList<>();
        int numOfWeights = w.length;
        int[][] values = new int[numOfWeights+1][W+1];
        int[][] keep = new int[numOfWeights+1][W+1];
        for (int k = 0; k <= W; k++){
            values[0][k] = 0;}
        for (int k = 0; k <= numOfWeights; k++){
            values[k][0] = 0;}        
        for (int i = 1; i <= numOfWeights; i++){
            for (int j = 1; j <= W; j++){
                values[i][j] = values[i-1][j];
                keep[i][j] = 0;
                if (w[i-1] <= j) { // if the weight of i is less than j
                   int val = values[i-1][j-w[i-1]] + w[i-1]; // w[i-1] = v[i-1] bc w=v
                   if (values[i][j] < val){
                       values[i][j] = val;
                       keep[i][j] = 1;
                    }   
                }
            }               
        }
        result.add(values);
        result.add(keep);
        return result;
    }    

    private static void printMatrix(int[][] matrix){
        System.out.println(Arrays.deepToString(matrix));
    }
    
    private static ArrayList<Integer> printKeep(int[][] keep, int b, int[] a){        
        int[] result = new int[a.length];
        ArrayList<Integer> output = new ArrayList<Integer>();
        for (int i = 0; i < result.length; i++){result[i]=0;}
        for (int i = a.length; i >= 1; i--){
            if (keep[i][b] == 1){
                result[i-1] = 1;
                b-=a[i-1];
            }
        }
        for (int i = 0; i < result.length; i++){
            if (result[i] == 1){
                output.add(a[i]);
            }
        }        
        return output;     
    }         
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        System.out.println(partition3(A));
    }
    
    public static void staticTester(){
        int n = 0;
        int[] a = new int[]{1};
        
        n = 6;
        a = new int[]{1,2,3,4,5,6};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();
        n = 6;
        a = new int[]{6,2,3,4,5,1};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();        
        System.out.println(partition3(a));        
        System.out.println();
        
        n = 4;
        a = new int[]{3,3,3,3};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();
        
        n = 1;
        a = new int[]{40};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();
        n = 11;
        a = new int[]{17,59,34,57,17,23,67,1,18,2,59};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();  
        n = 13;
        a = new int[]{1,2,3,4,5,5,7,7,8,10,12,19,25};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println(); 
        
        n = 5;
        a = new int[]{2,2,2,2,2};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();              
        n = 7;
        a = new int[]{2,2,2,2,2,2,3};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();
        
        n = 5;
        a = new int[]{2,2,2,2,4};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();  
        n = 3;
        a = new int[]{1,1,1};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();
        a = new int[]{2,2,2};
        for (int i = 0; i < a.length; i++){ System.out.print(a[i]+" ");}
        System.out.println();
        System.out.println(partition3(a));        
        System.out.println();
        
    }
}

