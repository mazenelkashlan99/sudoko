package Setup;
import Csv.*;
public class GameCreation {
    String filename;

    public GameCreation(String filename){
            this.filename=filename;
    }

    public int [][] getBoard(){
        CsvImport x=SingletonCSV.getInstance(filename);
        return x.convertToMatrix();
    }
         
}