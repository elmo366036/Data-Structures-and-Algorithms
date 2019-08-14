import java.util.*;

class EditDistance {
  public static int EditDistance(String s, String t) {    
    int sLength = s.length();
    int tLength = t.length();
    int[][] Distance = new int[sLength+1][tLength+1];
    
    //initialize distance matrix
    for (int i = 0; i <= sLength; i++){
        Distance[i][0] = i;}
    for (int j = 0; j <= tLength; j++){
        Distance[0][j] = j;}
        
    //printMatrix(Distance, sLength, tLength);
    int matchCount = 0;
    //compute Distance matrix
    for (int j = 1; j <= tLength; j++){
        for (int i = 1; i <= sLength; i++){
            int Insert = Distance[i][j-1]+1;
            int Delete = Distance[i-1][j]+1;
            int Match  = Distance[i-1][j-1];
            int MisMat = Distance[i-1][j-1]+1;
            if (s.charAt(i-1) == t.charAt(j-1)){
                Distance[i][j] = threeWayMinimum(Insert,Delete,Match);
                    }
            else {
                Distance[i][j] = threeWayMinimum(Insert,Delete,MisMat);}
        }
    }
    //printMatrix(Distance, sLength, tLength);
    return Distance[sLength][tLength];
  }

  private static int threeWayMinimum(int a, int b, int c){
      int minimum = c;
            if (minimum > b){minimum = b;}
            if (minimum > a){minimum = a;}
      return minimum;
    }
  
  private static void printMatrix(int[][] matrix, int sLength, int tLength){
      for (int i = 0; i <= sLength; i++){
          System.out.println();
          for (int j = 0; j <= tLength; j++){
              System.out.print(matrix[i][j]+" ");
            }
        }
      System.out.println();
  }
    
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(EditDistance(s, t));
  }

  public static void staticTester(){
      String s = "ab";
      String t = "ab";
      System.out.println(EditDistance(s, t));
      
      s="short";
      t="ports";
      System.out.println(s+" "+t);
      System.out.println(EditDistance(s, t));
      
      s="editing";
      t="distance";
      System.out.println(EditDistance(s, t));
      
      s="2783";
      t="5287";
      System.out.println(s+" "+t);
      System.out.println(EditDistance(s, t));
    }
}
