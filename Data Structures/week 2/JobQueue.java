import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private long time;
    private int jobIndex;
    private int[] FW;
    private ArrayList<long[]> OW;
    
    private FastScanner in;
    private PrintWriter out;

    public void tester() throws IOException{
        /*
        String source2 = "tests/02";
        String sourceA2 = "tests/02.a";
        runTest(source2, sourceA2);
        */
        String source8 = "tests/08";
        String sourceA8 = "tests/08.a";
        runTest(source8, sourceA8);
    }
    
    private void runTest(String source2, String sourceA2) throws IOException{
        //solve(source2); add for tester()
        //System.out.println("solved");
        //print out result
        /*
        System.out.println("begin");
        for (int i = 0; i < jobs.length; ++i) {
            System.out.println(assignedWorker[i] + " " + startTime[i]);
        }        
        System.out.println("end");
        */
        //read in answer file
        System.out.println();
        File fileA2 = new File(sourceA2);
        FileReader fileReaderA2 = new FileReader(fileA2);                
        BufferedReader bufferedReaderA2 = new BufferedReader(fileReaderA2);
        StringBuffer stringBufferA2 = new StringBuffer();
        String line;
        ArrayList<long[]> answer = new ArrayList<long[]>();
        while ((line = bufferedReaderA2.readLine()) != null){
            String[] lineList = line.split(" ");
            long a = Long.parseLong(lineList[0]);
            long b = Long.parseLong(lineList[1]);
            long[] temp = new long[2];
            temp[0] = a;
            temp[1] = b;
            answer.add(temp);
        }
        // print out answer
        /*
        System.out.println("answer");
        for (long[] list : answer){
            System.out.println(list[0]+" "+list[1]);   
        }
        */
        //compare answer with result         
        for (int i = 0; i < answer.size(); i++){
            long[] list = answer.get(i);
            if (list[0] != (long) assignedWorker[i] || list[1] != startTime[i]){
                System.out.println("FAIL  i="+i);
                System.out.println(list[0]+" "+assignedWorker[i]+" "+list[1]+" "+startTime[i]);
                break;
            }
            else {
                System.out.println("Sucess ["+i+" out of "+(answer.size() - 1)+"]");
            }
        }         
    }
                                           
    public static void main(String[] args) throws IOException {
        new JobQueue().solve(); //remove for tester()
        
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.               
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];

        /*
         * Naive Algorithm #1
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
        */        
        //Naive Algorithm #2 Coop's 
        /*
        int[] workers = new int[numWorkers];
        ArrayList<Integer> jobsList = new ArrayList<Integer>();
        long time = 0;
        int i = 0;        
        //convert jobs to an arraylist so I can remove the first job when it is taken (i.e. treat it like a queue)        
        for (int k = 0; k < workers.length; k++){
            workers[k] = 0;
        }
        for (int k = 0; k < jobs.length; k++){
            jobsList.add(jobs[k]);
        }        
        while (!jobsList.isEmpty() || jobsList.size() != 0){
            //System.out.println(time);
            for (int j = 0; j < workers.length; j++){
                //This is a problem area. Replace worker array with heap
                //System.out.println(j);
                if (workers[j] >= 1){
                    workers[j] -= 1;
                }
                if (workers[j] == 0){
                    workers[j] = jobsList.get(0);
                    jobsList.remove(0);                   
                    assignedWorker[i] = j;
                    startTime[i] = time;
                    i++;
                }               
            }
            time +=1;
        }        
        //assignedWorker.length = number of workers
        //create a queue for each worker
        //iterate through each line in jobs
        //jobs.length = number of jobs 
        */
       
        //initialize
        time = 0; 
        jobIndex = 0; 
        OW = new ArrayList<long[]>();        
        FW = new int[numWorkers];
        for (int i = 0; i < FW.length; i++){ //this is already a min heap (is it long enough? YES)
            FW[i] = i;
            //System.out.print(" i="+i+"\t FW[i]="+FW[i]);
        }
        
        //System.out.println("FW initialized. FW.length = "+FW.length);
        
        while (jobIndex < jobs.length){
            //System.out.println("jobIndex = "+jobIndex+"\t jobs.length = "+jobs.length);
            //System.out.println("OW.size = "+OW.size());
            if (OW.size() == 0) {
                //System.out.println("OW is empty (first time), initiate assign()");
                assign(); //assign workers in FW to jobs and move to OW, remove jobs from job list, record             
            }
            if (OW.get(0)[1] == 9223372036854775807L){
                //System.out.println("OW is empty (not first time), initiate assign()");
                assign(); //assign workers in FW to jobs and move to OW, remove jobs from job list, record                  
            }
            else {
                //System.out.println("OW is not empty, check for completed jobs");
                //System.out.println("OW.get(0)[1] = "+OW.get(0)[1]+"\t time = "+time);
                while (OW.get(0)[1] == time){
                    //move completed jobs from OW and return free workers to FW
                    //System.out.println("OW is not empty and there is a finished job. Move job and initiate assign()");
                    long[] jobOUT = new long[2]; // [occupied worker, finish time that = time]
                    jobOUT = extractMinOW();
                    int freeWorker = (int) jobOUT[0];
                    insertFW(freeWorker); //into FW    
                    assign(); //assign workers in FW to jobs and move to OW, remove jobs from job list, record In while loop or not?
                }

            }
            time = OW.get(0)[1];
            //System.out.println("Time = "+time);
        }       
    }
    
    private void assign(){
        //System.out.println("Assign: FW[0] = "+FW[0]+"\t FW.length = "+FW.length);
        while (FW[0] < FW.length && jobIndex < jobs.length){
            long[] jobIN = new long[2]; // [occupied worker, finish time]
            jobIN[0] = (long) extractMinFW();
            jobIN[1] = time + (long) jobs[jobIndex];
            assignedWorker[jobIndex] = (int) jobIN[0]; //record the assignment
            startTime[jobIndex] = time; //record the assignment
            //System.out.println("assignedWorker "+assignedWorker[jobIndex]+"\t startTime "+startTime[jobIndex]);
            insertOW(jobIN); 
            jobIndex++;
        }   
    }
    
    private int extractMinFW(){
        int result = FW[0];
        FW[0] = FW.length;
        siftDownFW(0);
        return result;        
    }
    
    private void siftDownFW(int i){
        int minIndex = i;
        int leftChild = (2*i + 1);
        if (leftChild < FW.length && FW[leftChild] < FW[minIndex]){
            minIndex = leftChild;
            }
        int rightChild = (2*i + 2);
        if (rightChild < FW.length && FW[rightChild] < FW[minIndex]){
            minIndex = rightChild;
            }
        if (i != minIndex){
            int temp = FW[i];
            FW[i] = FW[minIndex];
            FW[minIndex] = temp;
            siftDownFW(minIndex);
            }          
    }
    
    private void insertFW(int freeWorker){
        //System.out.println("insertFW "+freeWorker+" to FW["+(FW.length-1)+"]");
        FW[FW.length - 1] = freeWorker;
        siftUpFW(FW.length - 1);
    }
    
    private void siftUpFW(int i){        
        while (i >= 1 && FW[(i-1)/2] > FW[i]){
            int temp = FW[(i-1)/2];
            FW[(i-1)/2] = FW[i];
            FW[i] = temp;
            i = (i-1)/2;
        }
        //System.out.println("siftUpFW top worker F[0] now is "+FW[0]);
    }

    private long[] extractMinOW(){
        long[] result = new long[2];
        result = OW.get(0);
        long[] temp = new long[2];
        temp[0] = -1;
        temp[1] = 9223372036854775807L;
        OW.set(0,temp);
        siftDownOW(0);
        return result;         
    }
    
    private void siftDownOW(int i){
        int minIndex = i;
        int leftChild = (2*i + 1); 
        int rightChild = (2*i + 2); 
        if (leftChild < OW.size()){
            if (OW.get(leftChild)[1] < OW.get(minIndex)[1]){
                minIndex = leftChild;
            }
            if ((OW.get(leftChild)[1] == OW.get(minIndex)[1]) && (OW.get(leftChild)[0] < OW.get(minIndex)[0])){
                minIndex = leftChild;
            }
        }
        if (rightChild < OW.size()){
            if (OW.get(rightChild)[1] < OW.get(minIndex)[1]){
                minIndex = rightChild;
            }
            if (OW.get(rightChild)[1] == OW.get(minIndex)[1] && (OW.get(rightChild)[0] < OW.get(minIndex)[0])){
                minIndex = rightChild;
            }
        }        
        if (i != minIndex){
            long[] temp = OW.get(i);
            long[] tempMinIndex = OW.get(minIndex);           
            OW.set(i, tempMinIndex);
            OW.set(minIndex, temp);
            siftDownOW(minIndex);
            }  
    }
    
    private void insertOW(long[] jobIN){
        OW.add(jobIN);
        siftUpOW(OW.size()-1);
    }
    
    private void siftUpOW(int i){
        while (i >= 1 && OW.get((i-1)/2)[1] > OW.get(i)[1]){
            long[] temp = OW.get((i-1)/2);
            long[] temp2 = OW.get(i);
            OW.set((i-1)/ 2,temp2);
            OW.set(i,temp);
            i = (i-1)/2;
        }        
    }
    
    public void solve() throws IOException {//remove for tester()
    //public void solve(String source) throws IOException {   //add for tester()
        in = new FastScanner(); //remove for tester()
        //in = new FastScanner(source); //add for tester()
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();   //remove for tester()
        out.close();  //remove for tester()
        /*        
        for (int i = 0; i < jobs.length; ++i) {
            System.out.println(assignedWorker[i] + " " + startTime[i]);
        }
        */
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }
        /*
        public FastScanner(String source) throws IOException{//add for tester()
            File file = new File(source);
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
        }
        */
        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
