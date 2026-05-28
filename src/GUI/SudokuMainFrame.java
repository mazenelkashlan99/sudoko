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
import java.util.*;

public class SudokuMainFrame extends JFrame implements Controllable {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private SudokuMenuPanel menuPanel;
    private BoardFramePanel boardPanel;
    private JButton solveButton;
    private JButton exitButton;
    private JLabel statusLabel;
    private MVC.Viewable controller;
    private Difficulty chosenDifficulty;
    private Set<Integer> solvedCells = new HashSet<>();
    private boolean completionMessageShown = false;

    private javax.swing.Timer gameTimer;
    private int elapsedSeconds = 0;
    private int moveCount = 0;
    private boolean ignoreBoardChange = false;

    public SudokuMainFrame() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 850);
        setMinimumSize(new Dimension(900, 800));
        setPreferredSize(new Dimension(950, 850));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new SudokuMenuPanel(this);
        mainPanel.add(menuPanel, "menu");

        JPanel gameCard = new JPanel(new BorderLayout());
        boardPanel = new BoardFramePanel();
        gameCard.add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));
        bottomPanel.setBackground(new Color(30, 30, 40));

        solveButton = createStyledButton("SOLVE", new Color(70, 130, 200), Color.WHITE);
        solveButton.setPreferredSize(new Dimension(160, 55));
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
        solveButton.setEnabled(false);

        exitButton = createStyledButton("EXIT", new Color(60, 60, 80), Color.CYAN);
        exitButton.setPreferredSize(new Dimension(160, 55));
        exitButton.addActionListener(e -> System.exit(0));

        statusLabel = new JLabel("Status: - | Time: 0s | Moves: 0 | Empty: 0");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(Color.WHITE);

        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(solveButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(exitButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(statusLabel);
        bottomPanel.add(Box.createHorizontalGlue());

        gameCard.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(gameCard, "game");
        add(mainPanel);

        controller = new SudokuController(this, Difficulty.EASY);
        Catalog cat = controller.getCatalog();
        menuPanel.setContinueButtonEnabled(cat.current);

        boardPanel.getBoard().setOnBoardChange(() -> {
            if (!ignoreBoardChange) {
                SwingUtilities.invokeLater(() -> {
                    incrementMoveCount();
                    updateSolveButton();
                    updateStatus();
                    updateColors();
                    checkCompletion();
                });
            }
        });

        pack();
        setLocationRelativeTo(null);
        cardLayout.show(mainPanel, "menu");
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.brighter(), 2),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void incrementMoveCount() {
        moveCount++;
        updateStatus();
    }

    private void startTimer() {
        if (gameTimer != null && gameTimer.isRunning()) gameTimer.stop();
        elapsedSeconds = 0;
        gameTimer = new javax.swing.Timer(1000, e -> {
            elapsedSeconds++;
            updateStatus();
        });
        gameTimer.start();
    }

    private void stopTimer() {
        if (gameTimer != null) gameTimer.stop();
    }

    private void resetGameStats() {
        stopTimer();
        moveCount = 0;
        elapsedSeconds = 0;
        solvedCells.clear();
        completionMessageShown = false;
        startTimer();
        updateStatus();
    }

    public void onDifficultyChosen(char level) {
        chosenDifficulty = convertCharToDifficulty(level);
        if (chosenDifficulty == null) {
            JOptionPane.showMessageDialog(this, "Invalid difficulty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            ignoreBoardChange = true;
            int[][] board = getGame(level);
            if (board != null) {
                resetGameStats();
                cardLayout.show(mainPanel, "game");
                updateSolveButton();
                updateStatus();
                updateColors();
                pack();
                setLocationRelativeTo(null);
            }
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, "Failed to load game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            ignoreBoardChange = false;
        }
    }

    public void onContinueGame() {
        try {
            ignoreBoardChange = true;
            int[][] board = getGame('I');
            resetGameStats();
            cardLayout.show(mainPanel, "game");
            boardPanel.setBoardValues(board);
            updateSolveButton();
            updateStatus();
            updateColors();
            pack();
            setLocationRelativeTo(null);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(this, "No incomplete game found", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            ignoreBoardChange = false;
        }
    }

    @Override
    public Catalog getCatalog() { return controller.getCatalog(); }

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
            solveButton.setBackground(new Color(70, 130, 200));
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
        String conflictMsg = "";
        if ("Invalid".equals(state)) {
            Boolean[][] result = computeVerification(board);
            outer: for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (!result[i][j]) {
                        conflictMsg = String.format(" | Conflict at (%d,%d) value %d", i+1, j+1, board[i][j]);
                        break outer;
                    }
                }
            }
        }
        statusLabel.setText(String.format("Status: %s%s | Time: %ds | Moves: %d | Empty: %d",
                state, conflictMsg, elapsedSeconds, moveCount, zeros));
    }

    private void updateColors() {
        int[][] board = boardPanel.getBoardValues();
        Boolean[][] result = computeVerification(board);
        boardPanel.applyVerification(result);
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
                stopTimer();
                JOptionPane.showMessageDialog(this,
                        "Sudoku now complete!\nTime: " + elapsedSeconds + " seconds\nMoves: " + moveCount,
                        "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private Boolean[][] computeVerification(int[][] game) {
        Boolean[][] result = new Boolean[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                result[i][j] = true;

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