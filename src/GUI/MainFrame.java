package GUI;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import MVC.*;
import MVC.Exceptions.InvalidGame;
import MVC.Exceptions.NotFoundException;
import MVC.Exceptions.SolutionInvalidException;

public class MainFrame extends JFrame implements Controllable{

    public MainFrame() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.Board board = new GUI.Board();
        add(board);
        pack();
        setLocationRelativeTo(null);

        setMinimumSize(new Dimension(420, 420));
    }

    public Catalog getCatalog() {
        return null;
    }
    public int[][] getGame(char level) throws NotFoundException {
        return null;
    }
    public void driveGames(int[][] source) throws SolutionInvalidException {
    }

    // A boolean array which says if a specific cell is correct or invalid
    public boolean[][] verifyGame(int[][] game) {
        return null;
    }

    // contains the cell x, y and solution for each missing cell
    public int[][] solveGame(int[][] game) throws InvalidGame {
        return null;
    }

    // Logs the user action
    public void logUserAction(UserAction userAction) throws IOException {
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}