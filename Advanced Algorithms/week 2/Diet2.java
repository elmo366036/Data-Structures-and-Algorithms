import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Diet2 {

    File file;
    FileReader filereader;
    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    int solveDietProblem(int n, int m, double A[][], double[] b, double[] c, double[] x) {
        Double max = Double.NaN;
        double[] mainSolution = new double[m];
        double[][] newA;
        double[] newB;
        List<int[]> rowCombinations = rowCombinations(IntStream.range(0, n + m + 1).toArray(), m, 0, new int[m], new ArrayList<>());
            //IntStream.range(0, n+m+1) creates a list of integers from 0 to n+m. .toArray creates int[] [0, 1, 2, ... n+m]
            //rowCombinations will contain n+m int arrays. each int array will contain m numbers.
            //each int array is a list indexes to equations in A. for example, a rowCombination of [0,1] means use the first two rows of A
            //WHAT TO DO ...you need to take each possible subset of size m out of all the n+m inequalities...(create the n+m inequalities)

        //print out combinations    
        //printCombinations(rowCombinations); //for testing

        //iterare through each combination
        int iterationCount = 0; //for testing
        for (int[] currentRowCombination : rowCombinations) {
            iterationCount++; //for testing
            
            //WHAT TO DO ...you need to take each possible subset of size m out of all the n+m inequalities,
            newA = new double[m][m];
            newB = new double[m];
            extractRows(A, b, newA, newB, currentRowCombination);
                //extractRows copies the rows specified by currentRowCombination and puts them into newA and newB
            
            //print after extractRows
            //printExtractedRows(newA, newB, iterationCount, "Current Row Combination extraction"); //for testing
            
            double[] solution;
                //WHAT TO DO ...solve the system of linear equations where each equation is one of the selected inequalities changed to equality...
            try {
                solution = gaussianElimination(newA, newB);
                    //use gaussianElimination and see if a solution can be reached from the extracted rows in newA and newB
                    //if so solution contains values for [x1, x2, ... xm] that solves the inequalities of newA and newB
                //printVector(solution, "solution");
            } 
            catch (IllegalArgumentException e) {
                //System.out.println("no Gaussian Elimination solution");//for testing
                continue;
            }
            
            int[] currentExcludedRowCombinations = excludedRowCombinations(IntStream.range(0, n + m + 1).toArray(), currentRowCombination);
                //this produces a list of the other rows not part of currentCombination
            
            //print currentComplement
            //printExcludedRowCombinations(currentExcludedRowCombinations, iterationCount);//for testing
                        
            newA = new double[currentExcludedRowCombinations.length][m];
            newB = new double[currentExcludedRowCombinations.length];
            extractRows(A, b, newA, newB, currentExcludedRowCombinations);
            
            //print again after curRow
            //printExtractedRows(newA, newB, iterationCount, "excluded rows extraction");//for testing

            //check the solution            
            if (checkSolution(solution, newA, newB)) {
                //check the solution from gaussianElimiation on currentCombination with newA and newB from the complement
                double solutionValue = Arrays.stream(multiplyArrays(solution, c)).sum();
                //System.out.println();//for testing
                //System.out.println("Solution Value "+solutionValue+"; Iteration "+iterationCount);//for testing
                if (max.isNaN() || solutionValue > max) {
                        //if max has not been set or solutionValue is greater than max, 
                        //set max to solution value and copy the solution vector to mainSolution
                    max = solutionValue;
                    mainSolution = Arrays.copyOf(solution, solution.length);
                    //printVector(mainSolution, "Main Solution");//for testing
                }
            }
        }
        
        //check max
        if (max.isNaN()) {return -1;}
        if (max > 999_999_990D) {return 1;}
        System.arraycopy(mainSolution, 0, x, 0, x.length);
        return 0;
    }

    public static double[] multiplyArrays(double[] a, double b[]) {
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * b[i];
        }
        return result;
    }

    public static boolean checkSolution(double[] solution, double[][] A, double[] b) {
        //System.out.println("Check Solution");//for testing
        for (int i = 0; i < A.length; i++) {
            //for each row in A, multiple that row with solution and compare with b
            double[] inequality = A[i];
            //System.out.println("compare "+Arrays.stream(multiplyArrays(solution, inequality)).sum()+" to "+b[i]);//for testing
            if (Arrays.stream(multiplyArrays(solution, inequality)).sum() > b[i]){
                    //this multiplies inequality (a row in A) with solution and then adds up the result. 
                    //if it is greated than b there is no solution
                //System.out.println("FALSE");//for testing
                return false;
            }
        }
        return true;
    }

    public static int[] excludedRowCombinations(int[] all, int[] indexes) {
        return Arrays.stream(all).filter(a -> Arrays.stream(indexes).noneMatch(i -> i == a)).toArray();
    }

    //for testing
    public static void printExcludedRowCombinations(int[] currentExcludedRowCombinations, int iterationCount){
       System.out.println();
       System.out.println("Excluded Row Combinations; Iteration "+iterationCount);
       System.out.print("[");
       for (int i = 0; i < currentExcludedRowCombinations.length; i++){
           if (i < currentExcludedRowCombinations.length - 1){
               System.out.print(currentExcludedRowCombinations[i]+", ");
           }
           else {
             System.out.print(currentExcludedRowCombinations[i]);  
           }
        }
       System.out.print("]"); 
       System.out.println();
    }

    public static void extractRows(double[][] A, double[] b, double[][] newA, double[] newB, int[] indexes) {
        //manipulates newA and newB based on combinations
        for (int i = 0; i < indexes.length; i++) {
                //iterate m number of times. indexes.length = m
            if (indexes[i] < A.length) {
                    //if rowCombination[i] is less than n, copy A[rowCombination] to newA and copy b[rowCombination] to newB
                newA[i] = Arrays.copyOf(A[indexes[i]], A[i].length);
                newB[i] = b[indexes[i]];
            } else if (indexes[i] == A.length + A[0].length) {
                    //if index = n+m, newA is an array of 1s and newB is a big num (1.0E9)
                Arrays.fill(newA[i], 1);
                newB[i] = 1_000_000_000D;
            } else {
                    //if index < n+m, newA is all 0s except for one column which is -1
                newA[i][indexes[i] - A.length] = -1;
            }
        }
    }

    //for testing
    public static void printExtractedRows(double[][] newA, double[] newB, int iterationCount, String cutType){
        System.out.println();
        System.out.println("Extracted Rows; Iteration "+iterationCount+"; "+cutType); 
        for (int i = 0; i < newA.length; i++){
            for (int j = 0; j < newA[0].length; j++){
                System.out.print(newA[i][j]+"\t");
            }
            System.out.print(newB[i]);
            System.out.println();
        }        
    }
    
    public static List<int[]> rowCombinations(int[] arr, int len, int startPosition, int[] rowCombination, List<int[]> result) {
        if (len == 0) {//the recursive iteration goes from startPosition to m. once it is at m, len will be 0
            result.add(rowCombination.clone()); //add a clone of rowCombination
            return result;
        }
        for (int i = startPosition; i <= arr.length - len; i++) {
            rowCombination[rowCombination.length - len] = arr[i];
            rowCombinations(arr, len - 1, i + 1, rowCombination, result); // ([1,2,...n+m], m=m-1, start+1, rowCombination, result)
        }
        return result;
    }

    //for testing
    public static void printCombinations(List<int[]> combinations) {
        System.out.println();
        System.out.println("Row Combinations[]");
        for (int i = 0; i < combinations.size(); i++){
            int[] combo = combinations.get(i);
            System.out.print("[");
            for (int j = 0; j < combo.length; j++){                       
                if (j < combo.length - 1){
                    System.out.print(combo[j]+", ");
                }
                else {
                    System.out.print(combo[j]+"]");
                }
            }
            System.out.println();
        }       
    }

    public static double[] gaussianElimination(double A[][], double[] b) {
        //run Gaussian Elimination on newA and newB        
        int N = A[0].length;
        for (int p = 0; p < N; p++) {//iterate through N columns 
            int max = p; //set max to column#            
            //select pivot
            for (int i = p + 1; i < N; i++) {
                    //iterate in rows from column# + 1 to N
                    //this aligns with rowCombinations
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                    //find the row index with the largest value
                }
            }
            if (Math.abs(A[max][p]) <= 0.000001) {
                throw new IllegalArgumentException();
                    //for each column, if the largest value is nearly 0, throw an error
                    //if pivot is narly 0, through error
            }

            //swap rows for both A and b
            double[] temp = A[p];//temp = row corresponding to the col index
            A[p] = A[max];
            A[max] = temp;//swap A[p] with A[max]
            double t = b[p];
            b[p] = b[max];
            b[max] = t;//swap b[p] with b[max]

            //process pivot element
            for (int i = p + 1; i < N; i++) {
                double alpha = A[i][p] / A[p][p];   //determine scale value
                b[i] -= alpha * b[p];               //scale & subtract for b
                for (int j = p; j < N; j++) {
                    A[i][j] -= alpha * A[p][j];     //scale & subtract for A
                }
            }
        }

        //print A and b
        //printLEs(A,b, "Gaussian Elimation");//for testing
        
        double[] solution = new double[N];
        //this solves the set of equalities
        for (int i = N - 1; i >= 0; i--) {//i counts from second to last row down to first
            double sum = 0.0;//sum is 0 at each row
            for (int j = i + 1; j < N; j++) {//
                sum += A[i][j] * solution[j];
            }
            solution[i] = (b[i] - sum) / A[i][i];
                // solution = (b - sum_of_coefficients_of_a_column*solution_for_that_column) divided by the cooeficient of other column
        }

        return solution;
    }

    void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        double[][] A = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = nextInt();
            }
        }
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = nextInt();
        }
        double[] c = new double[m];
        for (int i = 0; i < m; i++) {
            c[i] = nextInt();
        }
        double[] ansx = new double[m];
        int anst = solveDietProblem(n, m, A, b, c, ansx);
        if (anst == -1) {
            out.printf("No solution\n");
            return;
        }
        if (anst == 0) {
            out.printf("Bounded solution\n");
            for (int i = 0; i < m; i++) {
                out.printf("%.18f%c", ansx[i], i + 1 == m ? '\n' : ' ');
            }
            return;
        }
        if (anst == 1) {
            out.printf("Infinity\n");
            return;
        }
    }

    //for testing
    void solve2() throws IOException {
        //process input        
        int n = nextInt();
        int m = nextInt();
        double[][] A = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = nextInt();
            }
        }
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            b[i] = nextInt();
        }
        double[] c = new double[m];
        for (int i = 0; i < m; i++) {
            c[i] = nextInt();
        }
                  
        //add new equation for boundedness
        double[] upperBoundA = new double[m];
        double upperBoundB = 1000000000;
        Arrays.fill(upperBoundA, 1);  
        
        //print input
        printLEs(A, b, "Initial LEs");//for testing
        String vectorC = "c";//for testing
        printVector(c, vectorC);//for testing        
        
        //process result
        int anst = solveDietProblem(n, m, A, b, c, upperBoundA);
        
        //print result
        System.out.println("+-------------------------+");
        System.out.println("Result");
        String result;
        if (anst == -1) {
            System.out.printf("No solution\n");
            result = "No solution";
            System.out.println("+-------------------------+");
            System.out.println();
            System.out.println();
            return;
        }
        if (anst == 0) {
            System.out.printf("Bounded solution\n");
            result = "Bounded solution";
            for (int i = 0; i < m; i++) {
                System.out.printf("%.18f%c", upperBoundA[i], i + 1 == m ? '\n' : ' ');
            }
            System.out.println("+-------------------------+");
            System.out.println();
            System.out.println();            
            return;
        }
        if (anst == 1) {
            System.out.printf("Infinity\n");
            result = "Infinity";
            System.out.println("+-------------------------+");
            System.out.println();
            System.out.println();            
            return;
        }      
    }          
    
    Diet2() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    //for testing
    Diet2(String source) throws IOException {
        file = new File(source);
        filereader = new FileReader(file);
        br = new BufferedReader(filereader);
        solve2();
    }        

    public static void main(String[] args) throws IOException {
        new Diet2();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
    
    public static void tester() throws IOException{
        //run through 0-6, 36, 43
        int testCaseStart = 1;
        int testCaseEnd = 6;
        //int testCaseEnd = 1;
        for (int i=testCaseStart; i<=testCaseEnd; i++){
            System.out.println("TEST "+i);
            runTest(i);
        }         
        
        System.out.println("TEST 36");
        runTest(36);
        System.out.println("TEST 43");
        runTest(43);
        
    }  
    
    static void runTest(int testNumber) throws IOException{
        String source; String sourceA;
        if (testNumber < 10){
            source = "tests/0"+testNumber;
            sourceA = "tests/0"+testNumber+".a";                            
        }
        else{        
            source = "tests/"+testNumber;
            sourceA = "tests/"+testNumber+".a";        
        }

        //run the algorithm using source 
        new Diet2(source);
         
        //read answer file (not used)
        /*
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answerString = inA.readLine();
        String[] answerStringArray = answerString.split(" ");	
        
        //run the algorithm (already run in Diet)              
         
        //compare result with answer (will do manally from Diet output)

        */
    }              

    static void printLEs(double[][] A, double[] b, String LPType) {
        System.out.println("+-------------------------+");
        System.out.println("Linear Equations "+LPType);
        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A[0].length; j++){
                System.out.print(A[i][j]+"\t");
            }
            System.out.print(b[i]);
            System.out.println();
        }
        System.out.println();
        System.out.println("+-------------------------+");
        System.out.println();
    }           
    
    void printVector(double[] Z, String vectorType){
        System.out.println("+-------------------------+");
        System.out.println("Vector "+vectorType);
        for (int i = 0; i < Z.length; i++){
            System.out.println(Z[i]);
        }
        System.out.println("+-------------------------+");         
    }        
}
