
import java.lang.classfile.constantpool.IntegerEntry;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import Csv.CsvImport;
import Setup.Elements;
import Setup.Role;
import Setup.ValidateGame;

void main() {
    
    IO.println(String.format("Hello and welcome!"));

    for (int i = 1; i <= 5; i++) {
        IO.println("i = " + i);
    }

    //int [] row={1,1,1,1,1,1,1,1,1};
    //int rowNum=2;
    //Role role = Role.BOX;
    //Elements e=new Elements(row, role, rowNum);
    //System.out.println(e.checkDuplicates(row));
    //Elements s=new Elements(new int[]{1,2,3,4,5,6,7,8,9}, Role.BOX, 3);
    //s.validityOfRole();

    CsvImport csvFile=new CsvImport("valid.csv");
    int [][] game=csvFile.convertToMatrix();

    ValidateGame vg=new ValidateGame(0,game);
    ArrayList <int[]> rows=vg.getRows();
    ArrayList <int[]> columns=vg.getColumns();
    for (int i=0;i<rows.size();i++){
        System.out.println("Row " + (i+1) + ":" + Arrays.toString(rows.get(i)));
    }
    for (int i=0;i<columns.size();i++){
        System.out.println("Column " + (i+1) + ":" + Arrays.toString(columns.get(i)));
    }
}