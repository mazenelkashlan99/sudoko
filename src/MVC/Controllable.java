package MVC;
import java.io.IOException;
import MVC.Exceptions.*;;
//
public interface Controllable
{
    Catalog getCatalog();
    int[][] getGame(char level) throws NotFoundException;
    void driveGames(int[][] source) throws SolutionInvalidException;

    // A boolean array which says if a specific cell is correct or invalid
    boolean[][] verifyGame(int[][] game);

    // contains the cell x, y and solution for each missing cell
    int[][] solveGame(int[][] game) throws InvalidGame;

    // Logs the user action
    void logUserAction(UserAction userAction) throws IOException;
}