import java.util.*;
import java.io.*;

public class is_bst {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
          // Implement correct algorithm here
          List<Integer> result = new ArrayList<Integer>();
          List<int[]> resultList = new ArrayList<int[]>();
          if (nodes == 0){return true;}
          resultList = inOrder();
          
          //System.out.print(resultList.get(0)[0]+" "+resultList.get(0)[1]);
          
          for (int i = 1; i < resultList.size();i++){
              //System.out.print(resultList.get(i)[0]+" "+resultList.get(i)[1]);
              int[] entryCurrent = new int[2];
              int[] entryPrev = new int[2];
              entryCurrent = resultList.get(i);
              entryPrev = resultList.get(i-1);
              
              //compare order of keys look for any key that is not assending
              if (entryPrev[0] > entryCurrent[0]){
                  return false;
                }
                
              //look for the case where the keys are the same and compare index
              if (entryPrev[0] == entryCurrent[0] && entryCurrent[1] < entryPrev[1]){
                  return false;
                }
            }
          return true;
          }
        
        List<int[]> inOrder() {
            ArrayList<int[]> resultList = new ArrayList<int[]>();
                        // Finish the implementation
                        // You may need to add a new recursive method to do that
            inOrderTraversal(resultList, 0);            
            return resultList;
        }
        
        void inOrderTraversal(ArrayList<int[]> resultList, int n){
            if (n == -1){
                return;
            }
            Node treeNode = tree[n];
            /*
            if (treeNode.left != -1){
                Node treeNodeLeft = tree[treeNode.left]; 
                //System.out.println("treeNode.key "+treeNode.key+"treeNodeLeft.key "+treeNodeLeft.key);
                if (treeNode.key > treeNodeLeft.key){
                    inOrderTraversal(result,treeNode.left);            
                    result.add(treeNode.key);
                    inOrderTraversal(result,treeNode.right);
                }
                else {
                    result.add(-1);
                    result.add(-2);
                }
                return;
            }
            */
            inOrderTraversal(resultList,treeNode.left);           
            int[] entry = new int[2];
            entry[0] = treeNode.key;
            entry[1] = n;
            resultList.add(entry);
            inOrderTraversal(resultList,treeNode.right);            
            
        }    
    }

    

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {//isBinarySearchTree??? 
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
