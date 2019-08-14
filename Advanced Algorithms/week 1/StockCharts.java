import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class StockCharts {
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new StockCharts().solve();
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        int[][] stockData = readData();
        int result = minCharts(stockData);
        writeResponse(result);
        out.close();
    }

    int[][] readData() throws IOException {
        int numStocks = in.nextInt();
        int numPoints = in.nextInt();
        int[][] stockData = new int[numStocks][numPoints];
        for (int i = 0; i < numStocks; ++i)
            for (int j = 0; j < numPoints; ++j)
                stockData[i][j] = in.nextInt();
        return stockData;
    }

    private int minCharts(int[][] stockData) {
        // Replace this incorrect greedy algorithm with an
        // algorithm that correctly finds the minimum number
        // of charts on which we can put all the stock data
        // without intersections of graphs on one chart.

        int numStocks = stockData.length;
        int numPoints = stockData[0].length;
        boolean[][] compareStocks = new boolean[numStocks][numStocks];
        
        //initialize the boolean matrix. this is a comparison matrix
        /*
         * F11  T12 T13           
         * T21* F22 T23
         * T31  T32 F33
         * 
         * compare: if stock x [i] less than stock y [j] look at [i][j] -> stock 2 [2] stock 1 [1] = [2][1] -> T21*
         */
        for (int i = 0; i < numStocks; i++){
            for (int j = 0; j < numStocks; j++){
                if (i == j) {continue;} //when i = j, do nothing and continue the loop (i.e., don't set it to true)
                compareStocks[i][j] = true; // set to true
                for (int k = 0; k < numPoints; k++){ //for every j in i, iterate through numPoints
                    compareStocks[i][j] &= stockData[i][k] < stockData[j][k]; 
                    // this will set compareStocks[i][j] to false iff the stockdata for [j][k] > [i][k]
                    // this is the comparison 
                    if (!compareStocks[i][j]){break;}
                    //if compareStocks[i][j] becomes false, break out of the k (numPoints) loop because it will never become true
                }
            }
        }

        //print out compareStocks
        /*
        System.out.println();
        System.out.println();
        printCompareStocks(compareStocks);
        */
       
        //initialize bipartiteMatch
        //bipartiteMatch[0] is left and bipartiteMatch[1] is right
        int[][] bipartiteMatch = new int[2][numStocks];
        for (int i = 0; i < 2; i++){
            int[] row = bipartiteMatch[i];
            Arrays.fill(row, -1);
        }

        int path = 0;
        for (int i = 0; i < numStocks; i++){
            if (dfs(i, new boolean[numStocks], bipartiteMatch, compareStocks)){
                path++; //this counts the number of paths between bipartiteMatch[0] and bipartiteMatch[1]
            }
        }

        //print out bipartiteMatch
        /*
        printBipartiteMatrix(bipartiteMatch);
        System.out.println("Path "+path); 
        */
       
        return numStocks - path; //path is the number of intersections between left and right
    }

    private boolean dfs(int i, boolean[] visited, int[][] bipartiteMatch, boolean [][] compareStocks){
        if (i == -1){return true;}// this is equivalent to tree = nil
        if (visited[i]){return false;}// if i has already been visited, break out of DFS and return false
        visited[i] = true;// else, set i to true and continue DFS
        for (int j = 0; j < compareStocks.length; j++){//iterate through each stock
                //if Stock i < j AND the there is a connection between node (U) and i (V) AND a connection from V to U where V has not been visited
            if (compareStocks[i][j] && dfs(bipartiteMatch[1][j], visited, bipartiteMatch, compareStocks)){
                bipartiteMatch[0][i] = j; //set matchLeft[i] to j 
                bipartiteMatch[1][j] = i; //set MatchRight[j] to i
                return true;
            }
        }
        return false;
    }    
    
    private void printCompareStocks(boolean[][] compareStocks){          
        System.out.println("+-----------------+");
        System.out.println("CompareStocks");
        for (int i = 0; i < compareStocks.length; ++i){
            for (int j = 0; j < compareStocks[0].length; ++j){
                    System.out.print(compareStocks[i][j]+" \t ");
            }            
            System.out.println();
        }        
        System.out.println("+-----------------+");   
        System.out.println();  
    }
    
    private void printBipartiteMatrix(int[][] bipartiteMatch){
        System.out.println("+-----------------+");
        System.out.println("Bipartite Matrix");
        for (int i = 0; i < bipartiteMatch[0].length; i++){
            System.out.print(bipartiteMatch[0][i]+" \t ");
        }
        System.out.println();
        for (int i = 0; i < bipartiteMatch[0].length; i++){
            System.out.print(bipartiteMatch[1][i]+" \t ");
        }   
        System.out.println();
        System.out.println("+-----------------+");
        System.out.println();        
    }
    

    
    
    /* OLD
    boolean compare(int[] stock1, int[] stock2) {
        for (int i = 0; i < stock1.length; ++i)
            if (stock1[i] >= stock2[i])
                return false;
        return true;
    }
    */
    private void writeResponse(int result) {
        out.println(result);
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
    
    void printMatrix(int[][] stockData, int testNumber){
        int numStocks = stockData.length;
        int numPoints = stockData[0].length;
        
        System.out.println("+-----------------+");
        System.out.println("MATRIX for test case "+testNumber);
        for (int i = 0; i < numStocks; ++i){
            for (int j = 0; j < numPoints; ++j){
                    System.out.print(stockData[i][j]+" ");
            }            
            System.out.println();
        }        
        System.out.println("+-----------------+");   
    }

    void tester() throws IOException{
        int testCaseStart = 1;
        int testCaseEnd = 36;
        //int testCaseEnd = 36;
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
        int[][] stockData = readData();
        
        //print out the processed input
        //printMatrix(stockData, testNumber);              
        
        //read answer file
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answer = inA.readLine(); 
        
        //run the algorithm               
        int result = minCharts(stockData);
         
        //compare result with answer
        if (result != Integer.parseInt(answer)){
            System.out.println("FAIL: test case "+testNumber+" \t result "+result+" \t answer "+answer);
        }        
        else {System.out.println("COMPLETE: test case "+testNumber);}
    }       
    
}
