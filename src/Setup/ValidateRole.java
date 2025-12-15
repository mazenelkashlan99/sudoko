package Setup;

public class ValidateRole extends ValidateGame {

    Role role;

    ValidateRole(int[][] testMatrix, int threadNum, Role role) {
        super(testMatrix, threadNum);
        this.role = role;
    }

    boolean checkVariation(int [] elements){
        int n = elements.length;
        for(int i = 0; i < n - 1; i++) {

            for(int j = i + 1; j < n; j++) {

                if(elements[i] == elements[j]){
                    return false; 
                }
            }
        }
        return true;
    }


    
    final void validateElements(){
        switch (this.role) {
            case BOX:
                break;
            case ROW:
                break;
            case COLUMN:
                break;
            default:
                break;
        }
        int [] elements = new int[9];
        if (checkVariation(elements)) {
            
        }

    }

}