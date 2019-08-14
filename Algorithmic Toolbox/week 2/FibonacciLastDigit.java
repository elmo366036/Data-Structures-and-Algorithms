import java.util.*;
import java.math.BigInteger;

public class FibonacciLastDigit {
    private static int getFibonacciLastDigitNaive(int n) {
        if (n <= 1)
            return n;

        int previous = 0;
        int current  = 1;

        for (int i = 0; i < n - 1; ++i) {
            int tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
        }

        return current % 10;
    }

    private static int getFibonacciLastDigitFAST(int n){
      if (n <= 1)
        return n;
     
      int[] fib = new int[n+1];
      int result = 0;
      fib[0] = 0;
      fib[1] = 1;
      for (int i=2;i<=n;i++){
          fib[i] = (fib[i-1]+fib[i-2])%10;
          //System.out.println(i+" "+fib[i-1]+" "+fib[i-2]);
        }
        return fib[n];
    }    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        //int d = getFibonacciLastDigitNaive(n);
        int c = getFibonacciLastDigitFAST(n);
        //System.out.println(d);
        System.out.println(c);
    }
}

