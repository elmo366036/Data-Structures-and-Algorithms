import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.*;

public class BFS {
    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        //write your code here
        int result = 0;
        int order = 0;
        int[][] distance = new int[adj.length][3];
        List<Integer> Q = new ArrayList<Integer>();
        
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
        //print out adj
        /*
        for (int z = 0; z < adj.length; z++){
            ArrayList<Integer> adjListTEMP = new ArrayList<Integer>();
            adjListTEMP = adj[z];
            System.out.print("Node["+z+"] ");
            for (int zz = 0; zz < adjListTEMP.size(); zz++){
                System.out.print(adjListTEMP.get(zz)+" ");
            }
            System.out.println();
        }        
        */
        //initialize distance to -1
        for (int i = 0; i < adj.length; i++){
            distance[i][0] = -1;
            distance[i][1] = -1;
            distance[i][2] = -1;
        }
        
        //run BFS
        //initialize distance[s]
        distance[s][0] = 0;
        distance[s][2] = 0;
        //initialize Q
        Q.add(s);   
        while (Q.size()>0){
            int u = Q.get(0);
            //System.out.println("u = "+u+"\t Q.size() = "+Q.size());
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
                    //System.out.println("distance["+vertex+"] = \t"+distance[vertex][0]+"\t"+distance[vertex][1]+"\t"+distance[vertex][2]);
                    }
                }            
        }
        
        //print out distance map
        /*
        System.out.println();
        System.out.println("Result");
        for (int i = 0; i < adj.length; i++){
            System.out.println("Distance["+i+"] -> \t Node "+(i+1)+"\t"+distance[i][0]+"\t"+distance[i][1]+"\t"+distance[i][2]);
        }
        */
        //calculate min segments
        if (distance[s][0] == -1 | distance[t][0] == -1){
            return -1;
        }
        else {
            result = distance[t][0] - distance[s][0];
        }
        
        return result;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }
}

