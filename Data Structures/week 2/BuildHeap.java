import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps; 
    private FastScanner in;
    private PrintWriter out;
    
    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();  //add back
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps() {
        swaps = new ArrayList<Swap>(); 
        int dataSizeHalf = (data.length / 2) - 1;
        for (int i = dataSizeHalf; i >= 0; i--){
            siftDown(i);
        }            
    }      

    private void siftDown(int i){
            int minIndex = i;
            int leftChild = (2*i + 1);
            if (leftChild < data.length && data[leftChild] < data[minIndex]){
                minIndex = leftChild;
            }
            int rightChild = (2*i + 2);
            if (rightChild < data.length && data[rightChild] < data[minIndex]){
                minIndex = rightChild;
            }
            if (i != minIndex){
                swaps.add(new Swap(i, minIndex));
                int temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;
                siftDown(minIndex);
            }         
    }   
    
    public void solve() throws IOException {      
        in = new FastScanner(); //add back
        out = new PrintWriter(new BufferedOutputStream(System.out)); //add back
        readData();
        generateSwaps();
        writeResponse(); //add back        
        out.close();  //add back
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
}
