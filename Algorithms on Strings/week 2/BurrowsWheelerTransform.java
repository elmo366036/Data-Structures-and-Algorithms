import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

public class BurrowsWheelerTransform {
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

    String BWT(String text) {
        StringBuilder result = new StringBuilder();//this is a BIG hint. Use StringBuilder to manipulate strings

        // write your code here        
        int lastCharIndex = text.length();//
        StringBuilder line = new StringBuilder(text);//this will be used to rotate the text for each line in the BWT matrix
        TreeSet<String> t = new TreeSet<String>();//use TreeSet to store the string. It will sort it in natural order

        //creates the rotated text and store it in a TreeSet. 
        for (int i = 0; i < text.length(); i++){
            //System.out.println(line);
            t.add(line.toString());//store
            char lastChar = line.charAt(lastCharIndex-1);//then identify last char
            line.insert(0,lastChar);//mode it to front
            line.delete(lastCharIndex, lastCharIndex+1);//delete the one that was just moved
        }        

        //System.out.println(t);        

        //pop (pollFirst) the nodes from TreeSet and take the last char. Append this to result. This is sorted in natural order
        while (!t.isEmpty()){
            String BWTline = t.pollFirst();
            char BWTChar = BWTline.charAt(lastCharIndex-1);//get the last char per line
            result.append(BWTChar);
        }
        
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
    
    public void tester() throws IOException{
        new BurrowsWheelerTransform();
        int testCaseStart = 1;
        int testCaseEnd = 3;
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
        String result = BWT(input);
        
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
