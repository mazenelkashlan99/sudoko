package Csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvImport {
    String csvFile;

    public CsvImport(String csvFile) {
        this.csvFile=csvFile;
    }

    public int [][] convertToMatrix(){
    String filePath = this.csvFile;
    int rows = 9;
    int cols = 9;
    int[][] data = new int[rows][cols];
     
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        int row = 0;
        while ((line = br.readLine()) != null && row < rows) {
            
            line = line.replaceFirst("^\\d+\\s+", "");
            
            String[] values = line.split(",\\s*");
            
            for (int col = 0; col < Math.min(values.length, cols); col++) {
                
                data[row][col] = Integer.parseInt(values[col].trim());
            }
            row++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.err.println("Number format error: " + e.getMessage());
        e.printStackTrace();
    }
     
    return data;
  }
}

