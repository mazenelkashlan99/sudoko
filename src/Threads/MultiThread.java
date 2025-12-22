package Threads;

public class MultiThread extends ThreadAbstract{

    int[][] board;
    int threadNum;

    MultiThread(int[][] board, int threadNum){
        super(board);
        this.threadNum=threadNum;
    }

    public void divideRoles(){
        if(this.threadNum==3){
            
        }
    }
}
