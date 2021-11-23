package simulation;

import java.util.Random;

public class Mutation {
    Mutation() {}

    public void mutate(Instructions pr) {
        if (Parameters.draw(Parameters.deleteProb())) {
            pr.deleteInstr();
        }
        if (Parameters.draw(Parameters.addProb())) {
            String register = Parameters.instrList();
            Random r = new Random();
            int n = r.nextInt(register.length());
            char c = register.charAt(n);
            pr.addInstr(c);
        }
        if (Parameters.draw(Parameters.replaceProb())) {
            if (pr.getProgLength() > 0) {
                String register = Parameters.instrList();
                Random r = new Random();
                int n = r.nextInt(register.length());
                char c = register.charAt(n);
                n = r.nextInt(pr.getProgLength());
                pr.replaceInstr(n, c);
            }
        }
    }
}