
/**
 * Write a description of Tip_Removal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.FileNotFoundException;
import java.util.*;

/*Methodology
 *  1) create de Bruijin graph. for each node we need to track who connects to it and who it connects to
 *  2) iterate through the graph
 *      2a) look for end nodes (nodes that do not connect to anything). remove them. count them
 *      2b) after all end nodes are removed, look for all beginning nodes 
 *          (nodes that do not have anything connected to them). remove them. count them
 *      2c) continue until there are no end and no begin nodes to remove
 *  3) the result will be a graph with the tips removed and the number of tips
 */


public class TipRemoval {
    //global variables
    private int tipsNumber = 0;
    private HashMap<String, NeighboringVertices> graph;    

    public TipRemoval(List<String> reads, int k) {
        constructDeBruijnGraph(reads.get(0).length(), k, reads);
        boolean foundTip = true;

        while (foundTip) {
            foundTip = false;   //once all segments (keys) have been evaluated and there are no tips,
                                //the while loop will end
            for (String key : graph.keySet()) { //iterate throuigh the segments (keys) in graph
                NeighboringVertices vertex = graph.get(key); //get the NeighboringVertices of the segment
                //check for ending tips first and remove them before moving on to starting tips
                //if there are no tips, move on to the next segment (key)
                if (vertex.to.size() == 0) {
                    //find all vertices that point to the segment and remove the edges that point 
                    //from the vertices to the segment
                    for (String from : vertex.from.keySet()) {
                        graph.get(from).to.remove(key);
                    }
                    //remove the segment from the graph
                    graph.remove(key);
                    //set while loop argument to true
                    foundTip = true;
                    //increment tip
                    tipsNumber++;
                    break; //start the read analysis over (go back to the for loop (i.e., go to next read)
                } 
                else if (vertex.from.size() == 0) {
                    //find all vertices that are pointed from the segment and remove the edges that point 
                    //from the segment to the vertices                    
                    for (String to : vertex.to.keySet()) {
                        graph.get(to).from.remove(key);
                    }
                    graph.remove(key);
                    foundTip = true;
                    tipsNumber++;
                    break;
                }
            }
        }
    }

    private void constructDeBruijnGraph(int readLength, int k, List<String> reads) {
        graph = new HashMap<String, NeighboringVertices>();
        for (String read : reads) {//iterate through each read
            for (int startPosition = 0; startPosition <= readLength - k; startPosition++) {
                /* iterate from 0 to read.length - k 
                 * this is the number of segments per read that must be processed
                 * it is based on k
                   as k decreases, number of segments per read increases
                   startPosition serves both as the start position and the segment number (startPos + 1)
                   e.g., if k=3 and l=4, there are 4-3+1 = 2 segments per read
                   e.g., if k=15 and l=100, there are 86 segments per read
                 */              
                String prefix = read.substring(startPosition, startPosition + k - 1);                    
                String suffix = read.substring(startPosition + 1, startPosition + k);
                    /* prefix is the read without the last character
                     * suffix is the read without the first character   
                     */
                if (!prefix.equals(suffix)) {//exclude self edge
                    //for each segment create prefix and suffix data using 
                    //prefix and suffix as a key and he NeighboringVertices object
                    //as the value which stores the nodes to and from the key
                    NeighboringVertices prefixVertex;
                    if (!graph.containsKey(prefix)) {
                        prefixVertex = new NeighboringVertices();
                            //if graph does not contain the prefix, add it
                    } else {
                        prefixVertex = graph.get(prefix);
                            //else, get the data (value) corresponding to prefix (key)
                    }
                    prefixVertex.to.put(suffix, 1);
                        //add the suffix, 1 and show it as a "to"
                        //prefix now points to suffix with an indicater of 1
                        //this SILL NOT result in duplicate entries??? need to test for this
                    graph.put(prefix, prefixVertex);
                        //replace prefix (key) with the updated prefixVertex (value)  
                    //repeat for the suffix
                    NeighboringVertices suffixVertex;
                    if (!graph.containsKey(suffix)) {
                        suffixVertex = new NeighboringVertices();
                    } else {
                        suffixVertex = graph.get(suffix);
                    }
                    suffixVertex.from.put(prefix, 1);
                        //add the prefix, 1 and show it as a "from"
                        //suffix now points to prefix with an indicater of 1
                        //this MAY result in duplicate entries??? need to test for this
                    graph.put(suffix, suffixVertex);
                }
            }
        }
        //printDeBruijnGraph(); //for debug
    }

    private void printDeBruijnGraph(){ //for debug
           for (String key : graph.keySet()) {
                NeighboringVertices neighbors = graph.get(key);
                System.out.println(key+" \t "+neighbors.from+" \t "+neighbors.to);
            }
        }
    
    private static class NeighboringVertices {
        //for each node in the graph, track nodes that point to it (from) and nodes that it points to (to)
        //as a hashmap, one for each
        private HashMap<String, Integer> from;
        private HashMap<String, Integer> to;

        public NeighboringVertices() {
            this.from = new HashMap<String, Integer>();
            this.to = new HashMap<String, Integer>();
        }
    }
    
        public static void main(String[] args) throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);
        int k = 15;
        //int k = 3; //for testing
        List<String> reads = new ArrayList<String>();
        
        while (reader.hasNext()) {
            reads.add(reader.next());
        }
        
        //reads.add("AACG"); reads.add("AAGG"); reads.add("ACGT"); reads.add("CAAC"); reads.add("CGTT"); reads.add("GCAA"); reads.add("GTTG"); reads.add("TCCA"); reads. add("TGCA"); reads.add("TTGC"); //for testing        
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    TipRemoval tipRemoval = new TipRemoval(reads, k);
                    System.out.println(tipRemoval.tipsNumber);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }, "1", 1 << 26).start();
    }
}
