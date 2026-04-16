package MVC;

import Csv.Difficulty;
import Csv.FileCSVConverter;
import MVC.Exceptions.InvalidGame;
import MVC.Verification.GameState;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class SudokuController implements Viewable {
    private GameState currentGame;
    private Controllable view;
    private FileCSVConverter fileLoader;
    private Difficulty difficulty;

    public SudokuController(Controllable view, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.view = view;
        this.fileLoader = new FileCSVConverter(this.difficulty);
    }

    @Override
    public Catalog getCatalog() {
        String easyFilePath = "games/easy";
        String mediumFilePath = "games/medium";
        String hardFilePath = "games/hard";
        String incompleteFilePath = "games/incomplete";

        boolean allModesExist = fileLoader.folderContainsGame(easyFilePath) &&
                fileLoader.folderContainsGame(mediumFilePath) &&
                fileLoader.folderContainsGame(hardFilePath);
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
        throw new UnsupportedOperationException("Unimplemented method 'solveGame'");
    }

    // Missing Viewable methods - add these:
    @Override
    public GameState getGame(Difficulty level) throws NotFoundException {
        // TODO: Load a random game from the specified difficulty folder
        throw new UnsupportedOperationException("Unimplemented method 'getGame'");
    }

    @Override
    public void driveGames(GameState source) throws SolutionInvalidException {
        // TODO: Verify source, then generate easy/medium/hard
        throw new UnsupportedOperationException("Unimplemented method 'driveGames'");
    }
}