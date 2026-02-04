package Threads;

public abstract class ThreadCreator {
    int [][]  board;
    public abstract ThreadAbstract factorymethod(int [][] board,int threadNum);
}
