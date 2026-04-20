package MVC.Solver;


public class PermutationSolverIterator {
    
    private int[] current;
    private int count;
    private static final int TOTAL_PERMUTATIONS = 59049;
    
    public PermutationSolverIterator(){
        count=0;
        current=new int[5];
        for (int i=0;i<current.length;i++){
            current[i]=1;
        }
    }

    public boolean hasNext(){
        return count < TOTAL_PERMUTATIONS;
    }

    private int[] getNextCombination(int[] current){

        for (int i=4;i>=0;i--){
            if (current[i]<9){
                current[i]+=1;
                break;
            }
            else{
                current[i]=1;
            }
        }
        return current;
    }

    public int[] next(){
        if (hasNext()){
            if (count==0){
                int[] temp={1,1,1,1,1};
                count+=1;
                return temp;
            }
            int [] currentReplicate=current.clone();
            current=getNextCombination(currentReplicate);
            count+=1;
            return currentReplicate;
        }
        return null;
    }
}
