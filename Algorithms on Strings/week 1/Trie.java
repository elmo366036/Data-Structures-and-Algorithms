import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

public class Trie {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        
        FastScanner(String source) throws IOException {//add this to read from local file. 
            //System.out.println("fastscanner "+source);             
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

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        List<Map<Character, Integer>> trie = new ArrayList<Map<Character, Integer>>();
        // write your code here
        
        //The data structure involves an ArrayList of HashMaps. The index of the ArrayList is a Node (never a leaf) and the HashMap is the connection from the Node to a subordinate Node of Leaf
        Map<Character, Integer> root = new HashMap<Character,Integer>(); //define the root. root is defined as a HashMap. the HashMap is of form {edge, node/leaf) -> <Character, Integer>
        trie.add(root); //add it to trie. trie now is a graph with a single node root
        
        //iterate through each string in patterns
        for (int i=0; i<patterns.length; i++){//iterate through i patterns. for each pattern in patterns. also can use  String pattern : patterns
            Map<Character, Integer> currentNode = root; //set the currentNode = root
            for (int j=0; j<patterns[i].length(); j++){//iterate through each string of pattern[i]
                Character currentSymbol = patterns[i].charAt(j); //set currentSymbol equal to that symbol and define as Character
                Set<Character> neighbors = currentNode.keySet();//Set(unordered list of nonrepeated objects) neighbors contains the keys (characters) of currentNode
                    //neighbors now contains a list of all the edges (characters) that belong to currentNode
                //if (neighbors != null && neighbors.contains(currentSymbol))
                if (neighbors.contains(currentSymbol)){//if there is an edge from currentNode with label currentSymbol
                    currentNode = trie.get(currentNode.get(currentSymbol)); //currentNode is set to the Node of the edge currentSymbol
                }
                else {
                    Map<Character, Integer> newNode = new HashMap<Character, Integer>();//create new node
                    trie.add(newNode);//add that node to trie
                    currentNode.put(currentSymbol, trie.size() - 1);//add new edge currentSymbol from currentNode to newNode
                    currentNode = newNode;//set currentNode to newNode
                }
            }
        }        
        return trie;
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i); //node contains a set of mappings. one for each edge from the node
            for (Map.Entry<Character, Integer> entry : node.entrySet()) { //iterate through those edges. Map.Entry is a key and value combined into one class. iterate through .entrySet()
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
    
    public void tester() throws IOException {
        new Trie();
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
        FastScanner scanner = new FastScanner(source);
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }

        //read answer file
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String line;
        List<String> results = new ArrayList<String>();
        while ((line = inA.readLine()) != null) {
            results.add(line);
        }
        
        //print input and answer to validate they work correctly
        /*
        for (int i=0; i<patterns.length; i++){
            System.out.println(patterns[i]);
        }
        for (int i=0; i<results.size(); i++){
            System.out.println(results.get(i));
        }
        */
       
        // execute the algorithm
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        
        //compare trie with answer. the order of results does not matter
        boolean fail = false;
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                //System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
                String result = i + "->" + entry.getValue() + ":" + entry.getKey();
                if (!results.contains(result)){//the order of results does not matter
                    System.out.println("FAIL\t"+result+"\t"+results.get(i));
                    fail = true;
                    break;
                }
            }
        }        
        if (!fail){System.out.println("Sucess");}  
        System.out.println("Trie");
        print(trie);
    }        
}
