import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Reachability {
    static int count = 1;
    
    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
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
       
        //Sets visited array to 0
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
        
        for (int c = 0; c < visited.length; c++){
            System.out.println("REACH: visited["+c+"][0]="+visited[c][0]+" visited["+c+"][1]="+visited[c][1]+" visited["+c+"][2]="+visited[c][2]);
        }
        */
        for (int k = 0; k < visited.length; k++){
            if (visited[x][1] >= visited[k][1] && visited[x][2] <= visited[k][2]){
                if (visited[y][1] >= visited[k][1] && visited[y][2] <= visited[k][2]){
                    return 1;
                }
            }
        }         
        return 0;
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
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }
    
    public static void tester() throws IOException{
        Scanner scanner = new Scanner(System.in);
        
        File file = new File("tests/01.txt");
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        int[] tall = new int[valueStr.length];
        for (int i = 0; i < valueStr.length; i++){
            tall[i] = Integer.parseInt(valueStr[i]);
        }
        for (int i = 0; i < tall.length-1; i=i+2){
            System.out.println(tall[i]+"\t"+tall[i+1]);
        }
        
        System.out.println(Arrays.asList(tall));
        
        int n = tall[0];
        int m = tall[1];
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 2; i < m*2; i=i+2) {
            int x, y;
            x = tall[i];
            y = tall[i+1];
            adj[y - 1].add(x - 1);
        }
        int x = tall[tall.length - 2] - 1;
        int y = tall[tall.length - 1] - 1;
        System.out.println(reach(adj, x, y));        
    }
}

