import java.util.*;

public class Dijkstra {
    private static final int inf = Integer.MAX_VALUE;
	
    public static class Node implements Comparable<Node> {
        int index;
	long distance;
		
	public Node(int index, long distance) {
            this.index = index;
            this.distance = distance;
        }
	//this specifies natural ordering	
	@Override
	public int compareTo(Node o) {
	    if (this.distance > o.distance) return 1;
	    else if (this.distance < o.distance) return -1;
	    else return 0;
	}
    }
	
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
	//write your code here
	//dist[x][0] = distance dist[x][1] = prev
	int[][] dist = new int[adj.length][2];
	// initialize dist to infinity
	for (int i = 0; i < dist.length; i++) {
            dist[i][0] = inf;
            dist[i][1] = -1;
        }
        //initialize dist[s] to 0
        dist[s][0] = 0;
        dist[s][1] = 0;
	//create new priority queue. add s to it
	PriorityQueue<Node> queue = new PriorityQueue<Node>();
	queue.add(new Node(s, dist[s][0]));
	//while loop
	while(!queue.isEmpty()){
	    Node u_Node = queue.remove();
	    int u = u_Node.index;
            ArrayList<Integer> adjList = new ArrayList<Integer>();
            adjList = adj[u]; 
            for (int i=0; i<adjList.size(); i++){
                int v = adjList.get(i);
		if (dist[v][0] > dist[u][0] + cost[u].get(i)) {
                    dist[v][0] = dist[u][0] + cost[u].get(i);
                    dist[v][1] = u;
                    queue.add(new Node(v, dist[v][0]));
                }                
            }
        }
	//check result	
	if(dist[t][0] == inf)
	   return -1;
        return dist[t][0];
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}

