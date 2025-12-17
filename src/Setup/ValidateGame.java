package Setup;
public abstract class ValidateGame {

    int [][] testMatrix;
    int threadNum;

    ValidateGame(int [][] testMatrix,int threadNum){
        this.testMatrix=testMatrix;
        this.threadNum=threadNum;
    }

    final boolean isSizeAppropriate(){
        return (this.testMatrix[1].length==9 && this.testMatrix[0].length==9);
    }


    abstract boolean isRoleValid(Role role,int roleNum);
}
