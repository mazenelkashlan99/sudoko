package Setup;

import java.util.ArrayList;

public class ManageRoles {

    int start=1;
    ArrayList<int []> roleElements;
    Role role;

    public ManageRoles(ArrayList<int[]> roleElements,Role role){
        this.role=role;
        this.roleElements=roleElements;
    }

    public void CheckRoleValidity(){
       for (var roleArr : roleElements){
                   ElementsWithRole TElementsWithRole = new ElementsWithRole(roleArr,role,start);
                   TElementsWithRole.validityOfRole();
                   start++;
        }
    }
}