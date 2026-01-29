package Threads;

import java.util.ArrayList;

import Setup.BoardBreakdown;
import Setup.ManageRoles;
import Setup.Role;

public class MultiThread extends ThreadAbstract{

    int[][] board;
    int threadNum;

    public MultiThread(int[][] board, int threadNum){
        super(board);
        this.board=board;
        this.threadNum=threadNum;
    }

    public void divideRoles() {
    if (this.threadNum == 3) {
        BoardBreakdown allRoles = new BoardBreakdown(this.board);
        
        // Create threads
        Thread rowThread = new Thread(() -> {
            ManageRoles boardRows = new ManageRoles(allRoles.getRows(), Role.ROW);
            System.out.println("Row thread starting...");
            boardRows.CheckRoleValidity();
            System.out.println("Row thread completed");
        });
        
        Thread columnThread = new Thread(() -> {
            ManageRoles boardColumns = new ManageRoles(allRoles.getColumns(), Role.COLUMN);
            System.out.println("Column thread starting...");
            boardColumns.CheckRoleValidity();
            System.out.println("Column thread completed");
        });
        
        Thread boxThread = new Thread(() -> {
            ManageRoles boardBoxes = new ManageRoles(allRoles.getBoxes(), Role.BOX);
            System.out.println("Box thread starting...");
            boardBoxes.CheckRoleValidity();
            System.out.println("Box thread completed");
        });
        
        // START the threads
        rowThread.start();
        columnThread.start();
        boxThread.start();
        
        // WAIT for them to finish (if you want main to wait)
        try {
            rowThread.join();
            columnThread.join();
            boxThread.join();
            System.out.println("All validation threads completed!");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted!");
        }
    }

    }

}
