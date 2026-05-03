package Gui;

import javax.swing.*;

import Csv.Difficulty;
import MVC.Controllable;
import MVC.Catalog;
import MVC.Exceptions.*;
import MVC.UserAction;
import java.awt.*;
import java.io.IOException;

public class SudokuMainFrame extends JFrame implements Controllable {

    private SudokuMenuPanel menuPanel;
    private BoardFramePanel boardPanel;
    private MVC.Viewable controller;   // will be initialized later
    private Catalog controllerCatalog;

    public SudokuMainFrame() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 750));

        // Create panels
        menuPanel = new SudokuMenuPanel(this);  // pass reference to main frame
        boardPanel = new BoardFramePanel();

        // Layout
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

        this.controller=new MVC.SudokuController(this,Difficulty.EASY);
        controllerCatalog=this.controller.getCatalog();
    }

    // ---------- Controllable methods (blueprint only, no implementation) ----------

    @Override
    public Catalog getCatalog() {
        // TODO: delegate to controller
        return null;
    }

    @Override
    public int[][] getGame(char level) throws NotFoundException {
        // TODO: call controller.getGame(), update boardPanel, return board data
        return null;
    }

    @Override
    public void driveGames(int[][] source) throws SolutionInvalidException {
        // TODO: delegate to controller
    }

    @Override
    public Boolean[][] verifyGame(int[][] game) {
        // TODO: delegate to controller, show result dialog, return error map (or null)
        return null;
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        // TODO: call controller.solveGame(), apply solution to board, return new board
        return null;
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        // TODO: delegate to controller
    }

    // Optional helper for menu
    public void onDifficultySelected(char level) {
        // TODO: call getGame(level)
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