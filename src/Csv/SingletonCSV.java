package Csv;

public class SingletonCSV {

    private static CsvImport instance;

    private SingletonCSV() {}


    public static CsvImport getInstance(String csvfilename) {

        synchronized (SingletonCSV.class){
            if (instance==null){
                instance=new CsvImport(csvfilename);
            }
        }

        return instance;
    }
    
}