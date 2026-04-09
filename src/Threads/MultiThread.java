package Threads;

import java.util.ArrayList;
import java.util.List;
import Setup.BoardBreakdown;
import Setup.ElementsWithRole;
import Setup.ManageRoles;
import Setup.Role;

public class MultiThread extends Threads.ThreadAbstract {

    int[][] board;
    int threadNum;
    BoardBreakdown allRoles;
    Threads.ThreadTimeCheck timeCheck;

    public MultiThread(int[][] board, int threadNum){
        super(board);
        this.board=board;
        this.threadNum=threadNum;
        allRoles=new BoardBreakdown(board);
        timeCheck=new Threads.ThreadTimeCheck(threadNum);
    }

    public void divideRoles() {
    timeCheck.start();
    if (this.threadNum == 3) {
        
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
            ManageRoles boardBoxes = new ManageRoles(allRoles.getBoxes(), Setup.Role.BOX);
            System.out.println("Box thread starting...");
            boardBoxes.CheckRoleValidity();
            System.out.println("Box thread completed");
        });
        
        
        rowThread.start();
        columnThread.start();
        boxThread.start();
        
        
        try {
            rowThread.join();
            columnThread.join();
            boxThread.join();
            System.out.println("All validation threads completed!");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted!");
        }

    }
    else if (threadNum == 27) {
        List<Thread> allThreads = new ArrayList<>();
        
        
        for (int i = 0; i < 9; i++) {
            final int index = i;
            Thread rowThread = new Thread(() -> {
                System.out.println("Row " + (index + 1) + " thread starting...");
                ElementsWithRole rowElement = new ElementsWithRole(allRoles.getRows().get(index), Role.ROW, index + 1);
                rowElement.validityOfRole();
                System.out.println("Row " + (index + 1) + " thread completed");
            });
            allThreads.add(rowThread);
            rowThread.start(); 
        }
        
        
        for (int i = 0; i < 9; i++) {
            final int index = i;
            Thread colThread = new Thread(() -> {
                System.out.println("Column " + (index + 1) + " thread starting...");
                ElementsWithRole colElement = new ElementsWithRole(allRoles.getColumns().get(index), Role.COLUMN, index + 1);
                colElement.validityOfRole();
                System.out.println("Column " + (index + 1) + " thread completed");
            });
            allThreads.add(colThread);
            colThread.start(); 
        }
        
        
        for (int i = 0; i < 9; i++) {
            final int index = i;
            Thread boxThread = new Thread(() -> {
                System.out.println("Box " + (index + 1) + " thread starting...");
                ElementsWithRole boxElement = new ElementsWithRole(allRoles.getBoxes().get(index), Role.BOX, index + 1);
                boxElement.validityOfRole();
                System.out.println("Box " + (index + 1) + " thread completed");
            });
            allThreads.add(boxThread);
            boxThread.start(); 
        }
        
        
        for (Thread thread : allThreads) {
            try {
                thread.join(); 
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
                Thread.currentThread().interrupt();
            }
        }

        }
    timeCheck.end();
    timeCheck.executeSummary();
    }
}
