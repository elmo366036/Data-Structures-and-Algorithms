import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class CleaningApartment2 {
    private final InputReader reader;
    private final OutputWriter writer;

    public CleaningApartment2(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CleaningApartment2(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertHampathToSat {
        int numVertices;
        Edge[] edges;
        int countClauses;

        ConvertHampathToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        //METHODOLOGY
        //Store clauses in a giant string
        //generate sets of clauses
        //keep a running count of the clauses countClauses
        
        void printEquisatisfiableSatFormula() {
            StringBuilder clauses = new StringBuilder();

            appearInPath(clauses);
            clauses.append("END APPEAR IN PATH");
            clauses.append("\n");            
            allVertexInPath(clauses);
            clauses.append("END ALL VERTEX IN PATH");
            clauses.append("\n");   
            onlyOnceInPath(clauses);
            clauses.append("END ONLY ONCE IN PATH");
            clauses.append("\n");             
            onlyOneInEachPosition(clauses);
            clauses.append("END ONLY ONE IN EACH POSITION");
            clauses.append("\n"); 
            connectedVertex(clauses);
            clauses.append("END CONNECTED VERTEX");
            clauses.append("\n");
            writer.printf(countClauses + " " + numVertices * numVertices + "\n");
            //should get n^2 Variables
            writer.printf(clauses.toString());
        }

        private void appearInPath(StringBuilder clauses) {
            for (int i = 1; i < numVertices * numVertices + 1; i += numVertices) {
                for (int j = 0; j < numVertices; j++) {
                    clauses.append(i + j);
                    clauses.append(" ");
                }
                clauses.append("0\n");
                countClauses++;
            }
            /*this method iterates i from 1 to n^2 skipping by n (1, 1+n, 1+2n, ... n^2) and adds a clause for each
             * for each i, it adds n arguments to the clause of the form i+j
             * i increments by n, j by 1 
             * i = 1 and n = 5,                 [1, 2, 3, 4, 5, 0]
             * i = 6 and n = 5                  [6, 7, 8, 9, 10, 0]
             * i = 11                           [11, 12, 13, 14 15, 0]
             * i = 16                           [16, 17, 18, 19, 20, 0]
             * i = 21                           [21, 22, 23, 24, 25, 0]
             * each row has to have at least one
             */ 
        }

        private void allVertexInPath(StringBuilder clauses) {
            for (int i = 1; i < numVertices + 1; i++) {
                for (int j = 0; j < numVertices * numVertices; j += numVertices) {
                    clauses.append(i + j);
                    clauses.append(" ");
                }
                clauses.append("0\n");
                countClauses++;
            }
            /*this method iterates i from 1 to n and adds a clause for each
             * for each i, it adds n arguments to the clause of the form i+j
             * This creates the transpose of the matrix above
             * i increments by 1, j by 5
             * i = 1 and n = 5,                 [1, 6, 11, 16, 21 0]
             * i = 2 and n = 5                  [2, 7, 12, 17, 22, 0]
             * i = 3                            [3, 8, 13, 18, 23 0]
             * i = 4                            [4, 9, 14, 19, 24, 0]
             * i = 5                            [5, 10, 15, 20, 25, 0]
             * each row has to have at least one
             */             
            
        }

        private void onlyOnceInPath(StringBuilder clauses) {
            for (int i = 1; i < numVertices * numVertices + 1; i += numVertices) {
                for (int j = 0; j < numVertices; j++) {
                    for (int k = j + 1; k < numVertices; k++) {
                        clauses.append(-(i + j));
                        clauses.append(" ");
                        clauses.append(-(i + k));
                        clauses.append(" 0\n");
                        countClauses++;
                    }
                }
            }
            /*this method produces clauses in the form of negative pairs
             * it follows the form of appearinpath
             * for n=5
             * [-1 -2] 
             * [-1 -3] ....
             * [-1 -5]
             *...
             * [-6 -7]
             * [-6 -8]
             * ...
             * [-24 -25]
             */
            
        }

        private void onlyOneInEachPosition(StringBuilder clauses) {
            for (int i = 1; i < numVertices + 1; i++) {
                for (int j = 0; j < numVertices * numVertices; j += numVertices) {
                    for (int k = j + numVertices; k < numVertices * numVertices; k += numVertices) {
                        clauses.append(-(i + j));
                        clauses.append(" ");
                        clauses.append(-(i + k));
                        clauses.append(" 0\n");
                        countClauses++;
                    }
                }
            }
            /*this method produces clauses in the form of negative pairs
             * it follows the form of allvertexinboth
             * for n=5
             * [-1 -6] 
             * [-1 -11] ....
             * [-1 -16]
             *...
             * [-6 -11]
             * [-6 -16]
             * ...
             * [-20 -25]
             */        
        }

        private void connectedVertex(StringBuilder clauses) {
            boolean[][] adj = new boolean[numVertices][numVertices];
            for (Edge edge : edges) {
                adj[edge.from - 1][edge.to - 1] = true;
                adj[edge.to - 1][edge.from - 1] = true;
            }
            //initalize the adjency matrix, normalize to 0 matrix
            /* adj[a][b] TRUE means that a is adjacent to b and adj[b][a] must also be adjacent (i.e. connected)
             * Xij = adj[Vertex A][Vertex B] 
             * adj[from][to] = adj[to][from] = TRUE            
             */ 
            for (int i = 0; i < numVertices; i++) {
                for (int j = i + 1; j < numVertices; j++) {
                    if (!adj[i][j]) {
                        for (int k = 0; k < numVertices - 1; k++) {
                            clauses.append(-((i + 1) * numVertices - (numVertices - 1) + k));
                            clauses.append(" ");
                            clauses.append(-((j + 1) * numVertices - (numVertices - 1) + k + 1));
                            clauses.append(" 0\n");
                            //add inverse of above (e.g., (a, b) and (b,a)
                            clauses.append(-((j + 1) * numVertices - (numVertices - 1) + k));
                            clauses.append(" ");
                            clauses.append(-((i + 1) * numVertices - (numVertices - 1) + k + 1));
                            clauses.append(" 0\n");
                            countClauses += 2;
                        }
                    }
                }
            }
            /*go through the adjaceny matrix matrix
             * [-1 -12]
             * [-11 -2]
             * [-2 -13]
             * [-12 -3]
             * 
             */
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertHampathToSat converter = new ConvertHampathToSat(n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt();
            converter.edges[i].to = reader.nextInt();
        }

        converter.printEquisatisfiableSatFormula();
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
