package Gui;

import javax.swing.*;
import Csv.Difficulty;
import MVC.Controllable;
import MVC.SudokuController;
import MVC.Catalog;
import MVC.Exceptions.*;
import MVC.Verification.GameState;
import MVC.UserAction;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SudokuMainFrame extends JFrame implements Controllable {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private SudokuMenuPanel menuPanel;
    private BoardFramePanel boardPanel;
    private JButton solveButton;
    private JLabel statusLabel;
    private MVC.Viewable controller;
    private Difficulty chosenDifficulty;
    private Set<Integer> solvedCells = new HashSet<>();
    private boolean completionMessageShown = false;

    public SudokuMainFrame() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(750, 800));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Menu card
        menuPanel = new SudokuMenuPanel(this);
        mainPanel.add(menuPanel, "menu");

        // Game card: board + bottom panel
        JPanel gameCard = new JPanel(new BorderLayout());
        boardPanel = new BoardFramePanel();
        gameCard.add(boardPanel, BorderLayout.CENTER);

        // Bottom panel with solve button and status
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.setBackground(new Color(30, 30, 40));

        solveButton = new JButton("SOLVE");
        solveButton.setOpaque(true);
        solveButton.setContentAreaFilled(true);
        solveButton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        solveButton.setForeground(Color.WHITE);
        solveButton.setBackground(new Color(70, 130, 200));
        solveButton.setFocusPainted(false);
        solveButton.setPreferredSize(new Dimension(160, 60));
        solveButton.setEnabled(false);
        solveButton.addActionListener(e -> {
            try {
                int[][] current = boardPanel.getBoardValues();
                solveGame(current);
                solveButton.setEnabled(false);
                updateStatus();
                updateColors();
                checkCompletion();
            } catch (InvalidGame ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Cannot Solve", JOptionPane.ERROR_MESSAGE);
            }
        });

        statusLabel = new JLabel("Status: ");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(solveButton);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(statusLabel);
        bottomPanel.add(Box.createHorizontalGlue());

        gameCard.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(gameCard, "game");

        add(mainPanel);

        controller = new SudokuController(this, Difficulty.EASY);
        Catalog cat = controller.getCatalog();
        menuPanel.setContinueButtonEnabled(cat.current);

        boardPanel.getBoard().setOnBoardChange(() -> {
            SwingUtilities.invokeLater(() -> {
                updateSolveButton();
                updateStatus();
                updateColors();
                checkCompletion();
            });
        });

        pack();
        setLocationRelativeTo(null);
        cardLayout.show(mainPanel, "menu");
    }

    public void onDifficultyChosen(char level) {
        chosenDifficulty = convertCharToDifficulty(level);
        if (chosenDifficulty == null) {
            JOptionPane.showMessageDialog(this, "Invalid difficulty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int[][] board = getGame(level);
            if (board != null) {
                solvedCells.clear();
                completionMessageShown = false;
                cardLayout.show(mainPanel, "game");
                updateSolveButton();
                updateStatus();
                updateColors();
                pack();
                setLocationRelativeTo(null);
            }
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, "Failed to load game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onContinueGame() {
        try {
            int[][] board = getGame('I');
            solvedCells.clear();
            completionMessageShown = false;
            cardLayout.show(mainPanel, "game");
            boardPanel.setBoardValues(board);
            updateSolveButton();
            updateStatus();
            updateColors();
            pack();
            setLocationRelativeTo(null);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, "No incomplete game found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------- Controllable methods ----------
    @Override
    public Catalog getCatalog() {
        return controller.getCatalog();
    }

    @Override
    public int[][] getGame(char level) throws NotFoundException {
        Difficulty diff = convertCharToDifficulty(level);
        if (diff == null && level != 'I')
            throw new NotFoundException("Invalid difficulty");
        GameState gameState;
        if (level == 'I')
            gameState = ((SudokuController) controller).getIncompleteGame();
        else
            gameState = controller.getGame(diff);
        int[][] board = gameState.getGame();
        boardPanel.setBoardValues(board);
        return board;
    }

    @Override
    public void driveGames(int[][] source) throws SolutionInvalidException {
        controller.driveGames(new GameState(source));
        JOptionPane.showMessageDialog(this, "Games generated!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public Boolean[][] verifyGame(int[][] game) {
        return computeVerification(game);
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        GameState gameGiven = new GameState(game);
        int[] solved = controller.solveGame(gameGiven);
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (game[i][j] == 0) {
                    int index = i * 9 + j;
                    solvedCells.add(index);
                    game[i][j] = solved[count++];
                }
            }
        }
        boardPanel.setBoardValues(game);
        // Apply black background and blue text to solved cells
        for (int index : solvedCells) {
            int row = index / 9;
            int col = index % 9;
            boardPanel.getBoard().setCellBackground(row, col, Color.BLACK);
            boardPanel.getBoard().setCellTextColor(row, col, Color.BLUE);
        }
        return game;
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        controller.logUserAction(userAction.toString());
    }

    // ---------- Private helpers ----------
    private Difficulty convertCharToDifficulty(char level) {
        switch (Character.toUpperCase(level)) {
            case 'E': return Difficulty.EASY;
            case 'M': return Difficulty.MEDIUM;
            case 'H': return Difficulty.HARD;
            default: return null;
        }
    }

    private void updateSolveButton() {
        int[][] board = boardPanel.getBoardValues();
        int zeros = 0;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] == 0) zeros++;
        
        boolean enable = (zeros == 5);
        solveButton.setEnabled(enable);
        
        if (enable) {
            solveButton.setBackground(Color.BLACK);
            solveButton.setForeground(Color.ORANGE);
        } else {
            solveButton.setBackground(new Color(70, 130, 200)); // original blue
            solveButton.setForeground(Color.WHITE);
        }
    }

    private void updateStatus() {
        int[][] board = boardPanel.getBoardValues();
        String state = controller.verifyGame(new GameState(board));
        int zeros = 0;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] == 0) zeros++;
        statusLabel.setText("Status: " + state + " | Remaining: " + zeros);
    }

    private void updateColors() {
        int[][] board = boardPanel.getBoardValues();
        Boolean[][] result = computeVerification(board);
        boardPanel.applyVerification(result);
        // Reapply black background and blue text to solved cells
        for (int index : solvedCells) {
            int row = index / 9;
            int col = index % 9;
            boardPanel.getBoard().setCellBackground(row, col, Color.BLACK);
            boardPanel.getBoard().setCellTextColor(row, col, Color.BLUE);
        }
    }

    private void checkCompletion() {
        if (completionMessageShown) return;
        int[][] board = boardPanel.getBoardValues();
        boolean allFilled = true;
        for (int i = 0; i < 9 && allFilled; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    allFilled = false;
                    break;
                }
            }
        }
        if (allFilled) {
            String state = controller.verifyGame(new GameState(board));
            if ("Valid".equals(state)) {
                completionMessageShown = true;
                JOptionPane.showMessageDialog(this, "Sudoku now complete!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private Boolean[][] computeVerification(int[][] game) {
        Boolean[][] result = new Boolean[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                result[i][j] = true;

        // rows
        for (int row = 0; row < 9; row++) {
            HashMap<Integer, Integer> seen = new HashMap<>();
            for (int col = 0; col < 9; col++) {
                int val = game[row][col];
                if (val != 0) {
                    if (seen.containsKey(val)) {
                        result[row][seen.get(val)] = false;
                        result[row][col] = false;
                    } else {
                        seen.put(val, col);
                    }
                }
            }
        }
        // columns
        for (int col = 0; col < 9; col++) {
            HashMap<Integer, Integer> seen = new HashMap<>();
            for (int row = 0; row < 9; row++) {
                int val = game[row][col];
                if (val != 0) {
                    if (seen.containsKey(val)) {
                        result[seen.get(val)][col] = false;
                        result[row][col] = false;
                    } else {
                        seen.put(val, row);
                    }
                }
            }
        }
        // boxes
        for (int box = 0; box < 9; box++) {
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;
            HashMap<Integer, int[]> seen = new HashMap<>();
            for (int dr = 0; dr < 3; dr++) {
                for (int dc = 0; dc < 3; dc++) {
                    int row = startRow + dr;
                    int col = startCol + dc;
                    int val = game[row][col];
                    if (val != 0) {
                        if (seen.containsKey(val)) {
                            int[] prev = seen.get(val);
                            result[prev[0]][prev[1]] = false;
                            result[row][col] = false;
                        } else {
                            seen.put(val, new int[]{row, col});
                        }
                    }
                }
            }
        }
        return result;
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