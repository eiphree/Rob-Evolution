package simulation;

public class SquareWithFood extends Square {
    private boolean canBeEaten;
    private int regenerationRound;

    SquareWithFood() {
        canBeEaten = true;
        regenerationRound = Parameters.howLongGrows();
    }

    @Override
    public void feedRob() {
        canBeEaten = false;
        regenerationRound = 0;
    }

    @Override
    public void regenerate() {
        regenerationRound ++;
        if (regenerationRound == Parameters.howLongGrows()){
            canBeEaten = true;
        }
    }

    @Override
    public boolean hasFood() {return canBeEaten;}

}
