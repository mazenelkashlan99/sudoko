package Csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVWriter;

public class CSVFileByDifficuilty {

    private final Difficulty diffLevel;
    private CSVGenerator csv;
    private int[] game1d;
    private int[][] game2d;
    private int[][] game2dEdited;


    public CSVFileByDifficuilty(Difficulty diffLevel){
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

    public void makeCSVFIle(){
        String filepath=determineFilePath();
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(filepath);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // create a List which contains String array
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Name", "Class", "Marks" });
            data.add(new String[] { "Aman", "10", "620" });
            data.add(new String[] { "Suraj", "10", "630" });
            writer.writeAll(data);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
