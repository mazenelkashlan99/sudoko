package Threads;

public class ConcreteMultiThread extends ThreadCreator {

    public ThreadAbstract factorymethod(int [][] board,int threadNum) {
        return new MultiThread(board,threadNum);
    }
    
    
}
