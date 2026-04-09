package Threads;
public class ThreadTimeCheck {

    int threadNum;
    long startTime;
    long endTime;
    long diffTime;
    
    public ThreadTimeCheck(int threadNum){
            this.threadNum=threadNum;
    }

    public void start(){
        this.startTime = System.currentTimeMillis();
    }

    void end(){
        this.endTime=System.currentTimeMillis();
    }

    void executeSummary(){
        long totalTime = endTime - startTime;
        System.out.println("\n=== EXECUTION TIME SUMMARY ===");
        System.out.println("Total execution time: " + totalTime + " ms");
        if (threadNum>0){
            System.out.println("Number of threads: " + threadNum);          
            System.out.println("Average per thread: " + (totalTime / (float) threadNum) + " ms");
        }
    }
}
