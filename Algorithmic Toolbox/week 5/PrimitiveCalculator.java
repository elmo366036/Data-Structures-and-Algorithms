import java.util.*;

public class PrimitiveCalculator {
    private static List<Integer> optimal_sequenceWRONG(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        while (n >= 1) {
            sequence.add(n);
            if (n % 3 == 0) {
                n /= 3;
            } else if (n % 2 == 0) {
                n /= 2;
            } else {
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }

    private static List<Integer> build_output_sequence(List<String> sequenceOfOperations, int n){
        List<Integer> sequence_output = new ArrayList<Integer>();
        int operationsSize = sequenceOfOperations.size() - 1;
        sequence_output.add(n);
        while (operationsSize>1){
            //System.out.println("n "+n);
            String currentOperation = sequenceOfOperations.get(operationsSize);
            int nextValue = 0;
            if (currentOperation == "A"){
                //minus 1
                n = n - 1;
                operationsSize = operationsSize - 1;
            }
            else if (currentOperation == "2"){
                //divide 2
                n = n / 2;
                operationsSize = operationsSize / 2;
            }
            else if (currentOperation == "3"){
                // divide 3
                n = n / 3;
                operationsSize = operationsSize / 3;
            }
            sequence_output.add(n);
        }        
        Collections.reverse(sequence_output);
        return sequence_output;
    }
    
    private static List<String> optimal_sequence_operation_array(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        List<String> sequenceOfOperations = new ArrayList<String>();
        int[] operations = new int[]{0,1,2,3};
        sequence.add(0);
        sequence.add(1);        
        //sequence.add(1);        
        //sequence.add(1);
        sequenceOfOperations.add("0");
        sequenceOfOperations.add("A");
        //sequenceOfOperations.add("2");
        //sequenceOfOperations.add("3");
        for (int i = 2; i <= n; i++){
            int multThree = 1500000;
            int multTwo = 1500000;
            int addOne = 1500000;
            if (i % 3 == 0){
                multThree = sequence.get(i/3)+1;
            }
            if (i % 2 == 0){
                multTwo = sequence.get(i/2)+1;
            }
            addOne = sequence.get(i-1) + 1;
            int minimum = 0;
            minimum = multThree;
            if (minimum > multTwo){minimum = multTwo;}
            if (minimum > addOne){minimum = addOne;}
            int nextDigit = 0;
            String nextOperation = "";
            if (minimum == multThree){
                nextDigit = multThree;
                nextOperation = "3";}
            else if (minimum == multTwo){
                nextDigit = multTwo;
                nextOperation = "2";}
            else if (minimum == addOne){
                nextDigit = addOne;
                nextOperation = "A";}
            sequence.add(minimum);
            sequenceOfOperations.add(nextOperation);
        }
        return sequenceOfOperations;
    }    
    
    private static List<Integer> optimal_sequence_array(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        int[] operations = new int[]{0,1,2,3};
        sequence.add(0);
        sequence.add(1);
        //sequence.add(1);        
        //sequence.add(1);         
        for (int i = 2; i <= n; i++){
            int multThree = 1500000;
            int multTwo = 1500000;
            int addOne = 1500000;
            if (i % 3 == 0){
                multThree = sequence.get(i/3)+1;
            }
            if (i % 2 == 0){
                multTwo = sequence.get(i/2)+1;
            }
            addOne = sequence.get(i-1) + 1;
            int minimum = 0;
            minimum = multThree;
            if (minimum > multTwo){minimum = multTwo;}
            if (minimum > addOne){minimum = addOne;}
            int nextDigit = 0;
            if (minimum == multThree){nextDigit = multThree;}
            else if (minimum == multTwo){nextDigit = multTwo;}
            else if (minimum == addOne){nextDigit = addOne;}
            sequence.add(minimum);
        }
        return sequence;
    }    
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        //List<Integer> sequence = optimal_sequence_array(n);
        List<String> sequence_array_operations = optimal_sequence_operation_array(n);
        List<Integer> sequence_array_output = build_output_sequence(sequence_array_operations, n);
        System.out.println(sequence_array_output.size() - 1);
        for (Integer x : sequence_array_output) {
            System.out.print(x + " ");
        }
    }
    
    public static void staticTester(){
        int a = 1;
        List<Integer> sequence_array = optimal_sequence_array(a);
        List<String> sequence_array_operations = optimal_sequence_operation_array(a);
        List<Integer> sequence_array_output = build_output_sequence(sequence_array_operations, a);
        System.out.println("Input: "+a);
        System.out.println("Output: "+(sequence_array.get(a) - 1));
        for (Integer x : sequence_array) {
            System.out.print(x + " ");
        }
        System.out.println();
        for (String s : sequence_array_operations) {
            System.out.print(s + " ");
        }
        
        System.out.println();
        a = 2;
        sequence_array = optimal_sequence_array(a);
        sequence_array_operations = optimal_sequence_operation_array(a);
        sequence_array_output = build_output_sequence(sequence_array_operations, a);
        System.out.println("Input: "+a);
        System.out.println("Output: "+(sequence_array.get(a) - 1));
        for (Integer x : sequence_array) {
            System.out.print(x + " ");
        }
        System.out.println();
        for (String s : sequence_array_operations) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (Integer y : sequence_array_output) {
            System.out.print(y + " ");
        }
        
        System.out.println();
        a = 3;
        sequence_array = optimal_sequence_array(a);
        sequence_array_operations = optimal_sequence_operation_array(a);
        sequence_array_output = build_output_sequence(sequence_array_operations, a);
        System.out.println("Input: "+a);
        System.out.println("Output: "+(sequence_array.get(a) - 1));
        for (Integer x : sequence_array) {
            System.out.print(x + " ");
        }
        System.out.println();
        for (String s : sequence_array_operations) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (Integer y : sequence_array_output) {
            System.out.print(y + " ");
        }
        
    }
    
    public static void stressTester(){
        Random random = new Random();
        int a = 1;
        int testSize = 100;
        int maxNumSize = 1000000;
        
        for (int i = 1; i <= testSize; i++){
            int aa = random.nextInt(maxNumSize)+1;
            List<Integer> sequence_array = optimal_sequence_array(aa);
            List<String> sequence_array_operations = optimal_sequence_operation_array(aa);
            List<Integer> sequence_array_output = build_output_sequence(sequence_array_operations, aa);
            boolean pass = true;
            if ((sequence_array.get(aa) - 1) != (sequence_array_output.size() - 1)){pass = false;};
            if (!pass){
                System.out.println("Failed Test Case ("+i+" out of "+testSize);
                System.out.println("Input: "+aa);
                System.out.println("Output: "+(sequence_array.get(aa) - 1));
                for (Integer x : sequence_array) {
                    System.out.print(x + " ");
                }
                System.out.println();
                for (String s : sequence_array_operations) {
                    System.out.print(s + " ");
                }
                System.out.println();
                for (Integer y : sequence_array_output) {
                    System.out.print(y + " ");
                }
            }   
        }
        System.out.println("TEST COMPLETE");
        
    }
}

