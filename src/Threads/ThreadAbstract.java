package Threads;

public abstract class ThreadAbstract {

    int threadNum;

    public ThreadAbstract(int threadNum){
        this.threadNum=threadNum;
    }
    
    public abstract void divideRoles();
}