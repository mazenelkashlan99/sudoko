package Csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileCSVConverter {
    private static int fileCount=0;
    private final Difficulty diffLevel;
    private CSVGenerator csv;
    private int[] game1d;
    private int[][] game2d;
    private int[][] game2dEdited;


    public FileCSVConverter(Difficulty diffLevel){
        csv=new CSVGenerator();
        this.diffLevel=diffLevel;
        game1d=csv.generateNumbers();
        System.out.println(Arrays.toString(game1d));
        game2d= csv.convertToTwoDimension();
        System.out.println(Arrays.deepToString(game2d));
    }

    public static int[] flatten(int[][] data) {

        List<Integer> toReturn = new ArrayList<Integer>();
        for (int[] sublist : Arrays.asList(data)) {
            for (int elem : sublist) {
                toReturn.add(elem);
            }
        }
        return toReturn.stream().mapToInt(Integer::intValue).toArray();

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
            case EASY ->"C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\easy\\game_001.csv";
            case MEDIUM -> "C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\medium";
            case HARD -> "C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\hard";
        };

    }

    public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {

        int count=0;
        try (FileWriter obj = new FileWriter(determineFilePath())) {
            for (var i : flatten(game2dEdited)){
                obj.append(String.valueOf(i));
                if ((count+1)%9==0){
                    obj.append("\n");
                }
                else{
                    obj.append(",");
                }
                count++;
            }
        }

    }

}
