package simulation;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulation {

    public static void main(String[] args) throws FileNotFoundException, Board.WrongChar,
            Board.WrongBoardSize, Parameters.WrongParameters {

        String boardFile = args[0];
        String parametersFile = args[1];

        System.out.println("Preparing simulation");
        System.out.println("Creating a board");
        BoardReader reader = new BoardReader();
        Board board = reader.loadBoard(boardFile);
        Parameters.loadBoard(board);

        System.out.println("Reading parameters");
        ArrayList<String> ParametersRegister = reader.readParameters(parametersFile);
        Parameters.getParameters(ParametersRegister);

        System.out.println("Starting simulation");
        Evolution e = new Evolution(board);
        e.performEvolution();
    }
}
