import java.util.*;

public class FibonacciPartialSum {
    private static long getFibonacciPartialSumNaive(long from, long to) {
        long sum = 0;

        long current = 0;
        long next  = 1;

        for (long i = 0; i <= to; ++i) {
            if (i >= from) {
                sum += current;
            }

            long new_current = next;
            next = next + current;
            current = new_current;
        }

        return sum % 10;
    }
    
    private static long getFibonacciPartialSumFAST(long from, long to) {       
        if (to <= 1)
            return to;
        
        to = (to+2)%60;
        int y = (int) to;
        from = (from+2)%60;
        int z = (int) from;
        long[] fibTo = new long[y+1];
        fibTo[0]=0;
        fibTo[1]=1;              
        long[] fibFrom = new long[z+1];
        fibFrom[0]=0;
        fibFrom[1]=1;  
        long [] fibSumTo = new long[y+1];
        fibSumTo[0]=0;
        fibSumTo[1]=1;
        long [] fibSumFrom = new long[z+1];
        fibSumFrom[0]=0;
        fibSumFrom[1]=1;
      
        
        for (int i = 2; i<=y; i++){
            fibTo[i] = (fibTo[i-1]%10 + fibTo[i-2]%10)%10;
            //System.out.println("i= "+i+"    fib[i]= "+fibTo[i]);
        }

        long a = 0;
        if (y==0) {a=0;}
        if (y==1) {a=1;}
        if (y>1)  {a = fibTo[y-2];}

        //System.out.println ("Last Digit of To is "+a);
        
        for (int k = 2; k<=z; k++){
            fibFrom[k] = (fibFrom[k-1]%10 + fibFrom[k-2]%10)%10;
            //System.out.println("k= "+k+"    fibFrom[k]= "+fibFrom[k]);
        }

        long b = 0;
        if (z==0) {b=0;}
        if (z==1) {b=1;}
        if (z>1)  {b = fibFrom[z-2];} //from
        //System.out.println ("Last Digit of From is "+b);                      
        
        for (int j = 2; j<=y; j++){
            fibSumTo[j] = fibSumTo[j-1] + fibTo[j];
            //System.out.println("j= "+j+"    fibSumTo[j]= "+fibSumTo[j]);
        }

        for (int l = 2; l<=z; l++){
            fibSumFrom[l] = fibSumFrom[l-1] + fibFrom[l];
            //System.out.println("l= "+l+"    fibSumFrom[l]= "+fibSumFrom[l]);
        }        

        
        long c = 0;
        if (y-2 == 0) {c=0;}
        if (y-2 == 1) {c=1;}
        if (y-2 == 2) {c=2;}
        if (y-2 > 2)  {c = fibSumTo[y-2];}

        long d = 0;
        if (z-3 == 0) {d=0;}
        if (z-3 == 1) {d=1;}
        if (z-3 == 2) {d=2;}
        if (z-3 >2)   {d = fibSumFrom[z-3];}
        //System.out.println(c+" "+d);
        return (c - d)%10;
    }       
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        //System.out.println(getFibonacciPartialSumNaive(from, to));
        System.out.println(getFibonacciPartialSumFAST(from,to));
    }
}

