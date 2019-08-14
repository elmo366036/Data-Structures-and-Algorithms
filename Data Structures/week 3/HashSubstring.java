import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    
    //prime, rand x

    public void tester() throws IOException{
        String source = "tests/06";
        String sourceA = "tests/06.a";
        
        //in = new FastScanner(source);
        printOccurrences(getOccurrences(readInput()));      
    }
    
    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);//add back
            out.print(" ");//add back
            //System.out.print(cur+" ");//remove
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        //INIT 
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length(), x = 7;
        //long prime = 8888888897888888899L; //this number is way to big!!! need larger than length T * length P but much less than max size of long
        long prime = 1000000007L;
        //List<Integer> occurrences = new ArrayList<Integer>(); //naive
        List<Integer> result = new ArrayList<Integer>();
        
        long pHash = polyHash(s,m,prime,x);
        //System.out.println("pHash for string "+s+" is "+pHash);
        long[] Hashes = new long[n-m+1];
        Hashes = preComputeHashes(t,m,n,prime,x);
        
        for (int i=0; i<=n-m; i++){
            //System.out.println("Hash T["+i+"] is "+Hashes[i]);
            if (pHash != Hashes[i]){continue;}
            if (areEqual(s,t,m,i)){result.add(i);}
        }
        
        return result;
        /*
        //START FindPatternNaive
        for (int i = 0; i + m <= n; ++i) {
            //START AreEqual 
            boolean equal = true;
            for (int j = 0; j < m; ++j) {
                if (s.charAt(j) != t.charAt(i + j)) {
                    equal = false;
                    break;
                }
            }
            //END AreEqual
            if (equal)
                occurrences.add(i);
            }
        //END FindPatternNaive                     
        return occurrences;
        */
    }

    private static boolean areEqual(String s, String t, int m, int i) {
        boolean equal = true;
        for (int j = 0; j < m; ++j) {
            if (s.charAt(j) != t.charAt(i + j)) {
                equal = false;
                break;
            }
        }        
        return equal;
    }
    
    private static long[] preComputeHashes(String t, int m, int n, long prime, int x){
        long[] hashList = new long[n-m+1];
        String lastSegmentText = t.substring(n-m);
        long lastSegmentTextHash = polyHash(lastSegmentText, m, prime, x);
        //System.out.println("lastSegmentTextHash "+lastSegmentTextHash);
        hashList[n-m] = lastSegmentTextHash;
        long y = 1;
        for (int i=1; i<=m; i++){
            y = (y*x) % prime;
        }
        for (int i=n-m-1; i>=0; --i){
            //hashList[i] = (x*hashList[i+1] + t.charAt(i) - y*t.charAt(i+m)) % prime; //T[i] = t.charAt[i];  oiginal
            hashList[i] = (((x*hashList[i+1] + t.charAt(i) - y*t.charAt(i+m)) % prime + prime) % prime); //T[i] = t.charAt[i]; try this instead
            
        }
        return hashList;
    }

    private static long polyHash(String s, int m, long prime, int x){
        long pHash = 0;
        for (int i=m-1; i>=0; --i){
            pHash = (pHash*x + s.charAt(i)) % prime; //S[i] = s.charAt[i]
        }       
        return pHash;
    }
    
    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }
        /*
        public FastScanner(String source) throws IOException{//remove all
            //System.out.println("fastscanner "+source);             
            File file = new File(source);
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);            
        }
        */
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

