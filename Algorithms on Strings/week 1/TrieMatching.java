import java.io.*;
import java.util.*;

class Node
{
    public static final int Letters =  4;
    public static final int NA      = -1;
    public int next [];

    Node ()
    {
        next = new int [Letters];
        Arrays.fill (next, NA); //array next starts out as [-1, -1, -1 ,-1]; -1 means that there is no edge to another A, C, G, or T. all -1s is a leaf.
    }
    
    public boolean isLeaf() { //need to test if a Node is a leaf. Do this in the Node class.
        for (int i = 0; i < next.length; i++){ 
            if (next[i] != NA){return false;} //if any of the four pointers in next[] points to something (i.e., is not -1), then it is not a leaf
        }
        return true;
    }
    
    // ** add method to see if it is the end of a pattern
}

public class TrieMatching implements Runnable {
    int letterToIndex (char letter)
    {
        switch (letter)
        {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3;
            default: assert (false); return Node.NA;
        }
    }

    List<Node> buildTrie(List<String> patterns){
        List<Node> trie = new ArrayList<Node>();
        trie.add(new Node()); //add a root to the trie in the form of a new Node
        
        //iterate through each string in patterns
        for (int i = 0; i < patterns.size(); i++){//iterate through i patterns. for each pattern in patterns. also can use  String pattern : patterns
            Node currentNode = trie.get(0); //set currentNode to the root
            for (int j = 0; j < patterns.get(i).length(); j++){//iterate through each character (symbol) of patterns.get(i)
                Character currentSymbol = patterns.get(i).charAt(j);//set currentSymbol equal to that symbol and define as a Character
                int currentNodeNextNode = currentNode.next[letterToIndex(currentSymbol)]; //convert currentSymbol to an index (0,1,2,3,N/A). lookup the value of this index in currentNode.next[]
                    // currentNodeNextNode is equivalent to currentNode.get(currentSymbol) in the Map based approach
                    // currentNodeNextNode of -1 means that there is no next Node with the labeled symbol
                if (currentNodeNextNode != Node.NA){//if the calculation based on symbol results in any value that is not -1 for currentNode.next[]
                        //if currentNodeNextNode = -1 then there are no edges from currentNode with a label currentSymbol
                    currentNode = trie.get(currentNodeNextNode);//set currentNode to the next Node of the edge
                }
                else {
                    Node newNode = new Node();//create new node
                    trie.add(newNode);//add it to the trie
                    currentNode.next[letterToIndex(currentSymbol)] = trie.size() - 1;//add new next node to currentNode.next[] corresponding 
                    currentNode = newNode;
                }
                //  **add if then for pattern length 
            }
        }        
        return trie;
    }
    
    List <Integer> solve (String text, int n, List <String> patterns) {
        List <Integer> result = new ArrayList <Integer> ();
        // write your code here        

        // create a trie for patterns
        List<Node> trie = buildTrie(patterns);        
        
        //implement TrieMatching which will call PrefixTrieMatching
        int textIndex = 0;
        while (text.length() > 0){//can use !text.isEmpty()
            int match = prefixTrieMatching(textIndex++, text, trie); //match is equal to the starting point of the match
            if (match != -1) {
                result.add(match);
            }
            text = text.substring(1);
        }
        
        
        return result;
    }

    int prefixTrieMatching(int textIndex, String text, List<Node> trie){
        // ** add references to pattern end. change isLeaf to isPatternEnd
        Character currentSymbol = text.charAt(0); //set currentSymbol to first character of text
        Node currentNode = trie.get(0); //set currentNode to the root
        int currentSymbolIndex=0; //this is the index of the currentSymbol
        while (true) {  //this is same as "while forever"
            int currentNodeNextNode = currentNode.next[letterToIndex(currentSymbol)]; //convert currentSymbol to an index (0,1,2,3,N/A). lookup the value of this index in currentNode.next[]
                    // currentNodeNextNode is equivalent to currentNode.get(currentSymbol) in the Map based approach
                    // currentNodeNextNode of -1 means that there is no next Node with the labeled symbol
            if (currentNode.isLeaf()){
                return textIndex; //returning the index rather than the pattern spelled out from root to currentNode
            }
            else if (currentNodeNextNode != Node.NA){//if the calculation based on symbol results in any value that is not -1 for currentNode.next[]
                        //if currentNodeNextNode = -1 then there are no edges from currentNode with a label currentSymbol
                    currentNode = trie.get(currentNodeNextNode);//set currentNode to the next Node of the edge
                    if (currentSymbolIndex + 1 < text.length()) {
                        currentSymbol = text.charAt(++currentSymbolIndex);
                    }
                    else {
                        if (currentNode.isLeaf()) {
                            return textIndex;
                        }
                        break;                       
                    }
            }
            else {break;}
                }                                     
        return -1;
        }
    
    public void run () {
        try {
            BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
            String text = in.readLine ();
            int n = Integer.parseInt (in.readLine ());
            List <String> patterns = new ArrayList <String> ();
            for (int i = 0; i < n; i++) {
                patterns.add (in.readLine ());
            }

            List <Integer> ans = solve (text, n, patterns);

            for (int j = 0; j < ans.size (); j++) {
                System.out.print ("" + ans.get (j));
                System.out.print (j + 1 < ans.size () ? " " : "\n");
            }
        }
        catch (Throwable e) {
            e.printStackTrace ();
            System.exit (1);
        }
    }

    public static void main (String [] args) {
        new Thread (new TrieMatching ()).start ();
    }
    
    public void tester() throws IOException{
        new TrieMatching();
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
        String text = in.readLine ();
        int n = Integer.parseInt (in.readLine ());
        List <String> patterns = new ArrayList <String> ();
        for (int i = 0; i < n; i++) {
            patterns.add (in.readLine ());
            }

        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String line = inA.readLine();
        String[] results;
        if (line == null){
            results = new String[0];            
        }
        else{
            results = line.split("\\s+");        
        }    

        // excute the alrogithm 
        List<Integer> ans = solve (text, n, patterns);
        
        //compare ans with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out ans from algorithm");
        for (int j = 0; j < ans.size (); j++) {
            System.out.print ("" + ans.get (j));
            System.out.print (j + 1 < ans.size () ? " " : "\n");
            }  
        System.out.println("Print out result from test cases");
        for (int j = 0; j < results.length; j++){
            System.out.print(results[j] + " ");
        }
        System.out.println();
        System.out.println("--------------------");        
    }
}





