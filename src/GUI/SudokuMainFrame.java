package Gui;

import javax.swing.*;

import MVC.Controllable;

import java.awt.*;

public class SudokuMainFrame extends JFrame{

    public SudokuMainFrame() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 700));

        SudokuMenuPanel menuPanel = new SudokuMenuPanel();
        add(menuPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SudokuMainFrame().setVisible(true);
        });
    }
}