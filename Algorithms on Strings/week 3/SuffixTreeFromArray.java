import java.io.*;
import java.util.*;

public class SuffixTreeFromArray {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

       FastScanner(String source) throws IOException{
            File file = new File(source);
            FileReader filereader = new FileReader(file);
            in = new BufferedReader (filereader);            
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

    // Data structure to store edges of a suffix tree.
    public class Edge {
        // The ending node of this edge.
        int node;
        // Starting position of the substring of the text 
        // corresponding to the label of this edge.
        int start;
        // Position right after the end of the substring of the text 
        // corresponding to the label of this edge.
        int end;

        Edge(int node, int start, int end) {
            this.node = node;
            this.start = start;
            this.end = end;
        }
    }

    public class Node {
        int id;
        int parent;
        Map<Character, Integer> children;
        int depth;
        int start;
        int end;
        
        public Node (int id, int parent, Map<Character, Integer> children, int depth, int start, int end){
            this.id = id;
            this.parent = parent;
            this.children = children;
            this.depth = depth;
            this.start = start;
            this.end = end;
        }
    }
    
    // Build suffix tree of the string text given its suffix array suffix_array
    // and LCP array lcp_array. Return the tree as a mapping from a node ID
    // to the list of all outgoing edges of the corresponding node. The edges in the
    // list must be sorted in the ascending order by the first character of the edge label.
    // Root must have node ID = 0, and all other node IDs must be different
    // nonnegative integers.
    //
    // For example, if text = "ACACAA$", an edge with label "$" from root to a node with ID 1
    // must be represented by new Edge(1, 6, 7). This edge must be present in the list tree.get(0)
    // (corresponding to the root node), and it should be the first edge in the list
    // (because it has the smallest first character of all edges outgoing from the root).

    void createNewLeaf(List<Node> nodeList, Node parent, String text, int suffix){
        Node leaf = new Node(nodeList.size(), parent.id, new HashMap<>(), text.length() - suffix, suffix + parent.depth, text.length() - 1);
            //new Node (id = current size of nodeList, parent = parent, children is a new HashMap, depth = text.length - suffix
            //          start = suffix + parent.depth, end = text.length - 1)
            //node is equal to parent
        nodeList.add(leaf);
        parent.children.put(text.charAt(leaf.start), leaf.id);        
    }
    
    Node breakEdge(List<Node> nodeList, Node node, String text, int start, int offset){
        Character startChar = text.charAt(start);
        Character midChar = text.charAt(start + offset);
        Node midNode = new Node(nodeList.size(), node.id, new TreeMap<>(), node.depth + offset, start, start + offset - 1);
        nodeList.add(midNode);
        midNode.children.put(midChar, node.children.get(startChar));
        nodeList.get(node.children.get(startChar)).parent = midNode.id;
        nodeList.get(node.children.get(midChar)).start += offset;
        node.children.put(startChar, midNode.id);
        return midNode;
    }
   
    
    Map<Integer, List<Edge>> SuffixTreeFromSuffixArray(int[] suffixArray, int[] lcpArray, final String text) {
        Map<Integer, List<Edge>> tree = new HashMap<Integer, List<Edge>>();
        // Implement this function yourself
        List<Node> nodeList = new ArrayList<Node>();
        Node root = new Node(0, -1, new TreeMap<>(), 0, -1, -1);
        nodeList.add(root);
        int lcpPrev = 0;
        Node currNode = root;
        for (int i = 0; i < text.length(); i++){
            int suffix = suffixArray[i];
            while (currNode.depth > lcpPrev){
                currNode = nodeList.get(currNode.parent); // why not just =currentNode.parent?
            }
            if (currNode.depth == lcpPrev){
                createNewLeaf(nodeList, currNode, text, suffix); // why nodeList?
            }
            else {
                int edgeStart = suffixArray[i-1] + currNode.depth;
                int offset = lcpPrev - currNode.depth;
                Node midNode = breakEdge(nodeList, currNode, text, edgeStart, offset);
                createNewLeaf(nodeList, midNode, text, suffix);
                currNode = midNode; //???
            }
            if (i < text.length() - 1){
                lcpPrev = lcpArray[i];
            }           
        }
        
        for (int i = 0; i < nodeList.size(); i++){
            Node currentNode = nodeList.get(i);
            if (!currentNode.children.isEmpty()){
                List<Edge> neighbors = new ArrayList<Edge>();
                for (Character c : currentNode.children.keySet()){
                    Node child = nodeList.get(currentNode.children.get(c));
                    neighbors.add(new Edge(child.id, child.start, child.end + 1));
                }
            tree.put(currentNode.id, neighbors);
            }
        }                
        return tree;
    }
                    
    static public void main(String[] args) throws IOException {
        new SuffixTreeFromArray().run();
    }

    public void print(ArrayList<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffixArray = new int[text.length()];
        for (int i = 0; i < suffixArray.length; ++i) {
            suffixArray[i] = scanner.nextInt();
        }
        int[] lcpArray = new int[text.length() - 1];
        for (int i = 0; i + 1 < text.length(); ++i) {
            lcpArray[i] = scanner.nextInt();
        }
        System.out.println(text);
        // Build the suffix tree and get a mapping from 
        // suffix tree node ID to the list of outgoing Edges.
        Map<Integer, List<Edge>> suffixTree = SuffixTreeFromSuffixArray(suffixArray, lcpArray, text);
        ArrayList<String> result = new ArrayList<String>();
        // Output the edges of the suffix tree in the required order.
        // Note that we use here the contract that the root of the tree
        // will have node ID = 0 and that each vector of outgoing edges
        // will be sorted by the first character of the corresponding edge label.
        //
        // The following code avoids recursion to avoid stack overflow issues.
        // It uses two stacks to convert recursive function to a while loop.
        // This code is an equivalent of 
        //
        //    OutputEdges(tree, 0);
        //
        // for the following _recursive_ function OutputEdges:
        //
        // public void OutputEdges(Map<Integer, List<Edge>> tree, int nodeId) {
        //     List<Edge> edges = tree.get(nodeId);
        //     for (Edge edge : edges) {
        //         System.out.println(edge.start + " " + edge.end);
        //         OutputEdges(tree, edge.node);
        //     }
        // }
        //
        int[] nodeStack = new int[text.length()];
        int[] edgeIndexStack = new int[text.length()];
        nodeStack[0] = 0;
        edgeIndexStack[0] = 0;
        int stackSize = 1;
        while (stackSize > 0) {
            int node = nodeStack[stackSize - 1];
            int edgeIndex = edgeIndexStack[stackSize - 1];
            stackSize -= 1;
            if (suffixTree.get(node) == null) {
                continue;
            }
            if (edgeIndex + 1 < suffixTree.get(node).size()) {
                nodeStack[stackSize] = node;
                edgeIndexStack[stackSize] = edgeIndex + 1;
                stackSize += 1;
            }
            result.add(suffixTree.get(node).get(edgeIndex).start + " " + suffixTree.get(node).get(edgeIndex).end);
            nodeStack[stackSize] = suffixTree.get(node).get(edgeIndex).node;
            edgeIndexStack[stackSize] = 0;
            stackSize += 1;
        }
        print(result);
    }
    
        
     public void tester() throws IOException{
        new SuffixTreeFromArray();
        int testCaseStart = 1;
        int testCaseEnd = 3;
        for (int i=testCaseStart; i<=testCaseEnd; i++){
            runTest(i);
        }    
    }

    public void runTest(int testNumber) throws IOException {
        String source = "sample_tests/sample"+testNumber;
        String sourceA = "sample_tests/sample"+testNumber+".a";        
        
        //read source file & process        
        FastScanner scanner = new FastScanner(source);
        String text = scanner.next();
        int[] suffixArray = new int[text.length()];
        for (int i = 0; i < suffixArray.length; ++i) {
            suffixArray[i] = scanner.nextInt();
        }
        int[] lcpArray = new int[text.length() - 1];
        for (int i = 0; i + 1 < text.length(); ++i) {
            lcpArray[i] = scanner.nextInt();
        }        
        System.out.println(text);
        // Build the suffix tree and get a mapping from 
        // suffix tree node ID to the list of outgoing Edges.
        Map<Integer, List<Edge>> suffixTree = SuffixTreeFromSuffixArray(suffixArray, lcpArray, text);
        ArrayList<String> result = new ArrayList<String>();
        // Output the edges of the suffix tree in the required order.
        // Note that we use here the contract that the root of the tree
        // will have node ID = 0 and that each vector of outgoing edges
        // will be sorted by the first character of the corresponding edge label.
        //
        // The following code avoids recursion to avoid stack overflow issues.
        // It uses two stacks to convert recursive function to a while loop.
        // This code is an equivalent of 
        //
        //    OutputEdges(tree, 0);
        //
        // for the following _recursive_ function OutputEdges:
        //
        // public void OutputEdges(Map<Integer, List<Edge>> tree, int nodeId) {
        //     List<Edge> edges = tree.get(nodeId);
        //     for (Edge edge : edges) {
        //         System.out.println(edge.start + " " + edge.end);
        //         OutputEdges(tree, edge.node);
        //     }
        // }
        //
        int[] nodeStack = new int[text.length()];
        int[] edgeIndexStack = new int[text.length()];
        nodeStack[0] = 0;
        edgeIndexStack[0] = 0;
        int stackSize = 1;
        while (stackSize > 0) {
            int node = nodeStack[stackSize - 1];
            int edgeIndex = edgeIndexStack[stackSize - 1];
            stackSize -= 1;
            if (suffixTree.get(node) == null) {
                continue;
            }
            if (edgeIndex + 1 < suffixTree.get(node).size()) {
                nodeStack[stackSize] = node;
                edgeIndexStack[stackSize] = edgeIndex + 1;
                stackSize += 1;
            }
            result.add(suffixTree.get(node).get(edgeIndex).start + " " + suffixTree.get(node).get(edgeIndex).end);
            nodeStack[stackSize] = suffixTree.get(node).get(edgeIndex).node;
            edgeIndexStack[stackSize] = 0;
            stackSize += 1;
        }
        //print(result);
                                        
        //read answer file. in this case, it is one line long. need to break it up 
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);
        ArrayList<String> answer = new ArrayList<String>();
        String line;
        while ((line = inA.readLine()) != null){
            answer.add(line); 
        }
        

        //compare occurances with answer. This will be printout based                 
        System.out.println();
        System.out.println("TEST CASE "+testNumber);
        System.out.println("Print out starting points from algorithm");
        print(result); 
        System.out.println();
        System.out.println("Print out result from test cases");
        print(answer);
        System.out.println();
        System.out.println("--------------------");
    } 
}
