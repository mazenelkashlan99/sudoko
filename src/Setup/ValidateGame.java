package Setup;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.GroupLayout.SequentialGroup;

import Threads.SequentialThread;

public class ValidateGame {
    int [][] testMatrix;
    int threadNum;

    public ValidateGame(int threadNum,int [][] testMatrix){
        this.testMatrix=testMatrix;
        this.threadNum=threadNum;
    }

    final boolean isSizeAppropriate(){
        return (this.testMatrix[1].length==9 && this.testMatrix[0].length==9);
    }



    public void threadNumMatching(){
        switch (threadNum) {
            case 0:
               SequentialThread singleThread=new SequentialThread(this.testMatrix);
               singleThread.divideRoles();
                break;
            case 3:
                break;
            case 27:
                break;
            default:
                break;
        }
    }

}
