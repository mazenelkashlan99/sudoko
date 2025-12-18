package Setup;

import java.util.ArrayList;

public abstract class ValidateGame {

    ArrayList <Elements> elements;
    int [][] testMatrix;
    int threadNum;

    ValidateGame(int [][] testMatrix,int threadNum){
        this.testMatrix=testMatrix;
        this.threadNum=threadNum;
    }

    final boolean isSizeAppropriate(){
        return (this.testMatrix[1].length==9 && this.testMatrix[0].length==9);
    }

}
