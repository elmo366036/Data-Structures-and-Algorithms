import java.util.Scanner;
import java.util.*;


public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        int n = values.length;
        if (n==0) {return 0;}
        Double[] ratiosList = new Double[n];
        for (int i = 0; i < n; i++){
            if (values[i]==0 || weights[i]==0) {
                ratiosList[i] = 0.0;
            }
            else {ratiosList[i] = (double) values[i] / (double) weights[i];}
        }
        //System.out.println("ratioList "+ratiosList[0]);
        
        while (capacity > 0 && Collections.max(Arrays.asList(ratiosList)) != 0){
            Double maxRatio = Collections.max(Arrays.asList(ratiosList));
            int maxRatioIndex = Arrays.asList(ratiosList).indexOf(maxRatio);           
            
            int quotient = new Integer(capacity / weights[maxRatioIndex]);
            int remain = new Integer(capacity % weights[maxRatioIndex]);
            
            if (quotient >= 1){
                value += values[maxRatioIndex];
                capacity -= weights[maxRatioIndex];                
            } 
            
            if (quotient == 0){
                double quotientDouble = new Double((double) capacity / (double) weights[maxRatioIndex]);
                value += values[maxRatioIndex] * quotientDouble;
                capacity -= weights[maxRatioIndex] * quotientDouble;  
            }
            /*
            System.out.println("current value "+value);
            System.out.println("current capacity "+capacity);*/
            ratiosList[maxRatioIndex] = 0.0;    
        }
        return value;
    }

    public static void main(String args[]) {
       
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.println(getOptimalValue(capacity, values, weights));
        
        //tester();
    }
    
    public static void tester(){
        
        int capacity = 50;
        int[] values = new int[] {60, 100, 120};
        int[] weights = new int[] {20, 50, 30};            
        /*
        System.out.println(getOptimalValue(capacity, values, weights));
       
        capacity = 10;
        values = new int[] {500};
        weights = new int[] {30};            
        System.out.println(getOptimalValue(capacity, values, weights));
        
        capacity = 1000;
        values = new int[] {500};
        weights = new int[] {30};            
        System.out.println(getOptimalValue(capacity, values, weights));
        */
        capacity = 16;
        values = new int[] {460};
        weights = new int[] {777};            
        System.out.println(getOptimalValue(capacity, values, weights));
        /*
        Random rand = new Random(50);
        
        for (int i=1;i<20;i++){
            capacity = rand.nextInt(1000);
            int n = rand.nextInt(5);
            values = new int[n];
            weights = new int[n];
            for (int j=0;j<n;j++){
                values[j] = rand.nextInt(1000);
                weights[j] = rand.nextInt(1000);}
            System.out.println("Capacity "+capacity+"\t n "+n+"\t values "+Arrays.toString(values)+"\t weights "+Arrays.toString(weights));
            System.out.println("Capacity "+capacity+"\t     Max Value "+getOptimalValue(capacity,values,weights));
        }
        */
}   
} 
