import java.util.*;

public class Knapsack {
    static int optimalWeight(int W, int[] w) {
        //write you code here
        int numOfWeights = w.length;
        //System.out.println(numOfWeights);
        int[][] values = new int[numOfWeights+1][W+1];
        // w(i) = v(i) because 
        for (int k = 0; k <= W; k++){
            values[0][k] = 0;}
        for (int k = 0; k <= numOfWeights; k++){
            values[k][0] = 0;}
        
        for (int i = 1; i <= numOfWeights; i++){
            for (int j = 1; j <= W; j++){
                values[i][j] = values[i-1][j];
                if (w[i-1] <= j) { // if the weight of i is less than j
                   int val = values[i-1][j-w[i-1]] + w[i-1]; // w[i-1] = v[i-1] bc w=v
                   if (values[i][j] < val){
                       values[i][j] = val;
                    }
                }
            }               
        }
        return values[numOfWeights][W];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
    
    public static void staticTester(){
        int W = 10;
        int n = 3;
        int[] weights = new int[]{1,4,8};
        
        System.out.println(optimalWeight(W, weights));
    }
}

