import java.util.Scanner;

public class Fibonacci {
  private static long calc_fib(int n) {
    if (n <= 1)
      return n;

    return calc_fib(n - 1) + calc_fib(n - 2);
  }

  private static long calc_fibFAST(int n){
      if (n==0){return 0;}      
      int[] fib = new int[n+1];
      long result = 0;
      fib[0] = 0;
      fib[1] = 1;
      for (int i=2;i<=n;i++){
          fib[i] = fib[i-1]+fib[i-2];
          //System.out.println(i+" "+fib[i-1]+" "+fib[i-2]);
        }
      result = fib[n];  
      return result;
    }
  
  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    //System.out.println(calc_fib(n));
    System.out.println(calc_fibFAST(n));
  }
}
