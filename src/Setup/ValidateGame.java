package Setup;


public abstract class ValidateGame {
    
    int [][] testMatrix;
    int threadNum;
    enum Role { 
           ROW,
           COLUMN,
           BOX;
     }


    ValidateGame(int [][] gameMatrix,int threadNum,Role role){
        this.testMatrix=gameMatrix;
        this.threadNum=threadNum;
        this.role=role;
    }

    boolean isSizeAppropriate(){
        return (this.testMatrix[1].length==9 && this.testMatrix[0].length==9);
    }

    boolean isValidGame(){
        return false;
    }

}