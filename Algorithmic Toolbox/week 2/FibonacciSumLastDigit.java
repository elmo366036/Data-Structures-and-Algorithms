import java.util.*;


public class FibonacciSumLastDigit {
    private static long getFibonacciSumNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current  = 1;
        long sum      = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
            sum += current;
        }

        return sum % 10;
    }
    
    private static long getFibonacciSumFast(long n){
        if (n <= 1)
            return n;
        
        n = (n+2)%60;
        int y = (int) n;
        long[] fib = new long[y+1];
        fib[0]=0;
        fib[1]=1;
        int res = 1;
        for (int i = 2; i<=y; i++){
            fib[i] = (fib[i-1]%10 + fib[i-2]%10)%10;
            res+=fib[i];
        }
        //System.out.println(res);
        //System.out.println(fib[y-1]);
        if (fib[y] == 0) {return 9;}
        return ((fib[y]%10)-1);

    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        //long s = getFibonacciSumNaive(n);
        long y = getFibonacciSumFast(n);
        //System.out.println(s);
        System.out.println(y);
    }
}

