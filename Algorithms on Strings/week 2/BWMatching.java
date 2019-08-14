
import java.io.*;

import java.util.*;

public class BWMatching {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        FastScanner(String source) throws IOException{
            File file = new File(source);
            FileReader fileReader = new FileReader(file);
            in = new BufferedReader(fileReader);              
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

    // Preprocess the Burrows-Wheeler Transform bwt of some text
    // and compute as a result:
    //   * starts - for each character C in bwt, starts[C] is the first position
    //       of this character in the sorted array of
    //       all characters of the text.
    //   * occ_count_before - for each character C in bwt and each position P in bwt,
    //       occ_count_before[C][P] is the number of occurrences of character C in bwt
    //       from position 0 to position P inclusive.
    private void PreprocessBWT(String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before) {
        // Implement this function yourself
        
        //need to sort bwt; last col = bwt, first col = sortedBWT
        char[] bwtSorted = bwt.toCharArray();
        Arrays.sort(bwtSorted);

        //go through sorted BWT and fill in starts (starting position of all letters) 
        for (int i = 0; i < bwtSorted.length; i++){
            char C = bwtSorted[i];
            if (!starts.containsKey(C)){
                starts.put(C, i);
            }
        }//note: since starts is a HashMap there is no ordering of the keys
        
        //initialize occ_counts by adding each character from starts and an array of side bwtSorted.length +1. the +1 is for the last row of the count matrix
        for (Character ch : starts.keySet()){
            occ_counts_before.put(ch, new int[bwtSorted.length + 1]);
        }
        
        //go through bwt and fill in occ_counts_before. 
        for (int i = 1; i < bwt.length() + 1; i++){
            char current = bwt.charAt(i-1);//take char i-1 from BWT (last Col)
            for (Map.Entry<Character, int[]> charEntry : occ_counts_before.entrySet()){//iterates through every unique letterin bwt. we get this from the starts keySet
                charEntry.getValue()[i] = charEntry.getValue()[i-1] + (charEntry.getKey() == current ? 1:0);//this sums previous values from 0 to i. it is top down. 
            }
        }//note: since occ_counts_before is a HashMap there is no ordering of the keys  
        
        //printPreprocessedBWT(bwt, starts, occ_counts_before);
    }

    private void printPreprocessedBWT(String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before){
        //this prints out the preprocessed BWT in a way that is similiar to how it was presented in the lecture
        System.out.println("+----- Preprocessed BWT -----+");
        System.out.println(bwt);
        System.out.println();
        char[] bwtSorted = bwt.toCharArray();
        Arrays.sort(bwtSorted);
        System.out.print("i\t FC\t LC\t Strt");
        for (Map.Entry<Character, int[]> charEntry : occ_counts_before.entrySet()){//iterates through every entry in occ_counts_before
                System.out.print("\t "+charEntry.getKey());
            }
        System.out.println();
        for (int i = 0; i < bwtSorted.length; i++){
            System.out.print(i+"\t "+bwtSorted[i]+"\t "+bwt.charAt(i)+"\t "+starts.get(bwtSorted[i]));
            for (Map.Entry<Character, int[]> charEntry : occ_counts_before.entrySet()){//iterates through every entry in occ_counts_before
                System.out.print("\t "+charEntry.getValue()[i]);
            }
            System.out.println();
        }
        System.out.print(bwtSorted.length+"\t \t \t ");
            for (Map.Entry<Character, int[]> charEntry : occ_counts_before.entrySet()){//iterates through every entry in occ_counts_before
                System.out.print("\t "+charEntry.getValue()[bwtSorted.length]);
            }  
        System.out.println();   
        System.out.println();
        System.out.println("+----- Preprocessed BWT -----+");
        System.out.println();
    }
    
    
    // Compute the number of occurrences of string pattern in the text
    // given only Burrows-Wheeler Transform bwt of the text and additional
    // information we get from the preprocessing stage - starts and occ_counts_before.
    int CountOccurrences(String pattern, String bwt, Map<Character, Integer> starts, Map<Character, int[]> occ_counts_before) {
        // Implement this function yourself
        //Implement the improved BWMatching algorithm
        int top = 0; 
        int bottom = bwt.length() - 1;
        StringBuilder patternSB = new StringBuilder(pattern);//use StringBuilder to remove last letter from pattern
        while (top <= bottom){
            if (patternSB.length() != 0){
                char symbol = patternSB.charAt(patternSB.length() - 1); //symbol = last letter of pattern
                patternSB.deleteCharAt(patternSB.length() - 1); //delete last character of pattern
                if ((occ_counts_before.get(symbol)) != null &&  (occ_counts_before.get(symbol)[bottom+1] > occ_counts_before.get(symbol)[top])){
                        //first check that the symbol from pattern is in occ_counts_before. It it is, then
                        //compare the value in occ_counts_before of top pointer to the bottom(+1) pointer
                        //an occurance of symbol in LastColumn from top to bottom only occurs if the bottom(+1) value is greater than the top value.         
                    top = starts.get(symbol) + occ_counts_before.get(symbol)[top];
                    bottom = starts.get(symbol) + occ_counts_before.get(symbol)[bottom+1] - 1;
                        //FirstOccurance(symbol) = starts.get(symbol)
                        //COUNTsymbol(top, LastColumn) = occ_counts_before.get(symbol)[top]
                        //COUNTsymbol(bottom+1, LastColumn) = occ_counts_before.get(symbol)[bottom+1]
                }
                else{
                    return 0;
                }
            }
            else{
                return bottom - top + 1;
            }
        }        
        return 0;
    }

