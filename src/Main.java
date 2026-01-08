
import Csv.CsvImport;
import Setup.ValidateGame;

void main() {
    

    CsvImport csvFile=new CsvImport("invalid.csv");


    ValidateGame vg=new ValidateGame(0,csvFile.convertToMatrix());
    vg.threadNumMatching();

    

}