import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class GSMNetwork {
    private final InputReader reader;
    private final OutputWriter writer;

    public GSMNetwork(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new GSMNetwork(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertGSMNetworkProblemToSat {
        int numVertices;
        Edge[] edges;

        ConvertGSMNetworkProblemToSat (int n, int m) {
            //this initalizes a set of Edges in an array called edge. There are m Edges in edges
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        void printEquisatisfiableSatFormula() {
            // This solution prints a simple satisfiable formula
            // and passes about half of the tests.
            // Change this function to solve the problem.
            
            //printf    
            //  \n = newline 
            //  %d = decimal 
            
            /*
            //original
            writer.printf("3 2\n");
            writer.printf("1 2 0\n");
            writer.printf("-1 -2 0\n");
            writer.printf("1 -2 0\n");
            */
           
            //Methodology
            /*determine all SAT clauses since max SAT Variable is 3000, max SAT Clauses is 5000, max Vertices is 500, max edges is 1000
             *max Variables is 3*500 and max Clauses = #Edges + max Variables = 1000 + 1500
             *take m Vertices and turn into m*3 variables (X) (3 colors)
             *[V1 V2, V3] goes to
             *
             *[V11, V12, V13, ... V1m] ---> [X1,    X2,     ... Xm ]
             *[V21, V22, V23, ... V2m] ---> [X1+m,  X2+m,   ... X2m]
             *[V31, V33, V33, ... V3m] ---> [X1+2m, X2+2m,  ... X3m]
             *
             *each row corresponds to a color
             *
             *identy clauses as follows
             *first set of m (#ofVertices) clauses are COLUMN values OR'ed 
             *(e.g., X1 OR X1+m OR X1+2m)
             *This means that each Vertex has to have a color (actually, one or more colors)
             *second set of n*3 (#ofEdges *3) clauses are all negative adjacent ROW pairs OR'ed
             *(e.g., -X1 OR -X2, -X2 OR -X3, -X1 OR -Xm, -X1+m OR -X2+m, etc.)
             *This means that adjacent Vertices in any row cannot have the same color
             */                                                
            
           
            //attempt #1
            //print line 1
            writer.printf("%d %d\n", numVertices + (edges.length * 3),  numVertices * 3);
                // Clauses      = #ofVertices + #ofEdges * 3
                // Variables    = #ofVertices * 3
            
            for(int i= 1; i<=numVertices; i++){// m Clauses
                writer.printf("%d %d %d 0\n", i, i + numVertices, i + numVertices * 2);
                //  Vertice#;                       Vertice# + #ofVertices;         Vertice# + #ofVertices*2;   0 
                // prints m clauses with 3 variables
            }
              
            for(Edge edge: edges){// n*3 clauses, 3 clauses per edge
                writer.printf("%d %d 0\n", -edge.from, -edge.to);
                writer.printf("%d %d 0\n", -edge.from - numVertices, -edge.to - numVertices);
                writer.printf("%d %d 0\n", -edge.from - (numVertices * 2), -edge.to - (numVertices * 2));
                //  -edge[z].from;                  -edge[z].to                     ;0
                //  -edge[z].from - #ofVertices;    -edge[z]from - #ofVertices      ;0
                //  -edge[z].from - #ofVertices*2;  -edge[z]from - #ofVertices*2    ;0
                // prints 3 sets of clauses with 2 variables n times
            }
            //end attempt #1
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
        //calling this initializes edges[]
        
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt();
            converter.edges[i].to = reader.nextInt();
        }
        //this simply puts the input into edges[].         
        
        converter.printEquisatisfiableSatFormula();
        //calculate and print the result
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
