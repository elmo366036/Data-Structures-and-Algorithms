
/**
 * Write a description of EulerianCycle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;
import java.util.*;

/*Methodology
 * 1) Create list of lists for the edges. one list for each vertex
 * 2) create two lists, one for the number of incoming edges per vertex and one for the number of outgoing ones 
 *      note: this could be done in a 2d array
 * 3) read in all the edge data
 * 4) for each vertex, count the number of incoming and outgoing edges
 * 5) for each vertes, add the edge to the list of edges for that vertex
 * 6) test if the graph is Eulerian by looking the number of incoming and outgoing edges for each vertex. also make sure they are > 0
 * 7) if Eulerian, run DFS and put together a path
 * 8) print the path in reverse order
 */

public class EulerianCycle {
    public static void main(String[] args) throws IOException {
        //tester();//for testing
        run();
    }
    
    private static void run(){
        Scanner reader = new Scanner(System.in);
        //read the first line
        int V = reader.nextInt(); //number of vertices
        int E = reader.nextInt(); //number of edges
        List[] edgesFrom = new List[V]; //initialize an arraylist of lists, one list for each Vertex V (size V)
        for (int i = 0; i < V; i++) {
            edgesFrom[i] = new ArrayList<Edge>();
                //for each Vertex, initalize the ArrayList of edges
        }
            
        //read the next set of lines (which correspond to the edges E)
        int incoming[] = new int[V];  //arraylist of size V
        int outgoing[] = new int[V]; //arraylist of size V
        //these will be used to sum the total number of incoming and outgoing edges for each vertex V
        for (int i = 0; i < E; i++) {
            int from = reader.nextInt() - 1;    //normalize to 0; starting vertex
            int to = reader.nextInt() - 1;      //normalize to 0; ending vertex
            edgesFrom[from].add(new Edge(from, to));
                // append the edge to the list of edges for the starting vertex (FROM)
            incoming[to]++;   //count the number of inbound edges for the ending vertes (TO)
            outgoing[from]++;//count the number of outbound edges for the starting vertex(FROM)
        }

        //Check to see if the graph is Eulerian
        boolean isEulerian = true;
        for (int i = 0; i < V; i++) {    
            /*go through the vertices and see if there are any that are not balanced
             *   (i.e., any where the number of inbound edges does not equal the number of outbound ones)
             *if so, its not Eulerian.
             *also, if one (or both) inbound or outbound edges is 0, it is not Eulerian
             */                
            if (incoming[i] != outgoing[i] || (incoming[i] == 0 && outgoing[i] == 0)) {
                System.out.println(0);
                isEulerian = false;
                break;
            }
        }
            
        //if it is Eulerian, find the path
        if (isEulerian){
            System.out.println(1);
            List<Integer> eulerianPath = new ArrayList<Integer>();
            dfs(0, eulerianPath, edgesFrom);//start DFS from Vertex 0
            //print out the path in reverse order, normalize from 0 to 1
            for (int i = eulerianPath.size() - 1; i > 0; i--) {
                System.out.print(1 + eulerianPath.get(i) + " ");
            }
            System.out.println(); 
        }              
    }
        
    private static void dfs(int v, List<Integer> path, List<Edge>[] edgesFrom) {//standard DFS from lecture
        for (Edge e : edgesFrom[v]) {//iterate through all edges for Vertex v
            if (!e.used) {
                e.used = true;
                dfs(e.to, path, edgesFrom);
            }
        }
        path.add(v);
    }

    private static class Edge {
        int from, to;
        boolean used = false;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
            //no weight for this edge
        }
    }    
    
    private static void tester() throws IOException{
        int testStart = 1;
        int testEnd = 4;
        
        for (int testNumber = testStart; testNumber <= testEnd; testNumber++){                            
            System.out.println();
            System.out.println("Test Number "+testNumber);
  
            //read input
            String file = "files/EulerianCycle"+testNumber+".txt";
            Scanner reader = new Scanner(new FileInputStream(file));
            System.out.println(file);

            //read the first line
            int V = reader.nextInt(); //number of vertices
            int E = reader.nextInt(); //number of edges
            List[] edgesFrom = new List[V]; //initialize an arraylist of lists, one list for each Vertex V (size V)
            for (int i = 0; i < V; i++) {
                edgesFrom[i] = new ArrayList<Edge>();
                //for each Vertex, initalize the ArrayList of edges
            }
            
            //read the next set of lines (which correspond to the edges E)
            int incoming[] = new int[V];  //arraylist of size V
            int outgoing[] = new int[V]; //arraylist of size V
                //these will be used to sum the total number of incoming and outgoing edges for each vertex V
            for (int i = 0; i < E; i++) {
                int from = reader.nextInt() - 1;    //normalize to 0; starting vertex
                int to = reader.nextInt() - 1;      //normalize to 0; ending vertex
                edgesFrom[from].add(new Edge(from, to));
                    // append the edge to the list of edges for the starting vertex (FROM)
                incoming[to]++;   //count the number of inbound edges for the ending vertes (TO)
                outgoing[from]++;//count the number of outbound edges for the starting vertex(FROM)
            }

            //Check to see if the graph is Eulerian
            boolean isEulerian = true;
            for (int i = 0; i < V; i++) {              
                /*go through the vertices and see if there are any that are not balanced
                 *   (i.e., any where the number of inbound edges does not equal the number of outbound ones)
                 *if so, its not Eulerian.
                 *also, if one (or both) inbound or outbound edges is 0, it is not Eulerian
                 */                
                if (incoming[i] != outgoing[i] || (incoming[i] == 0 && outgoing[i] == 0)) {
                    System.out.println(0);
                    isEulerian = false;
                    break;
                }
            }

            
            //if it is Eulerian, find the path
            if (isEulerian){
                System.out.println(1);
                List<Integer> eulerianPath = new ArrayList<Integer>();
                dfs(0, eulerianPath, edgesFrom);//start DFS from Vertex 0
                //print out the path in reverse order, normalize from 0 to 1
                for (int i = eulerianPath.size() - 1; i > 0; i--) {
                    System.out.print(1 + eulerianPath.get(i) + " ");
                }
                System.out.println(); 
            }
        }
    }
}

