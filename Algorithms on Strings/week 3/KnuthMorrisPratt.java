import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

public class KnuthMorrisPratt {
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

    // Find all the occurrences of the pattern in the text and return
    // a list of all positions in the text (starting from 0) where
    // the pattern starts in the text.
    
    public List<Integer> findPattern(String pattern, String text) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        // Implement this function yourself

        String S = pattern+'$'+text;
        //compute prefix function; input to computeprefixfunction is S, not pattern
        int[] s = new int[S.length()];
        int border = 0;
        s[0] = 0;
        for (int i = 1; i < S.length(); i++){
            while ((border > 0) && (S.charAt(i) != S.charAt(border))){//use charAt, not substring
                border = s[border - 1];
            }
            if (S.charAt(i) == S.charAt(border)){
                border++;
            }
            else{
                border = 0;
            }
            s[i] = border;
        }
        /*
        for (int i = 0; i < s.length; i++){
            System.out.print(s[i]+" ");
        }
        */
        //find all occurances
        for (int i = pattern.length() + 1; i < S.length(); i++){
            if (s[i] == pattern.length()){
                result.add(i - 2*pattern.length());
            }
        }
                
        return result;
    }

    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
        //may need to add a test for null
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
    
    public void tester() throws IOException{
        new KnuthMorrisPratt();
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
        String pattern = in.readLine();
        String text = in.readLine();

        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answer = inA.readLine();
        
        // excute the alrogithm        
        List<Integer> ans = findPattern(pattern, text);

        //compare edges with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out edges from algorithm");
        print(ans); 
        System.out.println();
        System.out.println("Print out result from test cases");
        System.out.println(answer);
        System.out.println();
        System.out.println("--------------------");
    }    
}
