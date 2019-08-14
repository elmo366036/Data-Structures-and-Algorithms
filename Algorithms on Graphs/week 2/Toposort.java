import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Toposort {

    static int count = 1;
    
    private static int Topo(ArrayList<Integer>[] adj) {
        int result = 0;
        //write your code here
        int[][] visited = new int[adj.length][3];
        // visited[k][0] = true false for k
        // visited[k][1] = previsit count for k
        // visited[k][2] = postvisit count for k

        /* 
         * print out adj
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
        //this fully populates adjacent neighbor lists in adj
        /*
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
        
        /*
         *print out updated adj
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
       
        //Sets visited array to 0 - DO NOT NEED TO DO THIS!!! TAKES UP TO .5 sec
        /*
        for (int i = 0; i < visited.length; i++){
            visited[i][0] = 0;
            visited[i][1] = 0;
            visited[i][2] = 0;
        }
        */
        //outer loop 
        for (int i = 0; i < adj.length; i++){
            //System.out.println("REACH: adjList = adj["+i+"]"); //debug
            ArrayList<Integer> adjList = new ArrayList<Integer>();
            adjList = adj[i];
            //System.out.println("REACH: visited["+i+"][0] = "+visited[i][0]+" visited["+i+"][1] = "+visited[i][1]+" visited["+i+"] = "+visited[i][2]); //debug
            if (visited[i][0] == 0){
                //System.out.println("REACH: call EXPLORE(adjList, visited, i="+i+" count="+count+")"); //debug
                explore(adj, visited, i);
            }
        }
        
        /*
        //debug
        System.out.println("REACH: "+ (x+1) +" visited["+x+"][0] = "+visited[x][0]+" visited["+x+"][1] = "+visited[x][1]+" visited["+x+"][2] = "+visited[x][2]); 
        System.out.println("REACH: "+ (y+1) +" visited["+y+"][0] = "+visited[y][0]+" visited["+y+"][1] = "+visited[y][1]+" visited["+x+"][2] = "+visited[y][2]); 
        */
        /*
        for (int c = 0; c < visited.length; c++){
            System.out.println("REACH: visited["+c+"][0]="+visited[c][0]+" visited["+c+"][1]="+visited[c][1]+" visited["+c+"][2]="+visited[c][2]);
        }
          */ 
       
        //need to sort visited by postVisitedCount -> visited[x][2] in descending order. This could probably be done in Explore
        int[][] orderArray = new int[visited.length][2];                
        for (int i = 0; i < visited.length; i++){
            orderArray[i][0] = (visited[i][2]);
            orderArray[i][1] = i+1; 
        }
        
        java.util.Arrays.sort(orderArray, java.util.Comparator.comparingDouble(a -> a[0]));
             
        for (int i = orderArray.length - 1; i >=0; i--){
            System.out.print(orderArray[i][1]+" ");
        }
             

       
          
        return result;
    }

    public static void explore(ArrayList<Integer>[] adj, int[][] visited, int i){
        visited[i][0] = 1;
        visited[i][1] = count; //previsit count
        //System.out.println("EXPLORE: visited["+i+"][0] = "+visited[i][0]+" visited["+i+"][1] = "+visited[i][1]+" visited["+i+"][2] = "+visited[i][2]);
        count++;
        for (int j = 0; j < adj[i].size(); j++){
            //System.out.println("EXPLORE: neighbor -> adjList.get("+j+") = "+adj[i].get(j));
            if (visited[adj[i].get(j)][0] == 0){
                explore(adj, visited, adj[i].get(j));
            }
        }
        visited[i][2] = count; //postvisit count
        //potentialy use this spot to place i in order and then sort?
        
        count++;
    }       
    
    
    private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
        int used[] = new int[adj.length];
        ArrayList<Integer> order = new ArrayList<Integer>();
        //write your code here
        return order;
    }

    private static void dfs(ArrayList<Integer>[] adj, int[] used, ArrayList<Integer> order, int s) {
      //write your code here
      
      
      
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
        }
        //ArrayList<Integer> order = toposort(adj);
        Topo(adj);
        //new method call
        /*
        for (int x : order) {
            System.out.print((x + 1) + " ");
        }
        */
    }
}

