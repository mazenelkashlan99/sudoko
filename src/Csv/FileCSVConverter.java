package Csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import MVC.Verification.GameState;

public class FileCSVConverter {
    private final Csv.Difficulty diffLevel;
    private Csv.CSVGenerator csvGenerator;
    private int[][] game2dEdited;
    private int[][] givenGameArray;

    public FileCSVConverter(Csv.Difficulty diffLevel,int[][] givenGameArray){
        this.diffLevel = diffLevel;
        this.givenGameArray=givenGameArray;
    }

    public void generateSpecificGameCopies(int[][] givenGameArray) throws IOException{
        FileCSVConverter csvEasy = new FileCSVConverter(Difficulty.EASY,givenGameArray);
        FileCSVConverter csvMedium = new FileCSVConverter(Difficulty.MEDIUM,givenGameArray);
        FileCSVConverter csvHard = new FileCSVConverter(Difficulty.HARD,givenGameArray);
        ArrayList <FileCSVConverter> csvArrayListByDiff=new ArrayList<FileCSVConverter>();
        GameState gamestate=new GameState(givenGameArray);
        if (gamestate.getState().equals("Valid")){
            csvArrayListByDiff.add(csvEasy);
            csvArrayListByDiff.add(csvMedium);
            csvArrayListByDiff.add(csvHard);
            for (var csv:csvArrayListByDiff){
                int[][] gameArrayWithZeros=csv.generateGameArray(givenGameArray);
                csv.givenDataArray_whenConvertToCSV_thenOutputCreated(gameArrayWithZeros);
            }
        }   
        else{
            throw new IllegalArgumentException("Game is: " + gamestate.getState());
        } 
    }

    public FileCSVConverter(Csv.Difficulty diffLevel){
        game2dEdited=new Csv.CSVGenerator().generateRandomValidBoard();
        csvGenerator=new Csv.CSVGenerator();
        this.diffLevel=diffLevel;
    }

    public static int[] flatten(int[][] data) {

        List<Integer> toReturn = new ArrayList<Integer>();
        for (int[] subList : data) {  
            for (int elem : subList) {
                toReturn.add(elem);
            }
        }
        return toReturn.stream().mapToInt(Integer::intValue).toArray();
}

    public int[][] getgame2dedited(){
        return game2dEdited;
    }

    public int[][] generateGameArray(int[][] gameBoard){
        switch (diffLevel){
            case EASY:
                game2dEdited= csvGenerator.replaceRandomPairs(10,gameBoard);
                break;
            case MEDIUM:
                game2dEdited= csvGenerator.replaceRandomPairs(20,gameBoard);
                break;
            case HARD:
                game2dEdited= csvGenerator.replaceRandomPairs(25,gameBoard);
                break;

        }
        return game2dEdited;
    }


    public int[][] generateGameArray(){

        switch (diffLevel){
            case EASY:
                game2dEdited= csvGenerator.replaceRandomPairs(10,game2dEdited);
                break;
            case MEDIUM:
                game2dEdited= csvGenerator.replaceRandomPairs(20,game2dEdited);
                break;
            case HARD:
                game2dEdited= csvGenerator.replaceRandomPairs(25,game2dEdited);
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
        String basePath = "games";
        
        String folderPath = switch (diffLevel) {
            case EASY -> basePath + "/easy/";
            case MEDIUM -> basePath + "/medium/";
            case HARD -> basePath + "/hard/";
        };
        
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        return folderPath + filename;    
    }

    public void givenDataArray_whenConvertToCSV_thenOutputCreated(int[][] board) throws IOException {
        int count=0;
        try (FileWriter obj = new FileWriter(determineFilePath())) {
            for (var i : flatten(board)){
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

    public void listFilesForFolder(final String fileFolder) {
        File folder=new File(fileFolder);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry.getAbsolutePath());
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

    public boolean folderContainsGame (String fileFolder){
        File folder=new File(fileFolder);
        File[] folders = folder.listFiles();
        return folders != null && folders.length != 0;
    }

    public void listGameFiles(){
        System.out.println("Easy Game Files:");
        listFilesForFolder("games/easy");
        System.out.println("Contains Files : " + folderContainsGame("games/easy"));
        System.out.println();
        System.out.println("Medium Game Files:");
        listFilesForFolder("games/medium");
        System.out.println("Contains Files : " + folderContainsGame("games/medium"));
        System.out.println();
        System.out.println("Hard Game Files:");
        listFilesForFolder("games/hard");
        System.out.println("Contains Files : " + folderContainsGame("games/hard"));
        System.out.println();
    }
}
