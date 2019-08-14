import java.util.*;

public class ShortestPaths {
    private static final long inf = Long.MAX_VALUE;
    
    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance, int[] reachable, int[] shortest) {
        //write your code here     

        List<Integer> negCycleNodes = new ArrayList<Integer>();
        //initialize reachable[s] as 1 and distance[s] as 0. reachable means is this node reachable by s
        distance[s] = 0;
        reachable[s] = 1;
        //begin BellmanFord; modify by checking if dist[u][0] is not infinity; update rechability[]      
        for (int k=0; k<adj.length; k++){ //repeat v times where v = adj size which is equal to the number of nodes
            for (int i=0; i<adj.length; i++){//for each v (node) get adjacent list; set u = i
                ArrayList<Integer> adjList = new ArrayList<Integer>();
                adjList = adj[i];
                int u = i;
                for (int j=0; j<adjList.size(); j++){//go through all vertex v in adjacent list u and check to relax
                    int v = adjList.get(j);
                    //need to ee if k = adj.length. if so, need to put v into array Q
                    if (distance[u] != inf && distance[v] > distance[u] + cost[u].get(j)) {//check if dist = inf?? have to check this because the origin is not 0
                        if (k == adj.length - 1){//if relax happens at repeated count v (when k = adj.length). do not update distance DOESN'T MATTER
                            negCycleNodes.add(v);
                        }                       
                        else {
                            distance[v] = distance[u] + cost[u].get(j);
                            reachable[v] = 1;
                        }
                    }
                }
            }
        }        
        //print nedCycleNodes
        /*
        System.out.println("NegCycleNodes");
        for (int i=0; i<negCycleNodes.size(); i++){
            System.out.print(negCycleNodes.get(i)+" ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
        */
        //run BFS on negCycleNodes and find nodes reachable from s. do not need prev. this implies a new visited array
        int[] visitedBFS = new int[adj.length];        
        while (negCycleNodes.size()>0){
            int u = negCycleNodes.get(0);
            negCycleNodes.remove(0);
            visitedBFS[u] = 1; 
            shortest[u] = 0; //set shortest of u to 0
            ArrayList<Integer> adjList = new ArrayList<Integer>();
            adjList = adj[u];            
            for (int j = 0; j < adjList.size(); j++){
                int v = adjList.get(j);
                if (visitedBFS[v] == 0){
                    negCycleNodes.add(v);
                    visitedBFS[v] = 1;
                    shortest[v] = 0;
                }
            }
        }   
        distance[s] = 0;
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
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }

}

