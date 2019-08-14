import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

public class NegativeCycle {
    private static final int inf = Integer.MAX_VALUE;
    
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        // write your code here
	//dist[x][0] = distance dist[x][1] = prev        
        long[][] dist = new long[adj.length][2];
	// initialize dist to infinity
	for (int i = 0; i < dist.length; i++) {
            dist[i][0] = inf;
            dist[i][0] = -1;
        }
        //set S to 0 and initialize dist[0] to 0
        dist[0][0] = 0;
        dist[0][1] = -1;
        //begin BellmanFord        
        for (int k=0; k<adj.length; k++){ //repeat v times where v = adj size which is equal to the number of nodes
            for (int i=0; i<adj.length; i++){//for each v (node) get adjacent list; set u = i
                ArrayList<Integer> adjList = new ArrayList<Integer>();
                adjList = adj[i];
                int u = i;
                for (int j=0; j<adjList.size(); j++){//go through all vertex v in adjacent list u and check to relax
                    int v = adjList.get(j);
                    if (dist[v][0] > dist[u][0] + cost[u].get(j)) {
                        dist[v][0] = dist[u][0] + cost[u].get(j);
                        dist[v][1] = u;
                        if (k == adj.length - 1){//if relax happens at repeated count v (when k = adj.length), then return 1
                            return 1;
                        }
                    }
                }
            }
        }        
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }
}

