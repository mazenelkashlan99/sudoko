package Setup;

import java.util.ArrayList;
import java.util.Arrays;

public class ValidateGame{

    int [][] testMatrix;
    int threadNum;

    public ValidateGame(int threadNum,int [][] testMatrix){
        this.testMatrix=testMatrix;
        this.threadNum=threadNum;
    }

    final boolean isSizeAppropriate(){
        return (this.testMatrix[1].length==9 && this.testMatrix[0].length==9);
    }

    public ArrayList<int[]> getRows(){
        ArrayList <int[]> rows=new ArrayList<int []>();
        for (int r=0;r<9;r++){
            rows.add(testMatrix[r]);
        }
        return rows;
    }

    public ArrayList<int[]> getColumns(){
        ArrayList <int[]> columns=new ArrayList<int []>();
        for (int c=0;c<9;c++){
            int[] column=new int[9];
            for (int r=0;r<9;r++){
                column[r]=testMatrix[r][c];
            }
            columns.add(column);
        }
        return columns;
    }

    void getBoxes(){

    }

    void breakdownIntoRoles(){
        switch (threadNum) {
            case 0:
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
