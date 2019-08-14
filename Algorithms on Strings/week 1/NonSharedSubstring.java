import java.io.*;
import java.math.*;
import java.util.*;

//inputs are the strings p and q

class Node
{  
    public static final int Letters =  6;
    public static final int NA      = -1;

    public int next []; //these are pointers to the next node that maps to [A, C, G, T, $]
    int start;          //this is the position in text where the node starts if it is on side 0                          
    int offset;         //this is how many letters after start are included. The total is start to start+offset(+1) 
    public int id;      //node ID
    public boolean hasNeighbors; //is it a leaf? also, this means that the edge ends with a $, also, if next = [-1...] hasNeighbors is false
    
    int generalStart;   //new param this is the positin in text where node starts if it is on side 1
    int side = 1;       //side 1 is p and side 0 is # + q + $ are 0. 
    String suffix = ""; //this stores the suffix of a string that is unique to side 1 (i.e. to p)

    Node ()
    {
        next = new int [Letters];
        Arrays.fill (next, NA); //everytime a node is created without any parameters next is set to [-1...]
    }
    
    Node(int start, int offset, int id){ //in this case, a node is craeted with a start, oddset, and id passed in 
        this();
        this.start = start;
        this.offset = offset;
        this.id = id;
    }   
    
    public void initNextNode() { //this sets the next pointers to -1  [-1...] and sets hasNeighbors to false
        Arrays.fill (next, NA);
        hasNeighbors = false;
    }
    
    List<Integer> getNextNodes(){ //this puts the nextNodes from next[] into a list
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < next.length; i++){
            if (next[i] > 0) {
                result.add(next[i]);
            }
        }
        return result;
    }
}

public class NonSharedSubstring implements Runnable {
    int letterToIndex (char letter)
    {
        switch (letter)
        {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3;
            case '$': return 4; 
            case '#': return 5;
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
            newNode.generalStart = text.length() - 1 - j;// *NEW* generalStart counts down from the end of text 
            tree.add(newNode);
            currentNode.next[letterToIndex(text.charAt(initialStart))] = newNode.id; //sets the pointer of currentNode to newNode (currentNode.next[]
            currentNode.hasNeighbors = true;
        }
        return tree;
    }
    
