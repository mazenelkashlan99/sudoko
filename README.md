# Sudoku Game

A fully-featured desktop Sudoku game built in Java using MVC architecture, Swing for the GUI, and file-based storage. The game supports multiple difficulty levels, automatic verification, a permutation‑based solver, undo, timer, move counter, and a clean, modern interface.

## Features

- **Three difficulty levels** – Easy (10 cells removed), Medium (20), Hard (25)
- **Random game generation** – Each game is generated from a valid solved board and saved as a CSV file
- **Permutation solver** – Solves the puzzle when exactly 5 empty cells remain (uses Iterator + Flyweight patterns)
- **Automatic verification** – Cells turn green when correct, red when conflicting; pre‑filled clues stay white
- **Undo** – Reverts the last move using a log file
- **Timer and move counter** – Tracks time spent and number of moves
- **Continue game** – Automatically loads the last unfinished game on startup
- **CSV file storage** – Games are saved in `games/easy/`, `games/medium/`, `games/hard/`
- **Keyboard navigation** – Use arrow keys or W,A,S,D buttons to navigate between cells; enter digits 1‑9
- **Solved cell highlighting** – Solved cells appear with black background and blue text

## Technologies

- Java (JDK 11 or higher)
- Swing (GUI)
- CSV files for game storage
- Java NIO for file I/O

## How to Run

### Using an IDE (IntelliJ, Eclipse, VS Code)
1. Open the project as an existing Java project.
2. Ensure `src` is marked as the source root.
3. Run `Gui.SudokuMainFrame` (the main class).
4. If no games exist, the application will ask you to provide a solved Sudoku CSV file (or you can generate games using `TestCSVGenerator`).

### Using Command Line
```bash
cd /path/to/sudoko
javac -d . src/*.java src/Csv/*.java src/Gui/*.java src/MVC/*.java src/MVC/Exceptions/*.java src/MVC/Verification/*.java src/MVC/Solver/*.java
java Gui.SudokuMainFrame
```

### How to Play
1- Start – Choose a difficulty from the main menu.

2- Navigate – Use arrow keys (or WASD) to move between cells.

3- Enter a number – Type a digit (1‑9) when a cell is selected.

4- Verify – The board auto‑verifies after each move; correct numbers become green, incorrect numbers turn red.

5- Solve – When exactly 5 cells are empty, the SOLVE button turns black with orange text; click it to auto‑fill the remaining cells.

6- Timer – Starts automatically when a game loads; stops on completion.

7- Complete – When the board is fully filled and correct, a popup shows your time and move count.
 
8- Exit – Click EXIT to close the application.

## Final

**Thanks for checking out my Sudoku project!**  
Built by Mazen Elkashlan.
