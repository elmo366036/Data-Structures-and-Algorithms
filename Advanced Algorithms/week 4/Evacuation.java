import java.io.*;
import java.util.*;


public class Evacuation {
    private static FastScanner in;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();

        FlowGraph graph = readGraph();
        System.out.println(maxFlow(graph, 0, graph.size() - 1));
    }

    private static int maxFlow(FlowGraph graph, int from, int to) {
        int flow = 0;
        /* your code goes here */
        
        //Edmonds Karp
        int path = 0;
        boolean augment = true;
        while (augment){ //keep augmenting until it cannot be done
            Map<Integer, Integer> pathData = computePath_BFS(graph, from, to);//this provides data for the Path
            if (pathData.get(to) == null){break;}//could use return flow instead of break
            augment = recalculateFlow(graph, to, pathData);
        }

        //sum up the flows from the source (from). this is the max flow. could also use flows to the sink (to)
        List<Integer> edgeList = graph.getIds(from);
        for (int i = 0; i < edgeList.size(); i++){
            Edge edge = graph.getEdge(edgeList.get(i));        
            if (edge.capacity > 0) {//exclude flows that are negative)
                flow += edge.flow;
            }
        }
        
        return flow;
    }

    private static Map<Integer, Integer> computePath_BFS(FlowGraph graph, int from, int to){
        //this runs BFS on G to identify the shortest path from Source to T (sink)
        //do not need to compute Gr.
        //the path can be reconstructed using the data in the hashmap
        //the hashmap is of the form (node b: node a) which is an edge
        
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(from);
        boolean[] visited = new boolean[graph.size()];//stores visited sites
        Map<Integer, Integer> pathData = new HashMap<Integer, Integer>(); 
            //this is a map of node pairs. node B to node A
            //this is used to store backward segments for in G from S to T (from to to)
            //this will store all the results of the BFS until the sink (to) is reached. the Path can be derived from this
            //no need to compute Gr
        while (!queue.isEmpty()){
            Integer vertex = queue.poll(); //removes the head of the queue and returns it
            visited[vertex] = true; 
            List<Integer> edgeList = graph.getIds(vertex);
            for (int i = 0; i < edgeList.size(); i++){
                Edge edge = graph.getEdge(edgeList.get(i));
                if (!visited[edge.to] && edge.flow < edge.capacity){
                    //if flow = capacity, don't use it. also, if the next node has already been visited, don't use it. 
                    //track visited because there can be loops. 
                    pathData.put(edge.to, vertex); //(node b, node a)
                    queue.add(edge.to);
                    if (edge.to == to){break;} //stop at to which is the sink
                }
            }
        }
        return pathData;               
    }

    private static boolean recalculateFlow(FlowGraph graph, int to, Map<Integer, Integer> pathData){
        //this rebuilds a path using pathData and works backwards from to (Vertex B to Vertex A)
        int current = to;
        int minCapacity = Integer.MAX_VALUE;
        List<Integer> Path = new LinkedList<Integer>(); //the path computed from pathData (i.e., from the BFS)
        while (true){//this adds edges to Path. It adds them to the front of Path.
            int previous = pathData.get(current); //set previous to vertex A
            List<Integer> edgeList = graph.getIds(previous); //this gets all the edges of the previous vertex 
            for (int i = 0; i < edgeList.size(); i++){ //iteratge over all edges of previous vertex
                Edge edge = graph.getEdge(edgeList.get(i)); //get the data of each edge
                if (edge.to == current && edge.from == previous && edge.flow < edge.capacity){
                    //compare it. the first two items are guards; edge.to must = B and edge.from must = A
                    //and flow must be less than capacity or we don't want to use it
                    Path.add(0, edgeList.get(i));//add this path to the beginning of the list
                    minCapacity = Math.min(minCapacity, edge.capacity - edge.flow);//the minimum capacity is constantly updated for Path)
                    break;
                }
            }
            current = previous; //move backward one vertex
            if (previous == 0){break;}                    
        }
        for (int i = 0; i < Path.size(); i++){
                graph.addFlow(Path.get(i), minCapacity); //for each edge in Path, add a flow to it which is min cap
        }
        return (minCapacity < Integer.MAX_VALUE && minCapacity > 0); 
            //Augment as long as min cap is less than MAX and greated than 0. I.e., that it was set and greater than 0
    }
    
    static FlowGraph readGraph() throws IOException {
        int vertex_count = in.nextInt();
        int edge_count = in.nextInt();
        FlowGraph graph = new FlowGraph(vertex_count);

        for (int i = 0; i < edge_count; ++i) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1, capacity = in.nextInt();
            graph.addEdge(from, to, capacity);
        }
        return graph;
    }

    static class Edge {
        int from, to, capacity, flow;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    /* This class implements a bit unusual scheme to store the graph edges, in order
     * to retrieve the backward edge for a given edge quickly. */
    static class FlowGraph {
        /* List of all - forward and backward - edges */
        private List<Edge> edges;

        /* These adjacency lists store only indices of edges from the edges list */
        private List<Integer>[] graph;

        public FlowGraph(int n) {
            this.graph = (ArrayList<Integer>[])new ArrayList[n];
            for (int i = 0; i < n; ++i)
                this.graph[i] = new ArrayList<>();
            this.edges = new ArrayList<>();
        }

        public void addEdge(int from, int to, int capacity) {
            /* Note that we first append a forward edge and then a backward edge,
             * so all forward edges are stored at even indices (starting from 0),
             * whereas backward edges are stored at odd indices. */
            Edge forwardEdge = new Edge(from, to, capacity);
            Edge backwardEdge = new Edge(to, from, 0);
            graph[from].add(edges.size());
            edges.add(forwardEdge);
            graph[to].add(edges.size());
            edges.add(backwardEdge);
        }

        public int size() {
            return graph.length;
        }

        public List<Integer> getIds(int from) {
            return graph[from];
        }

        public Edge getEdge(int id) {
            return edges.get(id);
        }

        public void addFlow(int id, int flow) {
            /* To get a backward edge for a true forward edge (i.e id is even), we should get id + 1
             * due to the described above scheme. On the other hand, when we have to get a "backward"
             * edge for a backward edge (i.e. get a forward edge for backward - id is odd), id - 1
             * should be taken.
             *
             * It turns out that id ^ 1 works for both cases. Think this through! */
            edges.get(id).flow += flow;
            edges.get(id ^ 1).flow -= flow;
        }
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
    
    void tester() throws IOException{
        int testCaseStart = 1;
        //int testCaseEnd = 1;
        int testCaseEnd = 36;
        for (int i=testCaseStart; i<=testCaseEnd; i++){
            runTest(i);
        }                                                         
    }

    void printGraph(FlowGraph graph){
        System.out.println();
        System.out.println("+---------------------+");
        System.out.println("PRINT GRAPH");
        System.out.println("Size: \t"+graph.size());
        for (int i = 0; i < graph.size(); i++){//this iterates through the vertices 0-N. each index of graph is a vertex
            List<Integer> edgeList = graph.getIds(i);//this provides the list of edges for that vertex
            System.out.print("Vertex: "+i);
            for (int j = 0; j < edgeList.size(); j++){//this iterates through the edges of each vertex
                Edge edge = graph.getEdge(edgeList.get(j));//forward edges have even IDs and backward edges have odd IDs
                System.out.print(" \t Edge: "+edgeList.get(j)+"  FROM "+edge.from+"  TO "+edge.to+"  CAP "+edge.capacity+"  FL "+edge.flow);
            }
            System.out.println();
        }
        System.out.println("+---------------------+");
        System.out.println();
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
        FlowGraph graph = readGraph();
        
        //print out the processed input
        //printGraph(graph);              
        
        //read answer file
        File fileA = new File(sourceA);
        FileReader fileReaderA = new FileReader(fileA);
        BufferedReader inA = new BufferedReader(fileReaderA);        
        String answerString = inA.readLine();
        int answer = Integer.parseInt(answerString);	
        
        //run the algorithm

          //look at pathData
        //testComputePath_BFS(graph, 0, graph.size()-1);
               
        int result = maxFlow(graph, 0, graph.size() - 1);
                
        //print out the modified input
        //printGraph(graph);   

        
        //compare result with answer
        System.out.print("test case "+testNumber);
        if (result != answer){
            System.out.println(" \t FAIL   ---> answer "+answer+" \t result "+result);
        }
        else {
            System.out.println(" \t SUCCESS");
        }
    }
    
    void testComputePath_BFS(FlowGraph graph, int from, int to){
        Map<Integer, Integer> path = new HashMap<Integer, Integer>();
        path = computePath_BFS(graph, from, to);
        System.out.println("+---------------------+");
        System.out.println("PRINT GR PATH DATA");
        for (Integer i : path.keySet()){
            System.out.print("("+i+","+path.get(i)+") ");
        }
        System.out.println();
        System.out.println("+---------------------+");        
    }
}
