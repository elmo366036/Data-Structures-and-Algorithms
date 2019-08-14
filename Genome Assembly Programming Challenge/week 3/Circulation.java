
/**
 * Write a description of Circulation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Circulation {
    private static class Edge { //from Evacuation with additionals
        int from, to, l, c, cR, flow, id;

        public Edge(int from, int to, int l, int c, int id) {
            this.from = from;   // u                from Evacuation
            this.to = to;       // v                from Evacuation
            this.l = l;         // lower bound      new
            this.c = c;         // capacity         from Evacuation
            this.cR = c - l;    // reserve capacity new
                                //  (i.e. usable capacity)
            this.flow = 0;      // flow             from Evacuation  
            this.id = id;       //                  new
        }
    }

    private static class FlowGraph { //from Evacuation with additions
        int[] flowMap;      // new (dv Demand Function)
        int flowResult = 0; // new (D)
        List<Edge> edges;   // List of all - forward and backward - edges */ 
        List[] graph;       // These adjacency lists store only indices of edges
                            //  from the edges list 

        public FlowGraph(int numVertices) {
            this.graph = new ArrayList[numVertices + 2];    //modified (add a source & sink)
                                                            //graph[numVertices] is the Source
                                                            //graph[numVertices + 1] is the Sink
            for (int i = 0; i < numVertices + 2; ++i) {     //modified (add a source & sink)
                this.graph[i] = new ArrayList<Integer>();   //creates a list of lists
            }
            this.edges = new ArrayList<Edge>();             //creates a list of Edges
            this.flowMap = new int[numVertices + 2];       //new   creates a list [source, v0, v1, ... vn, sink]
                                                            //      amount of flow per node
                                                            //      flowMap[numVertices] is the Source
                                                            //      flowMap[numVertices + 1] is the Sink
                                                            //      default value is 0
        }

        public void addEdge(int from, int to, int l, int c) {
            /* Note that we first append a forward edge and then a backward edge,
             * so all forward edges are stored at even indices (starting from 0),
             * whereas backward edges are stored at odd indices. */  
            /* Forward edge goes from node "From" to node "To" 
             * 
             * Note: edges normalized to 0 (to and from - 1)
             */     
            
            Edge forward = new Edge(from, to, l, c, edges.size());  //additional arguments
                                                                    //edges.sze() increments
            this.graph[from].add(edges.size()); //add "this."
            this.edges.add(forward);            //add "this."

            Edge backward = new Edge(to, from, 0, 0, edges.size()); //additional arguments
                                                                    //capacity and lower bound are 0
                                                                    //edges.size increments
            this.graph[to].add(edges.size());   //add "this."
            this.edges.add(backward);           //add "this."
            
            //flowMap is modified based on lower bounds. The answer is a residual flow
            flowMap[from] += l;                 //new   increase the flow out of node "From" ny lower bound l
            flowMap[to] -= l;                   //new   decrease the flow into node "To" by lower bound l 
        }

        public void addFlow(int flow, int edgeId) {
            /* To get a backward edge for a true forward edge (i.e id is even), we should get id + 1
             * due to the described above scheme. On the other hand, when we have to get a "backward"
             * edge for a backward edge (i.e. get a forward edge for backward - id is odd), id - 1
             * should be taken.
             *
             * It turns out that id ^ 1 works for both cases. Think this through! */
            this.edges.get(edgeId).flow += flow;        //add "this."
            this.edges.get(edgeId ^ 1).flow -= flow;    //add "this."

            //if flow is incremented, reserve flow must be decemented by same amount
            this.edges.get(edgeId).cR -= flow;          //new   
            this.edges.get(edgeId ^ 1).cR += flow;      //new   
        }
    }  
    
    public static void main(String[] args) throws FileNotFoundException {
        //tester();
        
        //replace FastScanner and readGraph from Evacuation 
         
        Scanner reader = new Scanner(System.in);                        
        int numVertices = reader.nextInt();
        int numEdges = reader.nextInt();
        FlowGraph graph = new FlowGraph(numVertices);
        
        for (int i = 0; i < numEdges; i++) {
            int from = reader.nextInt() - 1;
            int to = reader.nextInt() - 1;
            int l = reader.nextInt();
            int c = reader.nextInt();
            graph.addEdge(from, to, l, c);
        }

        Circulation circulation = new Circulation();
        circulation.findCirculation(graph, numVertices, numEdges);

        System.out.println();
        
    }

    public static void tester() throws FileNotFoundException{
        for (int i = 1; i <= 3; i++){

            Scanner reader = new Scanner(new FileInputStream("files/test"+i+".txt"));

            int numVertices = reader.nextInt();
            int numEdges = reader.nextInt();
            FlowGraph graph = new FlowGraph(numVertices);
            for (int j = 0; j < numEdges; j++) {
                int from = reader.nextInt() - 1;    //normalize to 0
                int to = reader.nextInt() - 1;      //normalize to 0
                int l = reader.nextInt();           //lower bound
                int c = reader.nextInt();           //capacity
                graph.addEdge(from, to, l, c);
                //printGraph(graph, "Read Input");    //for debug
            }
            
            Circulation circulation = new Circulation();
            circulation.findCirculation(graph, numVertices, numEdges);

            System.out.println();
        }
    }
    
    private static void findCirculation(FlowGraph graph, int numVertices, int numEdges) {
 
        //printGraph(graph, "Initial Graph"); //for debug
        
        //Add source and sink; flowMap is used to do this
        //positive number in flowMap will create a sink, a negative one a source
        for (int vertex = 0; vertex < numVertices; vertex++) {//create source (nodeID = numVertices)
            if (graph.flowMap[vertex] < 0) {
                graph.addEdge(numVertices, vertex, 0, -graph.flowMap[vertex]);
            }

            if (graph.flowMap[vertex] > 0) {//create sink (nodeID = numVertices + 1)
                graph.addEdge(vertex, numVertices + 1, 0, graph.flowMap[vertex]);
                graph.flowResult += graph.flowMap[vertex];
            }
        }

        //printGraph(graph, "Updated Graph with Source and Sink"); //for debug
        
        //run a modified version of maxFlow on the graph
        SearchResult result = maxFlow(graph, numVertices, numVertices + 1);

        if (!result.state){
            System.out.println("NO");
        }else{
            System.out.println("YES");
            for (int edge = 0; edge < numEdges; edge++) {
                Edge curr = graph.edges.get(edge*2);    //get all the even ordered edges
                                                        //this corresponds to the forward edges
                System.out.println(curr.flow + curr.l); //add flow to lower bound
            }
        }
        
        //printGraph(graph, "End Graph"); //for debug
    }

    private static void printGraph(FlowGraph graph, String s){
        System.out.println(s);
        for (int i = 0; i < graph.flowMap.length; i++){
            System.out.print(graph.flowMap[i]+" ");
        }  
        System.out.println();
        System.out.println("Edges");
        for (int edge = 0; edge < graph.edges.size(); edge++) {
            Edge curr = graph.edges.get(edge);
            System.out.println(curr.from +" "+curr.to+" "+curr.c+" "+curr.l+" "+curr.flow);
        }        
    }
    
    private static SearchResult maxFlow(FlowGraph graph, int from, int to) {
        //from Evacuation maxFlow with modifications
        //Edmonds Karp
        int flow = 0;
        while (true) {
            List<Integer> path = new ArrayList<>();
            SearchResult result = search(graph, from, to, path);
            if (!result.state) {//exit condition - if result.state = false there are no more augments to make
                result.value = flow;
                if (flow == graph.flowResult){
                    result.state = true;
                }
                return result;
            }
            for (Integer edgeId : path) {//add flows until result.state = false
                graph.addFlow(result.value, edgeId);
            }
            flow += result.value;
        }
    }

    private static SearchResult search(FlowGraph graph, int from, int to, List<Integer> path) {
        //from Evacuation (compute_BFS combined with recalculateFlow) with modifications
        int val = Integer.MAX_VALUE;
        boolean pathExists = false;
        int graphSize = graph.graph.length;

        int[] distance = new int[graphSize];
        Arrays.fill(distance, Integer.MAX_VALUE);
        ParentMap[] parent = new ParentMap[graphSize];  //list of ParentMaps
                                                        //similiar to pathData
                                                        //recreate this everytime search is run
                                                        //should be empty if there is a circulation

        //initialize queue for BFS
        Queue<Integer> qu = new LinkedList<Integer>();
        distance[from] = 0;
        qu.add(from);

        //start BFS
        while (!qu.isEmpty()) {
            int curr = qu.remove();
            for (Integer edgeId : (List<Integer>) graph.graph[curr]) {
                Edge currEdge = graph.edges.get(edgeId);
                if (currEdge.cR > 0 && distance[currEdge.to] == Integer.MAX_VALUE) {
                    distance[currEdge.to] = distance[curr] + 1;
                    parent[currEdge.to] = new ParentMap(curr, edgeId); //pathData.put(edge.to, vertex)
                    qu.add(currEdge.to);
                    if (currEdge.to == to) { //This ends BFS and starts recomputeFlow
                        //end BFS
                        //begin recomputePath
                        SearchResult result = new SearchResult(); //recreate evertime recomputePath is run
                        result.value = Integer.MAX_VALUE;
                        while (true){
                            path.add(0, edgeId);
                            result.value = Math.min(result.value, graph.edges.get(edgeId).cR);
                            if (curr == from){
                                break;
                            }
                            edgeId = parent[curr].edgeId;
                            curr = parent[curr].from;
                        }
                        result.state = true;
                        return result; //return a boolean and a value MIN(MAX or reserve capacity)
                        //end recomputePath
                    }
                }
            }
        }
        //printParentMap(parent); //for debug
        return new SearchResult();
    }
    
    //for debug
    private static void printParentMap(ParentMap[] parent){
        System.out.println("ParentMap");
        System.out.println(parent.length);
        for (int i = 0; i < parent.length; i++){
            if (parent[i] != null){
                System.out.println(i+" "+parent[i].from+" "+parent[i].edgeId);            
            }
        }
    }
    
    private static class ParentMap {
        //this is an object representing a "from" node and an edge that connects to it 
        //use this to track the path in search (BFS + recalculatePath
        Integer from, edgeId;

        public ParentMap() {
            this.from = null;
            this.edgeId = null;
        }

        public ParentMap(Integer from, Integer edgeId) {
            this.from = from;
            this.edgeId = edgeId;
        }
    }

    private static class SearchResult {
        boolean state = false;
        int value;

        public SearchResult() {
        }

        public SearchResult(boolean state, int value) {
            this.state = state;
            this.value = value;
        }
    }
}