package MVC;

import Csv.Difficulty;
import Csv.FileCSVConverter;
import MVC.Exceptions.InvalidGame;
import MVC.Exceptions.NotFoundException;
import MVC.Exceptions.SolutionInvalidException;
import MVC.Solver.PermutationSolverIterator;
import MVC.Verification.GameState;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

public class SudokuController implements Viewable {
    private GameState currentGame;
    private FileCSVConverter fileLoader;
    private Difficulty difficulty;

    public SudokuController(){}

    public SudokuController(Controllable view, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.fileLoader = new FileCSVConverter(this.difficulty);
        PermutationSolverIterator permutationIterator=new PermutationSolverIterator();
    }

    @Override
    public Catalog getCatalog() {
        String easyFilePath = "games/easy";
        String mediumFilePath = "games/medium";
        String hardFilePath = "games/hard";
        String incompleteFilePath = "games/incomplete";

        boolean allModesExist = fileLoader.folderContainsGame(easyFilePath) && fileLoader.folderContainsGame(mediumFilePath) && fileLoader.folderContainsGame(hardFilePath);
        boolean hasIncomplete = fileLoader.folderContainsGame(incompleteFilePath);

        Catalog c1 = new Catalog();
        c1.current = hasIncomplete;
        c1.allModesExist = allModesExist;
        return c1;
    }

    @Override
    public void logUserAction(String userAction) {
        String pathway = "games/incomplete/log.txt";
        try {
            final Path path = Paths.get(pathway);
            Files.write(path, Arrays.asList(userAction), StandardCharsets.UTF_8,
                    Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        } catch (final IOException ioe) {
            System.out.println("Error writing to log: " + ioe.getMessage());
        }
    }

    @Override
    public String verifyGame(GameState game) {
        return game.getState();
    }

    @Override
    public int[] solveGame(GameState game) throws InvalidGame {
        PermutationSolverIterator permutationIterator=new PermutationSolverIterator();
        ArrayList<Integer> zeroIndexes = new ArrayList<Integer>();
        if (game.getState().equals("Valid") || game.getState().equals("Invalid")) {
            throw new InvalidGame("Cannot solve game with state: " + game.getState());
        }
        int[][] gameArr2d = game.getGame();
        for (int i = 0; i < 81; i++) {
            int irow = i / 9;
            int icolumn = i % 9;
            if (gameArr2d[irow][icolumn] == 0) {
                zeroIndexes.add(i);
            }
        }
        if (zeroIndexes.size() != 5) {
            throw new InvalidGame("Game doesn't have 5 0s");
        } else {
            ArrayList<Integer> zeroValuesReplacement = null;
            while (!game.getState().equals("Valid")) {
                zeroValuesReplacement = permutationIterator.generateNumbers();
                for (int i = 0; i < 5; i++) {
                    int row = zeroIndexes.get(i) / 9;
                    int column = zeroIndexes.get(i) % 9;
                    gameArr2d[row][column] = zeroValuesReplacement.get(i);
                    game.setGame(gameArr2d);
                }
            }
            int[] correctZeroValuesReplacement = new int[5];
            for (int i = 0; i < 5; i++) {
                correctZeroValuesReplacement[i] = zeroValuesReplacement.get(i);
            }
            return correctZeroValuesReplacement;
        }
    }

    @Override
    public GameState getGame(Difficulty level) throws NotFoundException {
        this.currentGame = new GameState(fileLoader.getgame2dedited());
        return this.currentGame;
    }

    @Override
    public void driveGames(GameState source) throws SolutionInvalidException {
        String state = verifyGame(source);
        if (!state.equals("Valid")) {
            throw new SolutionInvalidException("Source game is " + state);
        }
        try {
            fileLoader.generateSpecificGameCopies(source.getGame());
        } catch (IOException e) {
            throw new SolutionInvalidException("Failed to save game files: " + e.getMessage());
        }
    }
}