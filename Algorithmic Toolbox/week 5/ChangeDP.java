import java.util.Scanner;
import java.util.*;

public class ChangeDP {
    private static int getChange(int m) {
        //write your code here
        int[] coins = new int[]{0,1,3,4};
        int numCoins = 0;
        int[] changeCount = new int[m+1];
        changeCount[0]=0;
        for (int i = 1; i <= m; i++){
            //System.out.println("i "+i);
            changeCount[i]=100000; //infinity;
            for (int j = 1; j <= 3; j++){
                //System.out.println("j "+j);
                if (i >= coins[j]) {//concatenate "coin" with j
                    numCoins = changeCount[i-coins[j]] + 1;
                    if (numCoins < changeCount[i]){
                        changeCount[i] = numCoins;
                    }
                }
            }
        }
        
        //test print
        /*
        for (int k = 0; k<=m; k++){
            System.out.print(changeCount[k]+" ");
        }
        */
        return changeCount[m];
        
        }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
    
    public static void staticTester(){
        int m = 2;
        System.out.println(getChange(m));
        m = 34;
        System.out.println(getChange(m));
    }
    
    public static void stressTester(){
        Random rand = new Random();
        int numTestCases = 10;
        int maxMoneySize = 1000;
        for (int i = 1; i <= numTestCases; i++){
            int testAmount = rand.nextInt(maxMoneySize)+1;
            System.out.println("Money Amount "+testAmount+"\t Minimum Number of Coins "+getChange(testAmount));
        }   
        
    }
}

