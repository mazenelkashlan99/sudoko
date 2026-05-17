package Gui;

import javax.swing.*;
import java.awt.*;

public class BoardFramePanel extends JPanel {
    private Board board;

    public BoardFramePanel() {
        setLayout(new BorderLayout());
        board = new Board();
        add(board, BorderLayout.CENTER);
        setPreferredSize(new Dimension(620, 620));
    }

    public int[][] getBoardValues(){ 
        return board.getBoardValues();
    }

    public void setBoardValues(int[][] game) { 
        board.setBoardValues(game); 
    }

    public void applyVerification(Boolean[][] result){ 
        board.applyVerification(result); 
    }

    public Board getBoard(){ 
        return board; 
    }
    
}