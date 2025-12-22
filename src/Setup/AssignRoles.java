package Setup;

import java.util.ArrayList;

public class AssignRoles {

    int start=1;
    ArrayList<int []> roleElements;
    Role role;

    public AssignRoles(ArrayList<int[]> roleElements,Role role){
        this.role=role;
        this.roleElements=roleElements;
    }

    public void CheckRoleValidity(){
       for (var roleArr : roleElements){
                   Elements TElements = new Elements(roleArr,role,start);
                   TElements.validityOfRole();
                   start++;
        }
    }
}