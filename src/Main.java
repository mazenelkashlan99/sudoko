
import Setup.GameCreation;
import Setup.ValidateGame;

void main() {
    
    GameCreation gameCreation=new GameCreation("invalid.csv");
    var game=gameCreation.getBoard();


    ValidateGame vg=new ValidateGame(3,game);
    vg.threadNumMatching();

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}