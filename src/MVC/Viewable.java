package MVC;

import java.io.IOException;

import MVC.Verification.GameState;
import MVC.Exceptions.*;
interface Viewable{

    Catalog getCatalog();

    // Returns a random game with the specified difficulty
    // Note: the Game class is the representation of the sudoku game in the controller
    // Get a sourceSolution and generates three levels of difficulty
    // given a game, if invalid returns invalid and the locates the invalid duplicates
    // if valid and complete, return a value
    // if valid and incomplete, returns another value
    // The exact representation as a string is done as you best see fit
    String verifyGame(GameState game);

    // returns the correct combination for the missing numbers
    // Hint: So, there are many ways you can approach this, one way is
    // to have a way to map an index in the combination array to its location in the board
    // one other way to try to encode the location and the answer all in just one int
    int[] solveGame(GameState game) throws InvalidGame;

    // Logs the user action
    void logUserAction(String userAction) throws IOException;
}