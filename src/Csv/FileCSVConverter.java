package Csv;

import java.util.Arrays;

public class FileCSVConverter {
    private final Difficulty diffLevel;
    private CSVGenerator csv;
    private int[] game1d;
    private int[][] game2d;
    private int[][] game2dEdited;


    public FileCSVConverter(Csv.Difficulty diffLevel){
        csv=new CSVGenerator();
        this.diffLevel=diffLevel;
        game1d=csv.generateNumbers();
        System.out.println(Arrays.toString(game1d));
        game2d= csv.convertToTwoDimension();
        System.out.println(Arrays.deepToString(game2d));
    }

    public int[][] generateGameArray(){

        switch (diffLevel){
            case EASY:
                game2dEdited= csv.replaceRandomPairs(10);
                break;
            case MEDIUM:
                game2dEdited= csv.replaceRandomPairs(20);
                break;
            case HARD:
                game2dEdited= csv.replaceRandomPairs(25);
                break;

        }
        return game2dEdited;
    }

    public String determineFilePath(){

        return switch (diffLevel) {
            case EASY -> "C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\easy";
            case MEDIUM -> "C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\medium";
            case HARD -> "C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\hard";
        };

    }

}
