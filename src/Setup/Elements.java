package Setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.event.InternalFrameAdapter;

public class Elements{

    int [] elements;
    Role role;
    int roleNumber;

    public Elements(int[] elements, Role role, int roleNumber) {
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

    //[1,1,1,1,1,1,1,1,1]

    public HashMap<Integer,ArrayList<Integer>> checkDuplicates(int [] elements){
        HashMap<Integer,ArrayList<Integer>> duplicateValuesAndIndex=new HashMap<Integer,ArrayList<Integer>>();
         ArrayList<Integer> dupIndex=new ArrayList<Integer>();
        for (int i=0;i<elements.length;i++){
            for (int j=1;j<elements.length;j++){
                if (elements[i]==elements[j]){
                    if (!dupIndex.contains(i+1))
                        dupIndex.add(i+1);
                }
            }
            duplicateValuesAndIndex.put(elements[i],dupIndex);
        }
        return duplicateValuesAndIndex;
    }

}