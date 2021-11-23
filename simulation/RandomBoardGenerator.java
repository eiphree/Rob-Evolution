package simulation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class RandomBoardGenerator {
    public static void main(String[] args) throws FileNotFoundException {
        String name = "example_board.txt";
        int limitX = 10000;
        int limitY = 10000;

        Random r = new Random();
        int x = r.nextInt(limitX);
        if (x == 0) x += limitX;
        int y = r.nextInt(limitY);
        if (y == 0) y += limitY;
       int pr = r.nextInt(x * y);

        PrintWriter writer = new PrintWriter(name);


        for (int j = 0; j < y; j++) {
            StringBuilder row = new StringBuilder();
            for (int i = 0; i < x; i++) {
                int p = r.nextInt(x * y);
                char c = (p <= pr ? 'x' : ' ');
                row.append(c);
            }
            writer.println(row);
        }
        writer.close();
    }
}