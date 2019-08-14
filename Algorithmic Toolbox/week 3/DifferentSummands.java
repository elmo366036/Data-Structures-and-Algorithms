import java.util.*;

public class DifferentSummands {
    public static ArrayList<Integer> optimalSummands(int n) {
        ArrayList<Integer> summands = new ArrayList<Integer>();
        //write your code here
        int N = new Integer(n);
        int m = new Integer(1);
        
        //System.out.println("N= "+N+"\t m="+m);
        
        if (N==1 || N==2 || N==0){
            summands.add(N);
            return summands;}
        
        for (int i = 1;i < N; i++){
            if (2*m < N){
                summands.add(m);
                N-=m;
                m+=1;}
            if (2*m >= N){
                summands.add(N);
                break;}
        }
        //System.out.println("Size: "+summands.size());
        return summands;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        //System.out.println("n1 "+n);
        ArrayList<Integer> summands = new ArrayList<Integer>();
        //System.out.println("n2 "+n);
        summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
    
    public static void tester(){
        int n = 4;
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }        
    }
}

