package simulation;

public class SquareWithNoFood extends Square {
    SquareWithNoFood () {
    }

    @Override
    public boolean hasFood() {
        return false;
    }

    @Override
    public void feedRob() {}

    @Override
    public void regenerate() {
    }

}
