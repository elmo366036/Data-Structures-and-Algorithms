import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixArrayLong {
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

    int letterToIndex (char letter)
    {
        switch (letter)
        {
            case 'A': return 1;//set from 0 to 1
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            case '$': return 0; //set $ to 0 since it comes first
            default: throw new IllegalArgumentException();
        }
    }    
    
    private int[] sortCharacters(String s){
        int[] order = new int[s.length()];
        int[] count = new int[5]; 
        for (int i = 0; i < s.length(); i++){        
            count[letterToIndex(s.charAt(i))]++; 
                //in the algorithm, need to covert char c from s into an index
                //use the mapping from earlier problems
        }
        for (int j = 1; j < count.length; j++){
            count[j] += count[j - 1];
        }
        for (int k = s.length() - 1; k >= 0; k--){
            count[letterToIndex(s.charAt(k))]--;
            order[count[letterToIndex(s.charAt(k))]] = k;
        }
        return order;
    }

    private int[] computeCharClasses(String s, int[] order){
        int[] clss = new int[s.length()];
        clss[order[0]] = 0;
        for (int i = 1; i < s.length(); i++){
            if (s.charAt(order[i]) != s.charAt(order[i - 1])){
                clss[order[i]] = clss[order[i - 1]] + 1;
            }
            else {
                clss[order[i]] = clss[order[i - 1]];
                
            }
        }
        return clss;
    }

    private int[] sortDoubled(String s, int L, int[] order, int[] clss){
        int[] count = new int[s.length()];
        int[] newOrder = new int[s.length()];
        for (int i = 0; i < s.length(); i++){
            count[clss[i]]++; 
        }   
        for (int j = 1; j < s.length(); j++){
            count[j] += count[j - 1];
        }
        for (int k = s.length() - 1; k >= 0; k--){
            int start = (order[k] - L + s.length()) % s.length();
            int cl = clss[start];
            count[cl]--;
            newOrder[count[cl]] = start;
        }
        return newOrder;
    }
    
    private int[] updateClasses(int[] newOrder, int[] clss, int L){
        int n = newOrder.length;
        int[] newClss = new int[n];
        newClss[newOrder[0]] = 0;
        for (int i = 1; i < n; i++){
            int cur = newOrder[i];
            int prev = newOrder[i - 1];
            int mid = cur + L;
            int midPrev = (prev + L) % n;
            if ((clss[cur] != clss[prev]) || (clss[mid] != clss[midPrev])){
                newClss[cur] = newClss[prev] + 1;
            }
            else {
                newClss[cur] = newClss[prev];
            }
        }
        return newClss;
    }
    
    public int[] computeSuffixArray(String text) {
        int[] result = new int[text.length()];

        // write your code here
        int[] order = sortCharacters(text);
        print(order);
        int[] clss = computeCharClasses(text, order);
        print(clss);
        int L = 1;
        while (L < text.length()){
            order = sortDoubled(text, L, order, clss);
            clss = updateClasses(order, clss, L);
            L *= 2;
        }
        print(order);
        print(clss);
        return order;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
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
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
    
    public void tester() throws IOException{
        new SuffixArrayLong();
        int testCaseStart = 1;
        int testCaseEnd = 4;
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
