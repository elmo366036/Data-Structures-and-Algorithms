
import java.io.*;

import java.util.*;

public class InverseBWT {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();

        // write your code here
        
        //create two lists, one for the bwt and the other for the indexes to it. left column contains pointers to elements within the right column which is equal to bwt.
        List<String> bwtList = new ArrayList<String>();//use String instead of Character. Characters hard to compare
        List<Integer> firstCol = new ArrayList<Integer>();
        for (int i = 0; i < bwt.length(); i++){
            bwtList.add("" + bwt.charAt(i));
            firstCol.add(i);           
        }
        //sort the first col based on the last col. 
        Collections.sort(firstCol, (o1, o2) -> bwtList.get(o1).compareTo(bwtList.get(o2)));
        //begin the iteration through the two columns and do some matching & extracting
        String rightChar;
        int index = firstCol.get(firstCol.get(0));
        for (int i = 1; i <= bwt.length(); i++){
            rightChar = bwtList.get(index);
            result.append(rightChar);
            index = firstCol.get(index);
        }        
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
    
        public void tester() throws IOException{
        new InverseBWT();
        int testCaseStart = 1;
        int testCaseEnd = 2;
        for (int i=testCaseStart; i<=testCaseEnd; i++){
            runTest(i);
        }    
    }

    public void runTest(int testNumber) throws IOException {
        String source = "sample_tests/sample"+testNumber;
        String sourceA = "sample_tests/sample"+testNumber+".a";        
        
        //read source file
        File file = new File(source);
        FileReader filereader = new FileReader(file);
        BufferedReader in = new BufferedReader (filereader);
        String input = in.readLine();

        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answer = inA.readLine();
        
        // excute the alrogithm 
        String result = inverseBWT(input);
        
        //compare edges with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out from algorithm");
        System.out.println(result);        
        System.out.println("Print out of result from test cases");
        System.out.println(answer);
        System.out.println();
        System.out.println("--------------------");
    }   
}
