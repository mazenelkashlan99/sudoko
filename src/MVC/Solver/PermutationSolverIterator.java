package MVC.Solver;

import java.util.ArrayList;
import java.util.Random;
public class PermutationSolverIterator {
    
    public PermutationSolverIterator(){}

    public ArrayList<Integer> generateNumbers(){
        Random rand = new Random();
        ArrayList<Integer> numbersGenerated=new ArrayList<Integer>();
        for (int i=0;i<5;i++){
          int randomNum = rand.nextInt(9 - 1 + 1) + 1;
          numbersGenerated.add(randomNum);
        }
        return numbersGenerated;
    }
}
