package Setup;


import Threads.*;
import Threads.ThreadCreator;

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

        if (threadNum==0){
            ThreadCreator SequentialThreadCreator= new ConcreteSequentialThread();
            ThreadAbstract SequentialThread=SequentialThreadCreator.factorymethod(testMatrix, threadNum);
            SequentialThread.divideRoles();
        }

        else if(threadNum==3 || threadNum==9 || threadNum==27){
            ThreadCreator MultiThreadCreator= new ConcreteMultiThread();
            ThreadAbstract MultiThread=MultiThreadCreator.factorymethod(testMatrix, threadNum);
            MultiThread.divideRoles();
        }
    
    }
}
