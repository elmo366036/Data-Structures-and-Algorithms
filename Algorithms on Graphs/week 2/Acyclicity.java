import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {
    static int count = 1;    
    
    private static int acyclic(ArrayList<Integer>[] adj) {
        //write your code here
        int result = 0;
        //write your code here
        int[][] visited = new int[adj.length][3];       
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
        for (int i = 0; i < visited.length; i++){
            visited[i][0] = 0;
            visited[i][1] = 0;
            visited[i][2] = 0;
        }
        
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
        for (int c = 0; c < visited.length; c++){
            System.out.println("REACH: visited["+c+"][0]="+visited[c][0]+" visited["+c+"][1]="+visited[c][1]+" visited["+c+"][2]="+visited[c][2]);
        }
        
       
        //for each neighboring vertex in for a vertex in adj, check if the postVisitCount of vertex is less than the postVisitCount of the neighboring vertex
       
        for (int i = 0; i < adj.length; i++){
            ArrayList<Integer> adjList = adj[i];
            for (int j = 0; j < adjList.size(); j++){
                int postVisitCountV1 = visited[i][2];
                int postVisitCountV2 = visited[adjList.get(j)][2];
                if (postVisitCountV1 < postVisitCountV2){
                    result++;
                    }                            
            }
        }
       
        if (result > 0){return 1;}
        else {return 0;}
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
        count++;
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
        System.out.println(acyclic(adj));
    }
}

