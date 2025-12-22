package Setup;

import java.util.ArrayList;

public class BoardBreakdown {

    int [][] board;

    public BoardBreakdown(int[][] board){
        this.board=board;
    }

    public ArrayList<int[]> getRows(){
        ArrayList <int[]> rows=new ArrayList<int []>();
        for (int r=0;r<9;r++){
            rows.add(this.board[r]);
        }
        return rows;
    }

    public ArrayList<int[]> getColumns(){
        ArrayList <int[]> columns=new ArrayList<int []>();
        for (int c=0;c<9;c++){
            int[] column=new int[9];
            for (int r=0;r<9;r++){
                column[r]=board[r][c];
            }
            columns.add(column);
        }
        return columns;
    }

    public ArrayList<int[]> getBoxes(){
        ArrayList <int[]> boxes=new ArrayList<int []>();
        for (int b = 0; b < 9; b++) {
            int[] box = new int[9];
            int boxRow = (b / 3) * 3;
            int boxCol = (b % 3) * 3;
            int index = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    box[index++] = board[boxRow + i][boxCol + j];
                }
            }
            boxes.add(box);
        }
        return boxes;
    }
    
}