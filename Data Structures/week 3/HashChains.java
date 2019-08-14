import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import java.util.*;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
     
    private HashMap<Integer, LinkedList<String>> elemsMap;
    
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;    

    public void tester() throws IOException{
        String source = "tests/06";
        String sourceA = "tests/06.a";
        //runs HashChains using a source file
        //new HashChains().processQueries(source);
        
    }
    
    public static void main(String[] args) throws IOException {
        new HashChains().processQueries(); //add back
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i){ //--i
            hash = (hash * multiplier + s.charAt(i)) % prime;
        }
        int result = (int)hash % bucketCount;
        return result;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } 
        else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no"); //add back
        //System.out.println(wasFound ? "yes" : "no"); //remove
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {
        int hashIndex;
        switch (query.type) {
            case "add":
                hashIndex = hashFunc(query.s);
                if (elemsMap.containsKey(hashIndex)){
                    LinkedList<String> existingList = new LinkedList<String>();
                    existingList = elemsMap.get(hashIndex);
                    if (!existingList.contains(query.s)){
                        existingList.addFirst(query.s); 
                        elemsMap.put(hashIndex,existingList);
                    }
                }
                else{
                    LinkedList<String> newList = new LinkedList<String>();
                    newList.add(query.s);
                    elemsMap.put(hashIndex,newList);
                }
                break;
            case "del":
                hashIndex = hashFunc(query.s);
                if (elemsMap.containsKey(hashIndex)){
                    LinkedList<String> existingList = new LinkedList<String>();
                    existingList = elemsMap.get(hashIndex);                   
                    if (existingList.contains(query.s)){
                        existingList.remove(query.s);
                        elemsMap.put(hashIndex,existingList);
                    }                                                         
                }
                break;
            case "find":
               hashIndex = hashFunc(query.s);
               if (elemsMap.containsKey(hashIndex)){
                    LinkedList<String> existingList = new LinkedList<String>();
                    existingList = elemsMap.get(hashIndex);
                    if (existingList.contains(query.s)){
                        writeSearchResult(true);
                    }
                    else {writeSearchResult(false);
                    }
                }
                else {writeSearchResult(false);}               
                break;
            case "check": 
                hashIndex = query.ind;
                if (elemsMap.containsKey(hashIndex)){
                    LinkedList<String> checkExistingList = new LinkedList<String>();
                    checkExistingList = elemsMap.get(hashIndex);
                    for (String str : checkExistingList){
                        out.print(str+" ");
                    }
                }
                out.println(); //add back
                //System.out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    //public void processQueries(String source) throws IOException{//remove
    public void processQueries() throws IOException { //add back
        elemsMap = new HashMap<Integer, LinkedList<String>>();
        in = new FastScanner(); //add back
        //in = new FastScanner(source); //remove
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt(); //m buckets, 1st integer read
        int queryCount = in.nextInt(); //n queries, second integer read
        for (int i = 0; i < queryCount; ++i) {
            Query query = readQuery();
            processQuery(query);
        }
        out.close(); //add back
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
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
