package Threads;

public abstract class ThreadFactory{
    private ThreadAbstract thread;
    
    public ThreadFactory(int threadNum,int [][] board){
       if (threadNum==0){
        thread=new SequentialThread(board); 
       }
       else if (threadNum==3){
        thread=new MultiThread(board, threadNum);
       }
    }

    public ThreadAbstract getThread() {
        return thread;
    }

}