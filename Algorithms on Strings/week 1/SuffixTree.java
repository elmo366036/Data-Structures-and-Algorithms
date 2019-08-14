import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

class Node
{  
    public static final int Letters =  5;
    public static final int NA      = -1;

    public int next []; //these are pointers to the next node that maps to [A, C, G, T, $]
    int start;          //this is the position in text where the node starts                           
    int offset;         //this is how many letters after start are included. The total is start to start+offset(+1) 
    public int id;      //node ID
    public boolean hasNeighbors; //is it a leaf? also, this means that the edge ends with a $, also, if next = [-1...] hasNeighbors is false

    Node ()
    {
        next = new int [Letters];
        Arrays.fill (next, NA); //everytime a node is created without any parameters next is set to [-1...]
    }
    
    Node(int start, int offset, int id){ //in this case, a node is created with a start, oddset, and id passed in 
        this();
        this.start = start;
        this.offset = offset;
        this.id = id;
    }   
    
    public void initNextNode() { //this sets the next pointers to -1  [-1...] and sets hasNeighbors to false
        Arrays.fill (next, NA);
        hasNeighbors = false;
    }
}

public class SuffixTree {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
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

    //class Node
    
    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    
    //this algorithm builds the suffix tree directly
    
    int letterToIndex (char letter)
    {
        switch (letter)
        {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3;
            case '$': return 4;
            default: assert (false); return Node.NA;
        }
    }
    
    //buildSuffixTree   
    List<Node> buildSuffixTree(String text) {
        List<Node> tree = new ArrayList<>();
        int count = 0;
        tree.add(new Node(0, -1, count++));//create tree and add root; start = 0 and offset -1 is for the root

        for (int j = 0; j < text.length(); j++) {//this iterates through text 
            int initialStart = text.length() - 1 - j; //initialStart counts down from the end of text
            int initialOffset = j; //initialOffset counts up from 0
            Node currentNode = tree.get(0); //set current node to root
            
            //the whole point of the while loop below is to identify the initialStart and initialOffset positions for the Nodes that are to be added to the tree.
            while (currentNode.next[letterToIndex(text.charAt(initialStart))] > 0) {//gets a character from initialStart (end of text) 
                    //whil currentNode has a pointer to a node that starts with the letter corresponding to the initialStart position, do a bunch of stuff
                    
                currentNode = tree.get(currentNode.next[letterToIndex(text.charAt(initialStart))]); //sets currentNode to "currentNextNode" based on next[]
                int currentStart = currentNode.start; //start position of currentNode
                int currentOffset = currentNode.offset; //offset position of currentNode
                int removeIndex = 1; //another offset/index that tells us how much from the beginning to remove. Is it really a removeCount?
                for (int i = 1; i < currentOffset + 1; i++) { //count to the currentOffset (use i <= currentOffset??)
                    if (text.charAt(currentStart + i) != text.charAt(initialStart + i)) {//this looks for a position where the sections of text do not match
                        break;
                    }
                    removeIndex++;//this increments the removeIndex up to a spot where the two sections of text do not match. 
                }

                if (currentOffset + 1 - removeIndex > 0) {//this is true if there was a spot where the two sections did not match. 
                    Node newNode = new Node(currentStart + removeIndex, currentOffset - removeIndex, count++);//create a new Node
                    currentNode.start = initialStart;
                    currentNode.offset = removeIndex - 1;
                    tree.add(newNode);//add the node to the tree
                    if (currentNode.hasNeighbors) {//if the currentNode has at least one neighbor, make newNode point to them
                        newNode.next = Arrays.copyOf(currentNode.next, currentNode.next.length);
                        newNode.hasNeighbors = true;
                        currentNode.initNextNode(); //currentNode becomes the end
                    }
                    currentNode.next[letterToIndex(text.charAt(newNode.start))] = newNode.id; //set currentNode.next[] to point to the newNode
                    currentNode.hasNeighbors = true;
                } //this appears to put newNode right after currentNde
                initialStart += removeIndex;
                initialOffset -= removeIndex;
            }
            
            
            Node newNode = new Node(initialStart, initialOffset, count++); //creates the Node that will be added to the tree based on calculated initialStart and initialOffset values
            tree.add(newNode);
            currentNode.next[letterToIndex(text.charAt(initialStart))] = newNode.id; //sets the pointer of currentNode to newNode (currentNode.next[]
            currentNode.hasNeighbors = true;
        }
        return tree;
    }


    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<String>();
        // Implement this function yourself
        
        List<Node> tree = buildSuffixTree(text);
        printSuffixTree(tree); //comment this out
        
        //this is one way to print out the edges of the Suffix Tree
        //this goes in order of A, C, G, T, $ after the root
        //this is a tree traversal algorim. Similiar to BFD (breadth first)
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
        while (!queue.isEmpty()){
            Node currentNode = tree.get(queue.poll());//this takes a node from the tree based on the queue            
            
            System.out.println("Result Node "+currentNode.id); //comment out          
            
            if (currentNode.offset != -1){ //if currentNode is not the root, get the letter positions from currentNode and add ot result
                result.add(text.substring(currentNode.start, currentNode.start + currentNode.offset + 1));//this adds segments of text to the result from start to start+offset+1
            }
            for (int i = 0; i < Node.Letters; i++){//add these to the Q in order of A, C, G, T, $
                if (currentNode.next[i] > 0){
                    queue.add(currentNode.next[i]); //looks at currentNode.next[] and adds neighbors (nextNodes) to the queue if there are any
                }
            }
        }                
        return result;
    }


    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
    
    public void tester() throws IOException{
        new SuffixTree();
        int testCaseStart = 4;
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

        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String line;
        List<String> results = new ArrayList<String>();
        while ((line = inA.readLine()) != null){
           results.add(line); 
        }
           
        // excute the alrogithm 
        List<String> edges = computeSuffixTreeEdges(text);
        
        //compare edges with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out edges from algorithm");
        print(edges);        
        System.out.println("Print out result from test cases");
        for (int j = 0; j < results.size(); j++){
            System.out.println(results.get(j));            
        }
        System.out.println();
        System.out.println("--------------------");
    }
    
    // This will print out the nodes of the Suffix Tree. 
    public void printSuffixTree(List<Node> tree){
        for (int i = 0; i < tree.size(); i++){
            Node currentNode = tree.get(i);            
            System.out.println();
            System.out.print("i "+i+"\tNode id "+currentNode.id+"\tStart "+currentNode.start+"\tOffset "+currentNode.offset+"\t hasNeighbors "+currentNode.hasNeighbors+"\tnext ["+currentNode.next[0]+", "+currentNode.next[1]+", "+currentNode.next[2]+", "+currentNode.next[3]+", "+currentNode.next[4]+"]"); 
        }
    }
}
