package Csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileCSVConverter {
    private static int fileCount=0;
    private final Difficulty diffLevel;
    private CSVGenerator csv;
    private int[][] game2dEdited;


    public FileCSVConverter(Difficulty diffLevel){
        game2dEdited=new CSVGenerator().generateRandomValidBoard();
        csv=new CSVGenerator();
        this.diffLevel=diffLevel;
    }

    public static int[] flatten(int[][] data) {

        List<Integer> toReturn = new ArrayList<Integer>();
        for (int[] sublist : data) {  // Removed Arrays.asList()
            for (int elem : sublist) {
                toReturn.add(elem);
            }
        }
        return toReturn.stream().mapToInt(Integer::intValue).toArray();
}

    public int[][] getgame2dedited(){
        return game2dEdited;
    }

    public int[][] generateGameArray(){

        switch (diffLevel){
            case EASY:
                game2dEdited= csv.replaceRandomPairs(10,game2dEdited);
                break;
            case MEDIUM:
                game2dEdited= csv.replaceRandomPairs(20,game2dEdited);
                break;
            case HARD:
                game2dEdited= csv.replaceRandomPairs(25,game2dEdited);
                break;

        }
        return game2dEdited;
    }

    public String csvStringFileNameGenerator() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(date); 
        String diff = diffLevel.toString().toLowerCase();
        return String.format("%s_%s.csv", diff, timestamp);
        
    }

    public String determineFilePath() {
        String filename = csvStringFileNameGenerator();
        String basePath = "C:\\Users\\Mazen El-Kashlan\\Documents\\GitHub\\sudoko\\";
        
        String folderPath = switch (diffLevel) {
            case EASY -> basePath + "easy\\";
            case MEDIUM -> basePath + "medium\\";
            case HARD -> basePath + "hard\\";
        };
        
        // Create directory if it doesn't exist
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        return folderPath + filename;
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
