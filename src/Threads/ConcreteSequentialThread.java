package Threads;

public class ConcreteSequentialThread extends ThreadCreator{

    @Override
    public ThreadAbstract factorymethod(int [][] board,int threadNum) {
        return new SequentialThread(board);
    }
    
}
