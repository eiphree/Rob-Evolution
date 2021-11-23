package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BoardReader {
    BoardReader() {
    }

    public Board loadBoard(String fileName) throws FileNotFoundException, Board.WrongBoardSize, Board.WrongChar {
        File file = new File(fileName);
        Scanner read = new Scanner(file);
        String line;

        ArrayList<String> boardScheme = new ArrayList <>();
        while (read.hasNextLine()) {
            line = read.nextLine();
            boardScheme.add(line);
        }
        return new Board(boardScheme);
    }

    public ArrayList<String> readParameters(String fileName) throws FileNotFoundException, Parameters.WrongParameters {
        File file = new File(fileName);
        Scanner read = new Scanner(file);
        String line;
        ArrayList<String> data = new ArrayList<>();
        int i = 0;

        while (read.hasNextLine()) {
            line = read.nextLine();
            data.add(line);
            i++;
            if (i > 15) {
                throw new Parameters.WrongParameters("Too many parameters");
            }
        }
        Collections.sort(data);
        return data;
    }
}