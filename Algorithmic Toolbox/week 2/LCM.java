import java.util.*;
import java.math.BigInteger;

public class LCM {
  private static long lcm_naive(int a, int b) {
    for (long l = 1; l <= (long) a * b; ++l)
      if (l % a == 0 && l % b == 0)
        return l;

    return (long) a * b;
  }

  private static long lcm_FAST(int a, int b){
      int gcf = gcd_FAST(a,b);
      BigInteger aBig = BigInteger.valueOf(a);
      BigInteger bBig = BigInteger.valueOf(b);
      BigInteger m = aBig.multiply(bBig);
      BigInteger gcfBig = BigInteger.valueOf(gcf);
      BigInteger d = m.divide(gcfBig);
      return d.longValue();     
    }
 
  private static int gcd_FAST(int a, int b){
    if (b==0){return a;}
    int a_prime = a%b;
    return gcd_FAST(b,a_prime);
    }  

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    //System.out.println(lcm_naive(a, b));
    System.out.println(lcm_FAST(a, b));
  }
}
