package MVC;

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
        
    }
    
    public void logUserAction(String userAction) {
        // Append to log.txt
    }

    @Override
    public String verifyGame(GameState game) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'verifyGame'");
        return game.getState();
    }

    @Override
    public int[] solveGame(GameState game) throws InvalidGame {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solveGame'");
    }
    
    // etc...
}