    public int identifySide(List<Node> tree, List<String> suffixes, String suffix, Node node, String text, int half){//pre-order NLR tree traversal. looks at .side and .suffix
        //System.out.println("node.side "+node.side+"\t node.id "+node.id+"\t node.start "+node.start+"\t node.suffix "+node.suffix+"\t BEGINNING");
        //System.out.println("suffix "+suffix);
        if (!node.hasNeighbors){//if there are no nextNodes 
            node.side = node.start < half + 1 ? 1 : 0; // set node.side to 1 if node.start is less than the length of the first string p and 0 otherwise 
                //set node.side to 1 if edge is in p or # and 0 if in q or $.
                //node.side = 1 if LEFT and 0 if RIGHT
        }        
        else {//if it has neighbors 
            List<Integer> nextNodes = node.getNextNodes();//get those neighbors
            for (int i = 0; i < nextNodes.size(); i++){//iterate through them
                node.side *= identifySide(tree, suffixes, suffix + text.substring(node.start, node.start + node.offset + 1), tree.get(nextNodes.get(i)), text, half);
                    // node.side = node.side * (the integer returned by recursive calls to identifySide)
                    // node.side = node.side * [result of identitySide(..., suffix plus additional suffix of node, nextNode, ...)
                    // which equates to node.side[curr] = node.side[prev]*node.side[of next node when evaluated]
                    // if node.side or the result of identifySide = 0 then node.side=0
                    // if a node has any child where node.size is 0, node.size will be 0 for the node. 
            }
        }
        //System.out.println("node.side "+node.side+"\t node.id "+node.id+"\t node.start "+node.start+"\t node.suffix "+node.suffix+"\t MID_0");
        if (node.side == 1){ //when is node.side not equal to 1?? it could be set to 0 based on the recusion above. This has to do with the start position being past string p
            //System.out.println("node.side "+node.side+"\t node.id "+node.id+"\t node.start "+node.start+"\t node.suffix "+node.suffix+"\t MID_1");
            node.suffix = suffix;
            //System.out.println("node.side "+node.side+"\t node.id "+node.id+"\t node.start "+node.start+"\t node.suffix "+node.suffix+"\t MID_2");
            if (node.start <= half){ // <= half. This causes and nodes with # pointing to them to have # written as a suffix. This makes going through suffixes easier. 
                //System.out.println("node.side "+node.side+"\t node.id "+node.id+"\t node.start "+node.start+"\t node.suffix "+node.suffix+"\t MID_3");
                //System.out.println("Suffix -> text.charAt(node.start) = "+text.charAt(node.start));                          
                node.suffix += text.charAt(node.start); 
            
                //System.out.println("node.side "+node.side+"\t node.id "+node.id+"\t node.start "+node.start+"\t node.suffix "+node.suffix+"\t MID_4");   
                
                if (suffixes.isEmpty() || suffixes.get(0).length() > node.suffix.length()){;                  
                    //System.out.println("suffixes -> \t"+node.suffix+"\t"+node.suffix.charAt(node.suffix.length()-1));
                    if (node.suffix.charAt(node.suffix.length() - 1) != '#'){// if the suffix ends with #, don't use it. 
                        suffixes.add(0, node.suffix);  
                    }                    
                }
            }
        }                
        /*
        System.out.println("node.side "+node.side+"\t node.id "+node.id+"\t node.start "+node.start+"\t node.suffix "+node.suffix+"\t END");
        if (!suffixes.isEmpty()){System.out.print(" \t\t suffixes.get(0) "+suffixes.get(0));}
        System.out.println();
        */
        return node.side;
    }              
    
    String solve (String p, String q) {        
        String text = p+"#"+q+"$";
        List<Node> tree = buildSuffixTree(text);
        
        //printSuffixTree(tree);//comment this out
        
        List<String> sortSuffix = new ArrayList<String>();
        identifySide(tree, sortSuffix, "", tree.get(0), text, p.length());
        
        //printSuffixTree(tree);//comment this out
        
        return sortSuffix.get(0);
    }

    public void run () {
        try {
            BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
            String p = in.readLine ();
            String q = in.readLine ();

            String ans = solve (p, q);

            System.out.println (ans);
        }
        catch (Throwable e) {
            e.printStackTrace ();
            System.exit (1);
        }
    }

    public static void main (String [] args) {
        new Thread (new NonSharedSubstring ()).start ();
    }
    
    public void tester() throws IOException{
        new NonSharedSubstring();
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
        String p = in.readLine();
        String q = in.readLine();

        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String line;
        line = inA.readLine();
        
        // excute the alrogithm 
        String ans = solve(p, q);
        
        //compare edges with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out edges from algorithm");
        System.out.println(ans);        
        System.out.println("Print out result from test cases");
        System.out.println(line);
        System.out.println();
        System.out.println("--------------------");
    }
    
    // This will print out the nodes of the Suffix Tree. 
    public void printSuffixTree(List<Node> tree){
        for (int i = 0; i < tree.size(); i++){
            Node currentNode = tree.get(i);            
            System.out.println();
            System.out.print("i "+i+"\t Node id "+currentNode.id+"\t Start "+currentNode.start+"\t Offset "+currentNode.offset+
                "\t hasNeighbors "+currentNode.hasNeighbors+"\t next ["+currentNode.next[0]+", "+currentNode.next[1]+", "+currentNode.next[2]+
                ", "+currentNode.next[3]+", "+currentNode.next[4]+", "+currentNode.next[5]+"]\t GeneralStart "+currentNode.generalStart+" \t side "+currentNode.side+"\t suffix "+currentNode.suffix); 
        }
        System.out.println();
        System.out.println();
    }
}
    

