import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.*;

public class Bipartite {
    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
        int result = 0;
        int order = 0;
        int[][] distance = new int[adj.length][3];
        List<Integer> Q = new ArrayList<Integer>();
        int s = 0;
        
        //this fully populates adjacent neighbor lists in adj
        for (int a = 0; a < adj.length; a++){
            ArrayList<Integer> nodeAdjList = new ArrayList<Integer>();
            nodeAdjList = adj[a];
            for (int b = 0; b < nodeAdjList.size(); b++){
                int n = nodeAdjList.get(b);
                ArrayList<Integer> nodeAdjListTarget = new ArrayList<Integer>();
                nodeAdjListTarget = adj[n];
                if (!adj[n].contains(a)){
                    adj[n].add(a);
                }
            }
        }        
        //initialize distance to -1
        for (int i = 0; i < adj.length; i++){
            distance[i][0] = -1;
            distance[i][1] = -1;
            distance[i][2] = -1;
        }
        
        //run BFS
        //initialize distance[s] - s = 0
        distance[s][0] = 0;
        distance[s][2] = 0;
        //initialize Q
        Q.add(s);   
        while (Q.size()>0){
            int u = Q.get(0);
            Q.remove(0);
            ArrayList<Integer> adjList = new ArrayList<Integer>();
            adjList = adj[u];            
            for (int j = 0; j < adjList.size(); j++){
                int vertex = adjList.get(j);
                if (distance[vertex][0] == -1){
                    Q.add(vertex);
                    order++;
                    distance[vertex][0] = distance[u][0] + 1;
                    distance[vertex][1] = u;
                    distance[vertex][2] = order; //order
                    }
                // check if bipartite
                else {
                    if (distance[vertex][0] == distance[u][0]){
                        return 0;}
                }
                }            
        }                           
        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(bipartite(adj));
    }
}

