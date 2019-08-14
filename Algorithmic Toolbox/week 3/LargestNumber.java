import java.util.*;

public class LargestNumber {
    private static String largestNumber(String[] a) {
        //write your code here
        
        //convert string array to ArrayList of integers
        ArrayList<Integer> digits = new ArrayList<Integer>();
        //for (String s:a){    
        for (int k=0;k<a.length;k++){    
            int stringDigit = Integer.parseInt(a[k]);
            //System.out.println("k "+k+"\t digit "+stringDigit);
            digits.add(stringDigit);
        }
        
        //perform the calculation        
        String result = "";

        while (digits.size() > 0){
            int maxDigit = -1;
            int maxDigitIndex=0;
            for (int i = 0;i<digits.size();i++){
                if (isGreaterOrEqual(digits.get(i),maxDigit)){
                    maxDigit = digits.get(i);
                    maxDigitIndex = i;
                }
            }
            result = result + maxDigit;
            digits.remove(maxDigitIndex);
        }

        return result;
    }
    
    private static boolean isGreaterOrEqual(int digit, int maxDigit){
        String digitString = Integer.toString(digit);
        String maxDigitString = Integer.toString(maxDigit); 
        
        if (maxDigit < 0){return true;}
        
        // check if the number of digits is the same. If so, direct comparison
        if (digitString.length() == maxDigitString.length()){
               if (digit >= maxDigit) {return true;}
               else {return false;}
            }
        
        String AB = digitString+maxDigitString;
        String BA = maxDigitString+digitString;
        
        if (Integer.parseInt(AB) > Integer.parseInt(BA)){return true;}
        else {return false;}

        }    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
    
    public static void tester(){
        int n = 4;
        
        String[] a = {"90", "384", "557","991"}; 
        System.out.println(largestNumber(a));
        
        n = 3;
        String[] b = {"26", "249", "248"}; 
        System.out.println(largestNumber(b)); 
        
        n = 4;
        String[] c = {"888", "8", "732", "75"}; 
        System.out.println(largestNumber(c));
        
        String[] d = {"1000", "11", "101", "10"}; 
        System.out.println(largestNumber(d));
        

        String[] e = {"723", "72"}; 
        System.out.println(largestNumber(e));
        
        Random rand = new Random(30);
        Scanner scanner = new Scanner(System.in);
        
        int runtime = 100;
        int num = 0;
        for (int j=0;j<runtime;j++){            
            //n = rand.nextInt(4)+1;
            n = 2;
            String[] test = new String[n];
            //System.out.println("");
            System.out.print("Input "+j+": ");
            for (int i = 0; i < n; i++) {
                num = rand.nextInt(999)+1; 
                test[i] = Integer.toString(num);
                System.out.print(test[i]+" ");
            }
            System.out.println("");
            //for (int l = 0;l<test.length;l++){
                //System.out.println("length "+test.length+"\t value: "+test[l]);}
            System.out.println(largestNumber(test));
        }                       
    }
}

