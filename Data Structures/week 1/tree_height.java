import java.util.*;
import java.io.*;

public class tree_height {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public class TreeHeight {
        int n;
        int parent[];
        
        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
        }

        int computeHeight() {
            //this builds a tree as a 2d array            
            int maxHeight = 0;  
            int root = 0;              
            ArrayList<ArrayList<Integer>> nodesList = new ArrayList<ArrayList<Integer>>();
            
            //initialize each nodesList entry with an empty children list
            for (int h = 0; h < parent.length; h++){
                ArrayList<Integer> children = new ArrayList<Integer>();
                nodesList.add(children);
            }
            //create the tree structure
            for (int i = 0; i < parent.length; i++){
                if (parent[i] == -1){root = i;}
                else {                                
                    ArrayList<Integer> childrenTEMP = nodesList.get(parent[i]);                                    
                    childrenTEMP.add(i);
                    nodesList.set(parent[i],childrenTEMP);
                    }
                }                   

            // BFD    
            ArrayList<ArrayList<Integer>> queue = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> depthQueue = new ArrayList<ArrayList<Integer>>();
            queue.add(nodesList.get(root));
            depthQueue.add(nodesList.get(root));
            int height = 1;            
            int loop = 0;
            
            while (!queue.isEmpty() || queue.size() != 0){                
                ArrayList<Integer> node = queue.get(0);
                queue.remove(0);
                if (depthQueue.size() != 0){depthQueue.remove(0);}
                
                if (node.size() > 0){                                    

                    if (depthQueue.size() == 0 || depthQueue.isEmpty()){height++;}
                    
                    for (int o = 0; o < node.size(); o++){
                        ArrayList<Integer> children = nodesList.get(node.get(o));
                        if (!children.isEmpty()){
                            queue.add(children);
                        }
                    }
                    
                    if (depthQueue.size() == 0 || depthQueue.isEmpty()){
                        for (int a = 0; a < queue.size(); a++){
                            depthQueue.add(queue.get(a));
                        }
                    }
                }
            }            
            return height;
        }
    }

    static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}
