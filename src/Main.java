import Setup.GameCreation;
import Setup.ValidateGame;

void main() {
    
    GameCreation gameCreation=new GameCreation("invalid.csv");
    var game=gameCreation.getBoard();

    ValidateGame vg=new ValidateGame(27,game);
    vg.threadNumMatching();
}