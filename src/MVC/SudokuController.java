package MVC;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import Csv.Difficulty;
import Csv.FileCSVConverter;
import MVC.Exceptions.InvalidGame;
import MVC.Verification.GameState;

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
    
    // Implement all Viewable methods (empty for now)
    public Catalog getCatalog() {
         // Check folders, return Catalog
        String easyFilePath="C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\games\\easy\\";
        String mediumFilePath="C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\games\\medium\\";
        String hardFilePath="C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\games\\hard\\";
        String incompleteFilePath="C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\games\\incomplete\\";
        boolean allModesExist=fileLoader.folderContainsGame(easyFilePath) && fileLoader.folderContainsGame(mediumFilePath) && fileLoader.folderContainsGame(hardFilePath);
        boolean hasIncomplete=fileLoader.folderContainsGame(incompleteFilePath);
        Catalog c1=new Catalog();
        c1.current=hasIncomplete;
        c1.allModesExist=allModesExist;
        return c1;
    }
    
    public void logUserAction(String userAction) {
        // Append to log.txt
        String pathway=("C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\games\\incomplete\\log.txt\\");
        try {
            final Path path = Paths.get(pathway);
            Files.write(path, Arrays.asList(userAction), StandardCharsets.UTF_8,
                Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        } catch (final IOException ioe) {
            System.out.println("Error exists");
        }
    }

    @Override
    public String verifyGame(GameState game) {
        return game.getState();   
    }

    @Override
    public int[] solveGame(GameState game) throws InvalidGame {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solveGame'");
    }
    
    // etc...
}