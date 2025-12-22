package Threads;

public abstract class ThreadAbstract {

    int[][] board;;

    public ThreadAbstract(int [][] board){
        this.board=board;
    }

    public abstract void divideRoles();
}