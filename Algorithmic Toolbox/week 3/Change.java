import java.util.Scanner;
import java.util.Random;

public class Change {
    private static int getChange(int m) {
        int dime = 10;
        int nickle = 5;
        int penny = 1;
        
        int numDimes = new Integer(m/dime);
        if (numDimes >= 1) {
            m = m%dime;}
            
        int numNickles = new Integer(m/nickle);
        if (numNickles >= 1) {
            m = m%nickle;}
            
        int numPennies = new Integer(m);  
        //System.out.println("dimes="+numDimes+"   nickles="+numNickles+"   pennies="+numPennies);
         
        return numDimes+numNickles+numPennies;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
        //tester();

    }
    
    public static void tester(){
        System.out.println("2 cents -> "+getChange(2)+" coins");
        System.out.println("");
        System.out.println("28 cents -> "+getChange(28)+" coins");
        Random rand = new Random();
        
        for (int i = 0; i<10; i++){
            int n = rand.nextInt(500);
            System.out.println(n+" cents -> "+getChange(n)+" coins");
        }
        
    }
}

