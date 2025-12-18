
import Setup.Elements;
import Setup.Role;
import Setup.Elements;

void main() {
    
    IO.println(String.format("Hello and welcome!"));

    for (int i = 1; i <= 5; i++) {
        IO.println("i = " + i);
    }

    int [] row={1,1,1,1,1,1,1,1,1};
    int rowNum=2;
    Role role = Role.BOX;
    Elements e=new Elements(row, role, rowNum);
    System.out.println(e.checkDuplicates(row));
}
