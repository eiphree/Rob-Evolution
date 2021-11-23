package simulation;

import java.util.ArrayList;

public class Instructions {
    private ArrayList<Character> program = new ArrayList<>();
    private int programLength;

    Instructions (String prog)  {
        int n = prog.length();
        programLength = n;
        for (int i = 0; i < n; i++){
            program.add(prog.charAt(i));
        }
    }

    public Instructions cloneProgram () {
        Instructions newProgram = new Instructions("");
        for (int i = 0; i < programLength; i++) {
            newProgram.program.add(this.program.get(i));
        }
        newProgram.programLength = this.programLength;
        return newProgram;
    }

    private boolean isInRegister (char c) {
        String s = Parameters.instrList();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (c == s.charAt(i))
                return true;
        }
        return false;
    }

    public int getProgLength () {
        return programLength;
         }


    public void controlRob (Rob robek) {
        for (int i = 0; i < programLength; i++){
            if (!robek.isAlive())
                break;

            char command = program.get(i);

            if (command == 'p')
                robek.turnRight();
            else if (command == 'l')
                robek.turnLeft();
            else if (command == 'i')
                robek.goForward();
            else if (command == 'w')
                robek.sniff();
            else if (command == 'j')
                robek.consume();

            robek.useEnergy(1);
        }
    }

    public void addInstr(char c) {
        program.add(c);
        programLength++;
    }

    public void deleteInstr() {
        if (programLength > 0) {
            program.remove(programLength - 1);
            programLength--;
        }
    }

    public void replaceInstr(int ind, char c) {
        program.set(ind, c);
    }

    public void printProg() {
        StringBuilder prog = new StringBuilder();
        for (int i = 0; i < programLength; i++){
            prog.append(program.get(i));
        }
        System.out.println(prog);
    }
}