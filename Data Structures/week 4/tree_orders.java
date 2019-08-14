import java.util.*;
import java.io.*;

public class tree_orders {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        FastScanner(String source) throws IOException{//reove all
            File file = new File(source);
            FileReader fileReader = new FileReader(file);
            in = new BufferedReader(fileReader);        
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

    public class TreeOrders {
        int n;
        int[] key, left, right;
        String source;//remove
        
        TreeOrders(String source){//remove all
            this.source = source;
          }
          
        TreeOrders() {} //remove
        //add constructors
        
            void read() throws IOException {
            //FastScanner in = new FastScanner(source);//remove
            FastScanner in = new FastScanner();//add back
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) { 
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
            
            //print out input data
            /*
            for (int i = 0; i < n; i++){
                System.out.print(key[i]+" ");
            }
            for (int i = 0; i < n; i++){
                System.out.print(left[i]+" ");
            }
            for (int i = 0; i < n; i++){
                System.out.print(right[i]+" ");
            } 
            */
        }                       
        
        List<Integer> inOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
                        // Finish the implementation
                        // You may need to add a new recursive method to do that
            inOrderTraversal(result, 0);            
            return result;
            
            //stack overflow
        }
        
        void inOrderTraversal(ArrayList<Integer> result, int n){//LNR
            if (n == -1){
                return;
            }
            inOrderTraversal(result,left[n]);
            result.add(key[n]);
            inOrderTraversal(result,right[n]);            
        }
        
        List<Integer> preOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
                        // Finish the implementation
                        // You may need to add a new recursive method to do that
            preOrderTraversal(result, 0);            
            return result;
        }
        
        void preOrderTraversal(ArrayList<Integer> result, int n){//NLR
            if (n == -1){
                return;
            }
            result.add(key[n]);
            preOrderTraversal(result,left[n]);
            preOrderTraversal(result,right[n]);             
        }

        List<Integer> postOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
                        // Finish the implementation
                        // You may need to add a new recursive method to do that
            postOrderTraversal(result, 0);              
            return result;
        }

        void postOrderTraversal(ArrayList<Integer> result, int n){//LRN
            if (n == -1){
                return;
            }
            postOrderTraversal(result,left[n]);
            postOrderTraversal(result,right[n]);  
            result.add(key[n]);
        }    
    }
    
    static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_orders().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
    }

    public void print(List<Integer> x) {
        //System.out.println("Printing");
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        TreeOrders tree = new TreeOrders();
        tree.read();
        print(tree.inOrder());
        print(tree.preOrder());
        print(tree.postOrder());
    }
    
    public void tester() throws IOException{
            // in is 01, out is 01.a
            //need to read in all 24 test cases        
            int testCaseStart = 21;
            int testCaseEnd = 21; 
            String source = "";
            String sourceA = "";
            
            List<Integer> INORDER_ANSWER = new ArrayList<Integer>();
            List<Integer> PREORDER_ANSWER = new ArrayList<Integer>();
            List<Integer> POSTORDER_ANSWER = new ArrayList<Integer>();
            
            
            for (int i = testCaseStart; i <= testCaseEnd; i++){                
                if (i < 10){
                    source = "tests/0"+i;  
                    sourceA = "tests/0"+i+".a";
                }
                else {
                    source = "tests/"+i;  
                    sourceA = "tests/"+i+".a";
                }
                System.out.println(i + " " + source);                      

                File file = new File(sourceA);
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                                
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append(bufferedReader.readLine());               
                String answer1 = stringBuffer1.toString();           
                String[] IN = answer1.split(" ");
                for (int z = 0; i<IN.length; i++){
                    INORDER_ANSWER.add(Integer.parseInt(IN[i]));
                }
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(bufferedReader.readLine());               
                String answer2 = stringBuffer2.toString();           
                String[] PRE = answer2.split(" ");
                for (int z = 0; i<PRE.length; i++){
                    PREORDER_ANSWER.add(Integer.parseInt(PRE[i]));
                }
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append(bufferedReader.readLine());               
                String answer3 = stringBuffer3.toString();           
                String[] POST = answer3.split(" ");
                for (int z = 0; i<POST.length; i++){
                    POSTORDER_ANSWER.add(Integer.parseInt(POST[i]));
                }                
                
                System.out.println("Answer File Loaded");
                //test for speed
                long startTime = System.currentTimeMillis();                
                TreeOrders tree = new TreeOrders(source);        
                tree.read();           
                System.out.println("Tree read");
                List<Integer> INORDER = tree.inOrder();
                System.out.println("Inorder Complete");
                List<Integer> PREORDER = tree.preOrder();
                System.out.println("Preorder Complete");
                List<Integer> POSTORDER = tree.postOrder();
                System.out.println("Postorder Complete");    
                long elapsedTime = (System.currentTimeMillis() - startTime);                
                //end speed
                               
                System.out.println("Time:    "+elapsedTime+" ms");
                
                //compare answers
                
                for (int z = 0; z < INORDER.size(); i++){
                    if (INORDER.get(z) != INORDER_ANSWER.get(z)){
                        System.out.println("Failed, INORDER = "+INORDER.get(z)+" and INORDER_ANSWER = "+INORDER_ANSWER.get(z)+" for row z = "+z);
                        break;
                    }
                    if (PREORDER.get(z) != PREORDER_ANSWER.get(z)){
                        System.out.println("Failed, PREORDER = "+PREORDER.get(z)+" and PREORDER_ANSWER = "+PREORDER_ANSWER.get(z)+" for row z = "+z);
                        break;
                    }
                    if (POSTORDER.get(z) != POSTORDER_ANSWER.get(z)){
                        System.out.println("Failed, POSTORDER = "+POSTORDER.get(z)+" and POSTORDER_ANSWER = "+POSTORDER_ANSWER.get(z)+" for row z = "+z);
                        break;
                    }                
                
                }
                System.out.println("Test Complete");
            }       
       }
}
