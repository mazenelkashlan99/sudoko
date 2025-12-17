package Setup;

import java.util.Arrays;

public class ValidateRole{

    int [] elements;
    Role role;
    int roleNumber;

    public ValidateRole(int[] elements, Role role, int roleNumber) {
        this.elements = elements;
        this.role = role;
        this.roleNumber = roleNumber;
    }

    boolean checkLength(){
      return (this.elements.length==9);
    }

    boolean checkElementsVariation(){
        int n = this.elements.length;
        for(int i = 0; i < n - 1; i++) {
            for(int j = i + 1; j < n; j++) {
                if(this.elements[i] == this.elements[j]){
                    return false; 
                }
            }
        }
        return true;
    }

    public void validityOfRole(){
        if(checkLength() && checkElementsVariation()){
            System.out.println("Correct " + this.role.toString());
        }
        else{
            System.out.println("Invalid " + this.role.toString() + " at index [" + this.roleNumber +"] : " + Arrays.toString(this.elements)); 
        }
    }

}