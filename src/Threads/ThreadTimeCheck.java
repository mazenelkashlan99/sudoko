package Threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTimeCheck {

    int threadNum;
    long startTime;
    long endTime;
    long diffTime;
    
    ThreadTimeCheck(int threadNum){
            this.threadNum=threadNum;
    }

    void start(){
        this.startTime = System.currentTimeMillis();
    }

    void end(){
        this.endTime=System.currentTimeMillis();
    }

    void executeSummary(){
        long totalTime = endTime - startTime;
        System.out.println("\n=== EXECUTION TIME SUMMARY ===");
        System.out.println("Total execution time: " + totalTime + " ms");
        System.out.println("Number of threads: " + threadNum);            
        System.out.println("Average per thread: " + (totalTime / (float) threadNum) + " ms");
    }
}
