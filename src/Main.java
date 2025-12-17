
import Setup.Role;
import Setup.ValidateRole;

void main() {
    
    IO.println(String.format("Hello and welcome!"));

    for (int i = 1; i <= 5; i++) {
        IO.println("i = " + i);
    }

    int [] row={1,1,1,1,1,1,1,1,1};
    int rowNum=2;
    Role role = Role.BOX;
    ValidateRole vr=new ValidateRole(row, role, rowNum);
    vr.validityOfRole();
}
