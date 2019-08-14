import java.util.*;
import java.math.BigInteger;

public class FibonacciHuge {
    private static long getFibonacciHugeNaive(long n, long m) {
        if (n <= 1)
            return n;
               
        BigInteger nBig = BigInteger.valueOf(n);
        BigInteger mBig = BigInteger.valueOf(m);            
        BigInteger previous = BigInteger.valueOf(0);
        BigInteger current = BigInteger.valueOf(1);

        for (long i = 0; i < n - 1; ++i) {
            BigInteger tmp_previous = previous;
            previous = current;
            current = tmp_previous.add(current);
        }
        
        BigInteger result = current.mod(mBig);
        return result.longValue();        
 
    }        

    private static int getPisanoPeriod(long m){
        long a = 0;
        long b = 1;
        long c = a + b;
        for (int i = 0; i < m * m; i++) {
            c = (a + b) % m;
            a = b;
            b = c;
            if (a == 0 && b == 1){ return i + 1;}    
        }
        return 0;
    }    
    
    private static long getFibonacciHugeFAST(long n, long m){
        long ppl = getPisanoPeriod(m);
        //System.out.println(ppl);
        
        return getFibonacciHugeNaive(n % ppl,m);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        //System.out.println(getFibonacciHugeNaive(n, m));
        System.out.println(getFibonacciHugeFAST(n, m));
    }
}

