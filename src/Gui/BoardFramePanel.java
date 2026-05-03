package Gui;

import javax.swing.*;
import java.awt.*;

public class BoardFramePanel extends JPanel{

    private Board board;  

    public BoardFramePanel() {
        setLayout(new BorderLayout());
        board = new Board();  
        add(board, BorderLayout.CENTER);
        setPreferredSize(new Dimension(620, 620));
    }

    public int[][] getBoardValues() {
        return board.getBoardValues();
    }

    public void setBoardValues(int[][] game) {
        board.setBoardValues(game);
    }

    
    public static void main(String[] args) {
        JFrame testFrame = new JFrame("Board Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.add(new BoardFramePanel());
        testFrame.pack();
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
    }
}