    static public void main(String[] args) throws IOException {
        new BWMatching().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        // Start of each character in the sorted list of characters of bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, Integer> starts = new HashMap<Character, Integer>();
        // Occurrence counts for each character and each position in bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, int[]> occ_counts_before = new HashMap<Character, int[]>();
        // Preprocess the BWT once to get starts and occ_count_before.
        // For each pattern, we will then use these precomputed values and
        // spend only O(|pattern|) to find all occurrences of the pattern
        // in the text instead of O(|pattern| + |text|).
        PreprocessBWT(bwt, starts, occ_counts_before);
        int patternCount = scanner.nextInt();
        String[] patterns = new String[patternCount];
        int[] result = new int[patternCount];
        for (int i = 0; i < patternCount; ++i) {
            patterns[i] = scanner.next();
            result[i] = CountOccurrences(patterns[i], bwt, starts, occ_counts_before);
        }
        print(result);
    }
    
        public void tester() throws IOException{
        new BWMatching();
        int testCaseStart = 1;
        int testCaseEnd = 3;
        for (int i=testCaseStart; i<=testCaseEnd; i++){
            runTest(i);
        }                  
    }

    public void runTest(int testNumber) throws IOException {
        String source = "sample_tests/sample"+testNumber;
        String sourceA = "sample_tests/sample"+testNumber+".a";        
        
        //read source file and run algorithm
        FastScanner scanner = new FastScanner(source);
        String bwt = scanner.next();
        // Start of each character in the sorted list of characters of bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, Integer> starts = new HashMap<Character, Integer>();
        // Occurrence counts for each character and each position in bwt,
        // see the description in the comment about function PreprocessBWT
        Map<Character, int[]> occ_counts_before = new HashMap<Character, int[]>();
        // Preprocess the BWT once to get starts and occ_count_before.
        // For each pattern, we will then use these precomputed values and
        // spend only O(|pattern|) to find all occurrences of the pattern
        // in the text instead of O(|pattern| + |text|).
        PreprocessBWT(bwt, starts, occ_counts_before);
        printPreprocessedBWT(bwt, starts, occ_counts_before);
        int patternCount = scanner.nextInt();
        String[] patterns = new String[patternCount];
        int[] result = new int[patternCount];        
        for (int i = 0; i < patternCount; ++i) {
            patterns[i] = scanner.next();
            result[i] = CountOccurrences(patterns[i], bwt, starts, occ_counts_before);
        }        
        
        //print out the input
        System.out.println(bwt);
        System.out.println(patternCount);
        for (int i = 0; i < patterns.length; i++){
            System.out.print(patterns[i]+" ");
        }
        
        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answer = inA.readLine();        
        
        //compare edges with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out from algorithm");
        print(result);
        System.out.println();
        System.out.println("Print out of result from test cases");
        System.out.println(answer);
        System.out.println();
        System.out.println("--------------------");
    }       
}
