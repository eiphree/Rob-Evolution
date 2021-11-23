package simulation;

import java.util.ArrayList;

public class Board {
    private final int sizeX;
    private final int sizeY;
    private final Square[][] sq;
    private final int squaresWIthFoodNumber;

    public static class WrongBoardSize extends Exception {
        WrongBoardSize(String s) {
            super(s);
        }
    }
    public static class WrongChar extends Exception {
        WrongChar(String s) {
            super(s);
        }
    }

    Board(ArrayList<String> boardScheme) throws WrongBoardSize, WrongChar {
        int y = boardScheme.size();
        if (y == 0) {
            throw new WrongBoardSize("Given board is of incorrect size");
        }

        int x = boardScheme.get(0).length();
        if (x == 0) {
            throw new WrongBoardSize("Given board is of incorrect size");
        }

        sq = new Square[x][y];
        String row;
        char c;
        int squaresWithFood = 0;

        for (int i = 0; i < y; i++) {
            row = boardScheme.get(i);
            if (row.length() != x) {
                throw new WrongBoardSize("Given board is of incorrect size");
            }
            for (int j = 0; j < x; j++) {
                c = row.charAt(j);
                switch (c) {
                    case ' ': {sq[j][i] = new SquareWithNoFood(); break;}
                    case 'x': {sq[j][i] = new SquareWithFood(); squaresWithFood++; break;}
                    default: { throw new WrongChar("Board scheme contains incorrect character");}
                }
            }
        }
        sizeX = x;
        sizeY = y;
        squaresWIthFoodNumber = squaresWithFood;
    }

    public int squaresWIthFoodNumber() {return squaresWIthFoodNumber;}
    public int sizeX() {return sizeX;}
    public int sizeY() {return sizeY;}
    public Square getSquare(int x, int y) {return sq[(x + sizeX) % sizeX][(y + sizeY) % sizeY];}

    public int squaresWithFood() {
        int n = 0;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (sq[i][j].hasFood())
                    n++;
            }
        }
        return n;
    }

    public void printBoard() {
        for (int i = 0; i < sizeY; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < sizeX; j++) {
                String c;
                if (sq[j][i].hasFood()) c = "x"; else c = " ";
                s.append(c);
            }
            System.out.println(s);
        }
    }

}
