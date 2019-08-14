import java.util.*;

public class LCS3 {

    private static int lcs3(int[] a, int[] b, int[] c) {
        //Write your code here
        //Write your code here
        int[][][] lengths = new int[a.length+1][b.length+1][c.length+1];
        for (int i = 1; i <= a.length; i++){
            for (int j = 1; j <= b.length; j++){
                for (int k = 1; k <= c.length; k++){
                    if (a[i-1] == b[j-1] && a[i-1] == c[k-1]){
                        lengths[i][j][k] = lengths[i-1][j-1][k-1]+1;}
                    else {
                        lengths[i][j][k] = Math.max(Math.max(lengths[i][j-1][k],lengths[i-1][j][k]),lengths[i][j][k-1]);}
                }
            }
        }
        return lengths[a.length][b.length][c.length];
        //return Math.min(a.length, b.length);        
        
        
        
        //return Math.min(Math.min(a.length, b.length), c.length);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));
    }
    
    public static void staticTester(){
        int a = 3;
        int b = 3;
        int c = 3;
        int[] aa = new int[]{1,2,3};
        int[] bb = new int[]{2,1,3};
        int[] cc = new int[]{1,3,5};
        for (int i = 0; i < a; i++){
            System.out.print(aa[i]+" ");}
        System.out.println();
        for (int j = 0; j < b; j++){
            System.out.print(bb[j]+" ");
        }
        System.out.println();
        for (int k = 0; k < c; k++){
            System.out.print(cc[k]+" ");
        }
        System.out.println();
        System.out.println(lcs3(aa,bb,cc));
        System.out.println();
        a = 5;
        b = 7;
        c = 6;
        aa = new int[]{8,3,2,1,7};
        bb = new int[]{8,2,1,3,8,10,7};
        cc = new int[]{6,8,3,1,4,7};
        for (int i = 0; i < a; i++){
            System.out.print(aa[i]+" ");}
        System.out.println();
        for (int j = 0; j < b; j++){
            System.out.print(bb[j]+" ");
        }
        System.out.println();
        for (int k = 0; k < c; k++){
            System.out.print(cc[k]+" ");
        }        
        System.out.println();
        System.out.println(lcs3(aa,bb,cc));     
    }    
}

