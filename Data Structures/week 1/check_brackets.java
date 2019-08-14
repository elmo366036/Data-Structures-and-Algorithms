import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;



class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        boolean initFlag = false; //don't need
        boolean missingOpen = false;
        boolean mismatch = false;
        int placement = 0;
        Bracket top = null;
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);
            if (next == '(' || next == '[' || next == '{') {
                Bracket bracket = new Bracket(next,position);
                opening_brackets_stack.push(bracket);
                initFlag = true;
            }             
            else if (next == ')' || next == ']' || next == '}') {
                if (opening_brackets_stack.empty()){
                    // empty list
                    missingOpen = true;
                    placement = position;
                    break;}
                top = opening_brackets_stack.pop();
                if (!top.Match(next)){
                    // mismatch case
                    placement = position + 1;
                    mismatch = true;
                    break;}
            }
        }
        if (opening_brackets_stack.empty() && initFlag == true && missingOpen == false){System.out.println("Success");}
        else if (opening_brackets_stack.empty() && missingOpen){System.out.println(placement+1);}//wrong maybe 1?
        else {
            if (missingOpen){System.out.println(placement);}
            else {
                if (mismatch){System.out.println(placement);}
                else {
                    Bracket last = opening_brackets_stack.pop();
                    System.out.println(last.position + 1);
                }
            }
        }
    }
     
    
    public static void staticTester(){
        String text = "[]";
        System.out.println(text);
        test(text);
    
        text = "{}[]";
        System.out.println(text);
        test(text);    
    
        text = "[()]";
        System.out.println(text);
        test(text);  
    
        text = "(())";
        System.out.println(text);
        test(text);     
    
        text = "{[]}()";
        System.out.println(text);
        test(text); 
    
        text = "{";
        System.out.println(text);
        test(text);
    
        text = "{[}";
        System.out.println(text);
        test(text);     

        text = "foo(bar);";
        System.out.println(text);
        test(text);     

        text = "foo(bar[i);";
        System.out.println(text);
        test(text); 
    
        text = "}";
        System.out.println(text);
        test(text); 
    
        text = "} ";
        System.out.println(text);
        test(text);     
    
        text = " } ";
        System.out.println(text);
        test(text);   
    
        text = "[](()";
        System.out.println(text);
        test(text);  
    
        text = "(()";
        System.out.println(text);
        test(text); 
        
        text = "}()";
        System.out.println(text);
        test(text); 
        
        text = "{[}";
        System.out.println(text);
        test(text); 
        
        text = "{}{}]";
        System.out.println(text);
        test(text);   
        
        text = "[[]}]{}";
        System.out.println(text);
        test(text);  
    }

    public static void test(String text){
        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        //boolean initFlag = false;
        boolean missingOpen = false;
        boolean mismatch = false;
        int placement = 0;
        Bracket top = null;
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);
            if (next == '(' || next == '[' || next == '{') {
                Bracket bracket = new Bracket(next,position);
                opening_brackets_stack.push(bracket);
                //initFlag = true;
            }             
            else if (next == ')' || next == ']' || next == '}') {
                if (opening_brackets_stack.empty()){
                    // empty list
                    missingOpen = true;
                    placement = position;
                    break;}
                top = opening_brackets_stack.pop();
                if (!top.Match(next)){
                    // mismatch case
                    placement = position + 1;
                    mismatch = true;
                    break;}
            }
        }
        if (opening_brackets_stack.empty() && missingOpen == false && mismatch == false){System.out.println("Success");}
        else if (opening_brackets_stack.empty() && missingOpen){System.out.println(placement+1);}//wrong maybe 1?
        else {
            if (missingOpen){System.out.println(placement);}
            else {
                if (mismatch){System.out.println(placement);}
                else {
                    Bracket last = opening_brackets_stack.pop();
                    System.out.println(last.position + 1);
                }
            }
        }
    }
}


class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}



