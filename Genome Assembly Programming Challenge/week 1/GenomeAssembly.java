
/**
 * Write a description of GenomeAssembly here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/* Methodology
 * 
 * 1) read input (reads) similiar to course problems (FastScanner)
 * 2) create overlap graph 
 * 2a)  use a class Vertex to store information about the vertexes, weights, etc.
 * 2b)  store each Vertex in an array
 * 2c)  create overlap graph by connecting vertices and determining weights
 * 2d)  connect successive vertices and determine weight by 
 *      "The length of each edge in the graph as the read length minus the length 
 *         of the shared suffix and prefix."
 * 3) find hamiltonian path 
 */

public class GenomeAssembly {
    
    static class FastScanner{ //standard FastScanner      
        StringTokenizer tok;
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {//reads in a line. not automated
            String str = "";
            try {
                str = in.readLine();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }        
    }
    
    public static Vertex[] getReads(){
        FastScanner fs = new FastScanner();
        //int size = 1618;
        int size = 5; //for testing
        Vertex[] graph = new Vertex[size];
        for (int i = 0; i < graph.length; i++){
            graph[i] = new Vertex(i, fs.next());
        }
        return graph;
    }
    
    public static void printGraph(Vertex[] graph, String s){//for testing
        System.out.println("+-----------------+");
        System.out.println("Graph "+s);
        for (int i = 0; i < graph.length; i++){
            System.out.println("Vextex "+graph[i].vertexNumber+" \t Read "+graph[i].read);
            System.out.println(" \t \t Number of edges "+graph[i].edges.size());
            System.out.println("\t \t Adjacent Node \t Weight");
            for (Integer v : graph[i].edges.keySet()){                
                System.out.println("\t \t "+v+" \t "+ graph[i].edges.get(v));
            }
        }
    }
    
    static class Vertex{
        int vertexNumber; 
        String read;
        Map<Integer, Integer> edges;
            //keys are adjacent vertices and values are weight
            //weight is length of maximum overlap between reads
            //might get rid of this
        
        public Vertex(int vertexNumber, String read){
            this.vertexNumber = vertexNumber;
            this.read = read;
            this.edges = new HashMap<Integer, Integer>();
        }
    }

    static class Edge{
        int vertexA;
        int vertexB;
        int weight;
        
        public Edge(int vertexA, int vertexB, int weight){
            this.vertexA = vertexA;
            this.vertexB = vertexB;
            this.weight = weight;
        }
    }
    
    private static void createOverlapGraph(Vertex[] graph){ 
        //The length of each edge in the graph as the read length minus the length of the shared suffix and prefix. 
        
        Double error_rate = 1.0;
        
        for (int i = 0; i < graph.length; i++){//for each vertex
            for (int j = 0; j < graph.length; j++){//go through the others
                if (j == i){continue;}
                char[] str_i = graph[i].read.toCharArray();
                char[] str_j = graph[j].read.toCharArray();

                int start = 0;
                int end = str_j.length - 1;
                int lengthShared = 0;
                
                //wrong
                for (int k = 0; k < str_i.length; k++){
                    System.out.println(str_i[k]+" "+str_j[end - k]+" "+lengthShared);
                    if (str_i[end - k] == str_j[k]){
                        lengthShared++;
                    }
                    else {break;}
                }
                
                graph[i].edges.put(j,str_i.length - lengthShared);     

            }
        }
    }
    
    //int
    
    private static String findHamiltonianPath(){
    return "";
    }
    
    private static String explore(){
    return "";
    }
    
    public static void main(String [] args){
        Vertex[] graph = getReads();
        printGraph(graph, "GetReads");
        createOverlapGraph(graph);
        //makeOverlapGraph(graph);
        printGraph(graph, "Overlap");
    }
}
