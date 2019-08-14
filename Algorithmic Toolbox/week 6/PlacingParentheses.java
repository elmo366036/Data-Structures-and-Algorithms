import java.util.*;

public class PlacingParentheses {
    private static long getMaximValue(String exp) {
      //write your code here

      //need to iterate through string
      //create two array lists, one of digits and the other of operations
      // d.size() = n = number of digits in string
      ArrayList<Character> op = new ArrayList<Character>();
      ArrayList<Integer> d = new ArrayList<Integer>();        
      for (int i = 0; i < exp.length(); i++){
          char c = exp.charAt(i);
          String num = "";
          if (c == '+'){op.add(c);}
          else if (c == '-'){op.add(c);}
          else if (c == '*'){op.add(c);}
          //assume now c is a number from 0 - 9 
          else if (c != '+' || c != '-' || c != '*'){d.add(Character.getNumericValue(c));}          
        }      
      //create and initialize Max and Min matrices
      long[][] Max = new long[d.size()+1][d.size()+1];
      long[][] Min = new long[d.size()+1][d.size()+1];          
      // fill in first diagonal of Max and Min, also initialize top row and 1st col to 0
      for (int i = 0; i <= d.size(); i++){
          Max[0][i] = 0;
          Max[i][0] = 0;
          Min[0][i] = 0;
          Min[i][0] = 0;
          if (i > 0){
              Max[i][i] = d.get(i-1);
              Min[i][i] = d.get(i-1);
            }
        }
      //printMatrix(Max);
      //printMatrix(Min);
      //check OK
      //run second half of parentheses algorithm. call Min Max and pass   
      int j = 0;  
      for (int s = 1; s <= d.size() - 1; s++){
          for (int i = 1; i <= d.size() - s; i++){
              j = i + s;
              long[] minMax = new long[2];
              minMax = MinANDMax(i,j,Min,Max,op);
              Max[i][j] = minMax[1];
              Min[i][j] = minMax[0];
            }
        }                 
      //printMatrix(Max);
      //printMatrix(Min);          
      return Max[1][d.size()];
    }

    public static long[] MinANDMax(int i, int j, long[][] Min, long[][] Max, ArrayList<Character> op){
        long[] result = new long[2];
        long min = 1000000;
        long max = -1000000;
        //call eval
        for (int k = i; k <= j-1; k++){
           long a = eval(Max[i][k],Max[k+1][j],op.get(k-1));
           long b = eval(Max[i][k],Min[k+1][j],op.get(k-1));
           long c = eval(Min[i][k],Max[k+1][j],op.get(k-1));
           long d = eval(Min[i][k],Min[k+1][j],op.get(k-1));
           long[] minArray = new long[]{min,a,b,c,d};
           long[] maxArray = new long[]{max,a,b,c,d};
           Arrays.sort(minArray);
           Arrays.sort(maxArray);
           min = minArray[0];
           max = maxArray[4];
           }
        result[0] = min;
        result[1] = max;
        return result;
    }    
    
    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    private static void printMatrix(long[][] matrix){
        System.out.println(Arrays.deepToString(matrix));
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
    
    public static void staticTester(){
        String exp = "1+5";
        System.out.println("expression: "+exp);
        System.out.println(getMaximValue(exp));
        
        exp = "5-8+7*4-8+9";
        System.out.println("expression: "+exp);
        System.out.println(getMaximValue(exp));
        
    }
}

