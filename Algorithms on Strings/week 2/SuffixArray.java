import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;


public class SuffixArray {
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

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }
    
    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    
      

    public int[] computeSuffixArray(String text){
        int[] suffixArray = new int[text.length()];
        TreeMap<String, Integer> suffixMap = new TreeMap<String, Integer>();
        for (int i = text.length() - 1; i >= 0; i--){
            String suffix = text.substring(i, text.length());
            suffixMap.put(suffix, i);            
        }
        
        int[] result = new int[suffixMap.size()];
        int index = 0;
        for (Map.Entry<String, Integer> entry : suffixMap.entrySet()){
            //System.out.println(entry.getKey()+" \t \t "+entry.getValue());
            result[index] = entry.getValue();
            index++;
        }
        
        return result;
    }   

    static public void main(String[] args) throws IOException {
        new SuffixArray().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] SuffixArray = computeSuffixArray(text);
        print(SuffixArray);
    }
    
    public void tester() throws IOException{
        new SuffixArray();
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
        int[] ans = computeSuffixArray(input);

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
