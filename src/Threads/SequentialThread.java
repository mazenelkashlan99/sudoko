package Threads;

import java.util.ArrayList;

import Setup.ManageRoles;
import Setup.BoardBreakdown;
import Setup.Role;

public class SequentialThread extends ThreadAbstract{
    
    int[][] board;  
    ThreadTimeCheck timeCheck;
    
    public SequentialThread(int[][] board) {
        super(board);
        this.board = board; 
        timeCheck=new ThreadTimeCheck(0);
    }
    

    public void divideRoles(){
        timeCheck.start();
        BoardBreakdown allRoles = new BoardBreakdown(this.board);
        ArrayList <int[]> rows=allRoles.getRows();
        ArrayList <int[]> columns=allRoles.getColumns();
        ArrayList <int[]> boxes=allRoles.getBoxes();
        ManageRoles boardRows=new ManageRoles(rows, Role.ROW);
        ManageRoles boardColumns=new ManageRoles(columns, Role.COLUMN);
        ManageRoles boardBoxes=new ManageRoles(boxes, Role.BOX);
        boardRows.CheckRoleValidity();
        boardColumns.CheckRoleValidity();
        boardBoxes.CheckRoleValidity();
        timeCheck.end();
        timeCheck.executeSummary();
    }



}