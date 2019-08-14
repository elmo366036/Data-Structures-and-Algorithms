import java.util.Scanner;
import java.util.*;

//this implements the Kruskal algorithm with objects Node and Edge and a array of Nodes

public class ConnectingPoints {
   
    public static class Node { //create a class Node for (x, y) and add to it a parent and a rank; Node has attributes x, y, parent Node, rank
        int x; int y; int parent; int rank;
        public Node(int a, int b, int c) {
            x=a; y=b; parent=c; rank=0;
        }                
    }

    //public static class Edge { //create a class Edge for the edges between nodes. Edge has attributes Node u, Node v, and Distance
    public static class Edge implements Comparable<Edge> {
        int u; int v; double distance;
        public Edge(int a, int b, double c) {
            u=a; v=b; distance=c; 
            this.u = u; this.v=v; this.distance=distance;
        } 
        @Override
        public int compareTo(Edge o) { //use a comparator to compare distances of edges. this can be done in the object
            if (this.distance > o.distance) {return 1;}     
            else if (this.distance < o.distance) {return -1;}
            else {return 0;}
        }
    }
    
    private static void makeSet(int i, Node[] nodes, int[] x, int[] y) {
        nodes[i] = new Node(x[i], y[i], i); //node[i] = a new node with Node.x=x[i], Node.y=y[i], and Node.parent = i
    }

    private static int find(int u, Node[] nodes){ //this is Find with path compression
        if (u != nodes[u].parent) {
            nodes[u].parent = find(nodes[u].parent, nodes);
        }
        return nodes[u].parent;
    }

    private static void union(int u, int v, Node[] nodes){//this is Union by rank heuristic
        int u_id = find(u, nodes);
        int v_id = find(v, nodes);
        if (nodes[u_id].rank > nodes[v_id].rank){
            nodes[v_id].parent = u_id;
        }
        else{
            nodes[u_id].parent = v_id;
            if (nodes[u_id].rank == nodes[v_id].rank){
                nodes[v_id].rank++;
            }
        }
    }
         
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        //write your code here
        int n=x.length;
        Node[] nodes = new Node[n]; //create an array of n Nodes
        //MakeSet(v)
        for (int i=0; i<n; i++){
            makeSet(i, nodes, x, y);
        }
        //need a data structure of edges
        PriorityQueue<Edge> edges = new PriorityQueue<Edge>(); //use PriorityQueue since the Q of edges needs to be sorted
        
        /*
        PriorityQueue<Edge> edges = new PriorityQueue(new Comparator<Edge>(){ //use PriorityQueue since the Q of edges needs to be sorted
            @Override
            public int compare(Edge e1, Edge e2) { //use a comparator to compare distances of edges
                return e1.distance < e2.distance ? -1 : 1; //if e1.distance < e2.distance, return -1, else return 1. 
            }
        });
        */
       
        //need to calculate distances between nodes and add to edges
        for (int i=0; i<n; i++){
            for (int j=i+1; j<n; j++){                
                double distance = Math.sqrt(Math.pow((x[i]-x[j]),2) + Math.pow((y[i]-y[j]),2));
                edges.offer(new Edge(i, j, distance));
            }
        }        
        //run through edges
        while (edges.size()>0){
            Edge currentEdge = edges.poll(); //pop the first edge from the Q. This will always have a minimum distance
            int u = currentEdge.u;
            int v = currentEdge.v;
            if (find(u, nodes) != find(v, nodes)){
                result += currentEdge.distance;
                union(u, v, nodes);
            }
        }                        
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));
    }
}

