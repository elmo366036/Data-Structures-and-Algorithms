import java.io.*;
import java.util.*;


public class MaxMatching {
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new MaxMatching().solve();
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        boolean[][] bipartiteGraph = readData();
        int[] matching = findMatching(bipartiteGraph);
        writeResponse(matching);
        out.close();
    }

    boolean[][] readData() throws IOException {
        int numLeft = in.nextInt();
        int numRight = in.nextInt();
        boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                adjMatrix[i][j] = (in.nextInt() == 1);
        return adjMatrix;
    }

    private int[] findMatching(boolean[][] bipartiteGraph) {
        // Replace this code with an algorithm that finds the maximum
        // matching correctly in all cases.
        int numLeft = bipartiteGraph.length;
        int numRight = bipartiteGraph[0].length;
        
        int[] matchLeft = new int[numLeft];
        int[] matchRight = new int[numRight];
        //matchLeft and matchRight track connections between nodes in U (right) and V (left)
        //matchLeft[node U] = node V
        //matchRight[node V] = node U
        Arrays.fill(matchLeft, -1); //initialize to -1
        Arrays.fill(matchRight, -1);// initialize to -1
        for(int i = 0; i < numLeft; i++){ //iterate through U and run DFS on it
            dfs(i, matchLeft, matchRight, bipartiteGraph, new boolean[numLeft]);
        }                
        return matchLeft;
    }

    static boolean dfs(int node, int[] matchLeft, int[] matchRight, boolean[][] bipartiteGraph, boolean[] visited){
        if (node == -1){return true;} // this is equivalent to tree = nil
        if (visited[node]){return false;}; // if the node has already been visited, break out of DFS and return false
        visited[node] = true; // else, set the node to true and continue DFS
        for (int i = 0; i < matchRight.length; i++){//iterate through V for each node U
            if (bipartiteGraph[node][i] && dfs(matchRight[i], matchLeft, matchRight, bipartiteGraph, visited)){
                    //if the there is a connection between node (U) and i (V) AND a connection from V to U where V has not been visited
                matchLeft[node] = i; //set set matchLeft[node] to i (set matchNode[U] to V)
                matchRight[i] = node; //do the opposite. set matchRight[i] to node (set matchRight[V] to U
                return true; 
            }
        }
        return false;
    }
    
    private void writeResponse(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                out.print(" ");
            }
            if (matching[i] == -1) {
                out.print("-1");
            } else {
                out.print(matching[i] + 1);
            }
        }
        out.println();
    }

    private void writeResponse2(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                System.out.print(" ");
            }
            if (matching[i] == -1) {
                System.out.print("-1");
            } else {
                System.out.print(matching[i] + 1);
            }
        }
        System.out.println();
    }    
    
    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        private File file;
        private FileReader filereader;        
        
        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public FastScanner(String source) throws IOException{
            file = new File(source);
            filereader = new FileReader(file);
            reader = new BufferedReader(filereader);
            tokenizer = null;            
        }                
        
        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
     
    void printMatrix(boolean[][] bipartiteGraph, int testNumber){
        int numLeft = bipartiteGraph.length;
        int numRight = bipartiteGraph[0].length;

        System.out.println("+---------------+");
        System.out.println("MATRIX for Test Case "+testNumber);
        for (int i = 0; i < numLeft; ++i){
            for (int j = 0; j < numRight; ++j){
                if (bipartiteGraph[i][j] == false){
                    System.out.print("0 ");
                }
                else if (bipartiteGraph[i][j] == true){
                    System.out.print("1 ");
                }
            }
            System.out.println();
        }
        System.out.println("+---------------+");    
    }
    
    void tester() throws IOException{
        int testCaseStart = 1;
        int testCaseEnd = 31;
        //int testCaseEnd = 31;
        for (int i=testCaseStart; i<=testCaseEnd; i++){
            runTest(i);
        }                                                         
    }  
    
    void runTest(int testNumber) throws IOException{
        String source; String sourceA;
        if (testNumber < 10){
            source = "tests/0"+testNumber;
            sourceA = "tests/0"+testNumber+".a";        
                    
        }
        else{        
            source = "tests/"+testNumber;
            sourceA = "tests/"+testNumber+".a";        
        }
        
        //read source file
        in = new FastScanner(source);               
        
        //process source file
        boolean[][] bipartiteGraph = readData();
        
        //print out the processed input
        //printMatrix(bipartiteGraph, testNumber);              
        
        //read answer file
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answerString = inA.readLine();
        String[] answerStringArray = answerString.split(" ");	
        
        //run the algorithm               
        int[] matching = findMatching(bipartiteGraph);
         
        //compare result with answer
        
        for (int i = 0; i < matching.length; i++){
            int m;
            if (matching[i] != -1){
                m = matching[i] + 1;
            }
            else {m = matching[i];}
            if (m != Integer.parseInt(answerStringArray[i])){
                System.out.println("FAIL: test case "+testNumber);
            }
        }
        
        System.out.println("COMPLETE: test case "+testNumber);
        
        /*
        System.out.print("Result: \t");
        writeResponse2(matching);
        System.out.println();
        System.out.print("Answer: \t");
        for (int i = 0; i < answerStringArray.length; i++){
            System.out.print(answerStringArray[i]+" ");
        }
        System.out.println();
        System.out.println();
        */
    }    
}
