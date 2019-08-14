import java.io.*;
import java.util.*;



class PlanParty {
    static Vertex[] ReadTree() throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);

        tokenizer.nextToken();
        int vertices_count = (int) tokenizer.nval;
        //read the first line (n) which is the number of vertices
        
        Vertex[] tree = new Vertex[vertices_count];
        //create a list of Vertices
        
        for (int i = 0; i < vertices_count; ++i) {
            tree[i] = new Vertex();
            tokenizer.nextToken();
            tree[i].weight = (int) tokenizer.nval;
        }
        //read in the second line and assign weights to each vertex

        for (int i = 1; i < vertices_count; ++i) {
            tokenizer.nextToken();
            int from = (int) tokenizer.nval;
            tokenizer.nextToken();
            int to = (int) tokenizer.nval;
            tree[from - 1].children.add(to - 1);
            tree[to - 1].children.add(from - 1);
        }
        //read in the next series of lines and create the child relationships
        
        return tree;
    }

    static void dfs(Vertex[] tree, int vertex, int parent) {
        //the code below runs dfs to find the leafs of the tree
        //note that TreeRead will list parents as children
        
        //System.out.println("+--------------+");
        //System.out.println("DFS Begin");
        for (int child : tree[vertex].children){
            //System.out.println("Vertex: "+vertex+"\t Child: "+child);
            if (child != parent){//this prevents cycles
                dfs(tree, child, vertex);
            }
        }
        //System.out.println("DFS END");        
        //System.out.println("+--------------+");
        //System.out.println();
        /*the following code runs first for the leafs and then for the parents and so on. 
         * 
         */
        int m1 = tree[vertex].weight;   //grandchildren
        int m0 = 0;                     //children
        
        /*this next section will not run if vertex is a leaf. 
         *in that case, m1 = vertex.weight and maxCumWeight
         *if vertex is not a leaf, then it will evaluate 
         */
        
        //System.out.println("m0 m1 BEGIN");
        
        for (int child : tree[vertex].children){//for all children u of v
            if (child!=parent){//this prevents cycles
                m0 += tree[child].maxCumWeight;
                //System.out.println("Child "+child+" \t m0 "+m0);
                for (int grandchild : tree[child].children){//for all children w of u
                    if ((grandchild != child) && (grandchild != parent)){
                        m1 += tree[grandchild].maxCumWeight;
                        //System.out.println("GrandChild "+grandchild+" \t m1 "+m1);
                    }
                }
            }
        }
        //System.out.println("m0 m1 END");
        //System.out.println();
        tree[vertex].maxCumWeight = Math.max(m0, m1);
    }

    static int MaxWeightIndependentTreeSubset(Vertex[] tree) {
        int size = tree.length;
        if (size == 0)
            return 0;
        dfs(tree, 0, -1);
        return tree[0].maxCumWeight;
        //return the maxCumWeght of the first (root) vertex
    }

    public static void main(String[] args) throws IOException {
        // This is to avoid stack overflow issues
        
        new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new PlanParty().run();
                        } catch(IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
                
        //new PlanParty().run();        
    }

    public void run() throws IOException {
        Vertex[] tree = ReadTree();
        //printTree(tree, "Input");       
        int weight = MaxWeightIndependentTreeSubset(tree);
        //printTree(tree, "Output");
        System.out.println(weight);
    }
    
    public void printTree(Vertex[] tree, String s){
        System.out.println("+--------------+");
        System.out.println("Tree "+s);
        for (int i = 0; i < tree.length; i++){
            System.out.print("["+i+"]\tWeight "+tree[i].weight+"  maxCumWeight "+tree[i].maxCumWeight+"  Children ");
            for (int child : tree[i].children){
                System.out.print(child+" ");
            }
            System.out.println();
        }
        System.out.println("+--------------+");
    }
}

class Vertex {
    Vertex() {
        this.weight = 0;
        this.children = new ArrayList<Integer>();
    }

    int weight;
    int maxCumWeight;
    ArrayList<Integer> children;
}