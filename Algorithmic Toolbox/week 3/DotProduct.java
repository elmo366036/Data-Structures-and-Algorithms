import java.util.*;

public class DotProduct {
    private static long maxDotProduct(int[] a, int[] b) {
        //write your code here
        //long result = 0;
        long maxTotalProfit = 0;        
        ArrayList<Integer> profits = new ArrayList<Integer>();
        ArrayList<Integer> clicks = new ArrayList<Integer>();
        for (int i=0;i<a.length;i++){
            profits.add(a[i]);
            clicks.add(b[i]);
            //System.out.println(profits[i]+" "+clicks[i]);
        }       
        Collections.sort(profits,Collections.reverseOrder());
        Collections.sort(clicks,Collections.reverseOrder());       

        /*
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
          */
         
        int j = 0;
        while (j < a.length){
            int profitsMax = profits.get(j);
            int clicksMax = clicks.get(j);
            //System.out.println("profits "+profitsMax+"\t clicks "+clicksMax);
            maxTotalProfit+=((double) profitsMax * (double) clicksMax);
            j+=1;
        }
        return maxTotalProfit;
    }

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(maxDotProduct(a, b));
        
        //tester();
    }
    
    public static void tester(){
    int n = 1;
    int[] p = new int[] {23};
    int[] c = new int[] {39};
    System.out.println(maxDotProduct(p,c));
    n = 3;
    p = new int[] {1, 3, -5};
    c = new int[] {-2, 4, 1};
    System.out.println(maxDotProduct(p,c));
    
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

