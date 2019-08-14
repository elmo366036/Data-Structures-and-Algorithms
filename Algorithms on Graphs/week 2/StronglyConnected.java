import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class StronglyConnected {
    static int count = 1;
    
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj, ArrayList<Integer>[] adjR) {
        //write your code here
        int result = 0;
        int[][] visited = new int[adjR.length][3];
        
        //need to create Gr which is a reverse ajd. maybe create adjR in MAIN
        
        //outer loop DFS
        for (int i = 0; i < adjR.length; i++){
            //System.out.println("REACH: adjList = adj["+i+"]"); //debug
            ArrayList<Integer> adjList = new ArrayList<Integer>();
            adjList = adjR[i];
            //System.out.println("REACH: visited["+i+"][0] = "+visited[i][0]+" visited["+i+"][1] = "+visited[i][1]+" visited["+i+"] = "+visited[i][2]); //debug
            if (visited[i][0] == 0){
                //System.out.println("REACH: call EXPLORE(adjList, visited, i="+i+" count="+count+")"); //debug
                explore(adjR, visited, i);
            }
        }        
        
        
        //perform toposort to create postOrder list
        // orderArray [i][0] = postOrder time of vertex 1
        // orderArray [i][1] = vertex i+1
        int[][] orderArray = new int[visited.length][2];                
        for (int i = 0; i < visited.length; i++){
            orderArray[i][0] = (visited[i][2]);
            orderArray[i][1] = i+1; 
        }
        
        java.util.Arrays.sort(orderArray, java.util.Comparator.comparingDouble(a -> a[0]));
        /* this prints out the array
        System.out.println("orderArray: [vertex][postOrder #]");
        for (int i = orderArray.length - 1; i >=0; i--){
            System.out.print("["+orderArray[i][1]+"]["+orderArray[i][0]+"] ");
        }        
        System.out.println();
        */
        // make new visited
        // reset count
        count = 1;
        int[][] visitedSCC = new int[adj.length][3];    
        //iterate through through adj in the order of orderArray (reverse postorder)
        //if not visited
            //call explore with int i set to the vertex of orderArray
            //increment result by 1
        for (int i = orderArray.length - 1; i >=0; i--){
            //System.out.println("REACH: adjList = adj["+i+"]"); //debug
            ArrayList<Integer> adjList = new ArrayList<Integer>();
            int index = orderArray[i][1] - 1;
            //System.out.println("orderArray["+i+"][1]-1="+(orderArray[i][1]-1)+" & index ="+index);
            adjList = adj[index];
            //System.out.println("REACH: visitedSCC["+index+"][0] = "+visitedSCC[index][0]+" visitedSCC["+index+"][1] = "+visitedSCC[index][1]+" visitedSCC["+index+"][2] = "+visitedSCC[index][2]); //debug
            if (visitedSCC[index][0] == 0){
                //System.out.println("REACH: call EXPLORE(adjList, visitedSCC, index="+index+" count="+count+")"); //debug
                result++;
                explore(adj, visitedSCC, index);                
            }
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
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] adjR = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            adjR[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            //create adjR
            adjR[y - 1].add(x - 1);
        }
        System.out.println(numberOfStronglyConnectedComponents(adj, adjR));
    }
}

