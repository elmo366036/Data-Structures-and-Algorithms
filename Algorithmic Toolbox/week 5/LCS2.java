import java.util.*;

public class LCS2 {

    private static int lcs2(int[] a, int[] b) {
        //Write your code here
        int[][] lengths = new int[a.length+1][b.length+1];
        for (int i = 1; i <= a.length; i++){
            for (int j = 1; j <= b.length; j++){
                if (a[i-1] == b[j-1]){
                    lengths[i][j] = lengths[i-1][j-1]+1;}
                else {
                    lengths[i][j] = Math.max(lengths[i][j-1],lengths[i-1][j]);}
            }
        }
        return lengths[a.length][b.length];
        //return Math.min(a.length, b.length);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = scanner.nextInt();
        }

        System.out.println(lcs2(a, b));
    }
    
    public static void staticTester(){
        int a = 3;
        int b = 2;
        int[] aa = new int[]{2,7,5};
        int[] bb = new int[]{2,5};
        for (int i = 0; i < a; i++){
            System.out.print(aa[i]+" ");}
        System.out.println();
        for (int j = 0; j < b; j++){
            System.out.print(bb[j]+" ");
        }        
        System.out.println();
        System.out.println(lcs2(aa,bb));
        System.out.println();
        a = 1;
        b = 4;
        aa = new int[]{7};
        bb = new int[]{1,2,3,4};
        for (int i = 0; i < a; i++){
            System.out.print(aa[i]+" ");}
        System.out.println();
        for (int j = 0; j < b; j++){
            System.out.print(bb[j]+" ");
        }        
        System.out.println();
        System.out.println(lcs2(aa,bb));
        System.out.println();
        a = 4;
        b = 4;
        aa = new int[]{2,7,8,3};
        bb = new int[]{5,2,8,7};
        for (int i = 0; i < a; i++){
            System.out.print(aa[i]+" ");}
        System.out.println();
        for (int j = 0; j < b; j++){
            System.out.print(bb[j]+" ");
        }        
        System.out.println();
        System.out.println(lcs2(aa,bb));         
    }
}

