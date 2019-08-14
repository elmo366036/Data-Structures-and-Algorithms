
import java.io.*;

import java.util.*;


public class SuffixArrayMatching {
    class fastscanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        fastscanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        fastscanner(String source) throws IOException{
            File file = new File(source);
            FileReader filereader = new FileReader(file);
            in = new BufferedReader (filereader);            
        }
        
        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextint() throws IOException {
            return Integer.parseInt(next());
        }
    }

    //from SA_Long
    
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
        int[] clss = computeCharClasses(text, order);
        int L = 1;
        while (L < text.length()){
            order = sortDoubled(text, L, order, clss);
            clss = updateClasses(order, clss, L);
            L *= 2;
        }
        return order;
    }    
    
    public List<Integer> findOccurrences(String pattern, String text, int[] suffixArray) {
        List<Integer> result = new ArrayList<Integer>();

        // write your code here
        int minIndex = 0;
        int maxIndex = text.length();
        while (minIndex < maxIndex){
            int midIndex = (minIndex + maxIndex) / 2;
            String suffix = text.substring(suffixArray[midIndex], Math.min(suffixArray[midIndex] + pattern.length(), text.length()));
                //suffix of Text starting at position SUFFIXARRAY(midIndex)
                //suffix ends at the smaller of the start position + pattern.length or the end of text
            if (pattern.compareTo(suffix) > 0){
                //if pattern > suffix of Text starting at position SUFFIXARRAY(midIndex)
                minIndex = midIndex + 1;
            }
            else {
                maxIndex = midIndex;
            }
        }
        int start = minIndex;
        maxIndex = text.length();
        while (minIndex < maxIndex){
            int midIndex = (minIndex + maxIndex) / 2;
            String suffix = text.substring(suffixArray[midIndex], Math.min(suffixArray[midIndex] + pattern.length(), text.length()));
            if (pattern.compareTo(suffix) < 0 ) {
                maxIndex = midIndex;
            }
            else {
                minIndex = midIndex + 1;
            }
        }
        int end = maxIndex;
        if (start <= end){ //this is simplier than   if (start > end) {return 0}   else {append result array} as stated in lecture
            for (int i = start; i < end; i++){
                result.add(suffixArray[i]);
                //System.out.println("start "+start+"   end "+end+" \t i "+i+" suffixArray[] "+suffixArray[i]);
            }
        }
        return result;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayMatching().run();
    }

    public void print(boolean[] x) {
        for (int i = 0; i < x.length; ++i) {
            if (x[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public void run() throws IOException {
        fastscanner scanner = new fastscanner();
        String text = scanner.next() + "$";
        int[] suffixArray = computeSuffixArray(text);
        int patternCount = scanner.nextint();
        boolean[] occurs = new boolean[text.length()];
        for (int patternIndex = 0; patternIndex < patternCount; ++patternIndex) {
            String pattern = scanner.next();
            List<Integer> occurrences = findOccurrences(pattern, text, suffixArray);
            for (int x : occurrences) {
                occurs[x] = true;
            }
        }
        print(occurs);
    }
    
     public void tester() throws IOException{
        new SuffixArrayMatching();
        int testCaseStart = 1;
        int testCaseEnd = 3;
        for (int i=testCaseStart; i<=testCaseEnd; i++){
            runTest(i);
        }    
    }

    public void runTest(int testNumber) throws IOException {
        String source = "sample_tests/sample"+testNumber;
        String sourceA = "sample_tests/sample"+testNumber+".a";        
        
        //read source file & process        
        fastscanner scanner = new fastscanner(source);
        String text = scanner.next() + "$";
        int[] suffixArray = computeSuffixArray(text);
        int patternCount = scanner.nextint();
        boolean[] occurs = new boolean[text.length()];
        for (int patternIndex = 0; patternIndex < patternCount; ++patternIndex) {
            String pattern = scanner.next();
            List<Integer> occurrences = findOccurrences(pattern, text, suffixArray);
            for (int x : occurrences) {
                occurs[x] = true;
            }
        }        
        
        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answer = inA.readLine();

        //compare occurances with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out starting points from algorithm");
        print(occurs); 
        System.out.println();
        System.out.println("Print out result from test cases");
        System.out.println(answer);
        System.out.println();
        System.out.println("--------------------");
    }    
}
