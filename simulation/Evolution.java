package simulation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Evolution {
    private int roundNr;
    private int aliveRobs;
    private int progMin;
    private int progMax;
    private int progSum;
    private double energyMin;
    private double energyMax;
    private static double energySum;
    private int ageMin;
    private int ageMax;
    private int ageSum;
    private ArrayList<Rob> robs = new ArrayList<>();
    Board board;

    Evolution(Board p) {
        this.roundNr = 1;
        int aliveRobs = Parameters.beginRobs();
        this.aliveRobs = aliveRobs;
        Instructions beginProgram = new Instructions(Parameters.beginProgram());
        this.progMin = beginProgram.getProgLength();
        this.progMax = beginProgram.getProgLength();
        this.progSum = beginProgram.getProgLength() * aliveRobs;
        double beginEnergy = Parameters.beginEnergy();
        this.energyMin = beginEnergy;
        this.energyMax = beginEnergy;
        energySum = beginEnergy * aliveRobs;
        this.ageMin = 0;
        this.ageMax = 0;
        this.ageSum = 0;
        this.board = p;

        for (int i = 0; i < aliveRobs; i++){
            Random r = new Random();
            int x = r.nextInt(p.sizeX());
            int y = r.nextInt(p.sizeY());
            Rob robek = new Rob(beginEnergy, Parameters.beginProgram(), x, y);
            robs.add(robek);
        }
    }

    private void printRound() {
        DecimalFormat df = new java.text.DecimalFormat("0.00");
        double progAvg = (aliveRobs > 0 ?  ((double)progSum / aliveRobs) : 0 );
        double energAvg = (aliveRobs > 0 ?  (energySum / aliveRobs) : 0 );
        double ageAvg = (aliveRobs > 0 ?  ((double)ageSum / aliveRobs) : 0 );
        System.out.println("Round number: " + roundNr + ", robs: " + aliveRobs + ", squares with food: "
                + board.squaresWithFood() + ", programs lengths: " + progMin + "/" + df.format(progAvg) +
                "/" + progMax + ", energy: " + df.format(energyMin) + "/" + df.format(energAvg) +
                "/" + df.format(energyMax) + ", age: " + ageMin + "/" + df.format(ageAvg) + "/" + ageMax);
    }

    private void printDetailed() {
        DecimalFormat df=new java.text.DecimalFormat("0.00");
        System.out.println("Round " + roundNr + ". Number of alive robs if" + aliveRobs);
        for (int i = 0; i < robs.size(); i++){
            Rob robek = robs.get(i);
            if (robek.isAlive()) {
                System.out.println("state of rob number " + i + ": Square (" + robek.coordX() + ", " +
                        robek.coordY() + "); energy " + df.format(robek.energy()) +
                        "; program length " + robek.program().getProgLength() + "; age " + robek.age());
            }
        }
        System.out.println("Number of squares with accessible food is " + board.squaresWithFood());
    }

    private void calculateRoundState() {
        int minProg = Integer.MAX_VALUE;
        int maxProg = 0;
        int sumProg = 0;
        int minAge = Integer.MAX_VALUE;
        int maxAge = 0;
        int sumAge = 0;
        double minEnerg = Double.MAX_VALUE;
        double maxEnerg = 0;
        double sumEnerg = 0;
        for (int i = 0; i < robs.size(); i++) {
            Rob robek = robs.get(i);
            if (robek.isAlive()) {
                int progLength = robek.program().getProgLength();
                int age = robek.age();
                double energ = robek.energy();
                minProg = Math.min(minProg, progLength);
                maxProg = Math.max(maxProg, progLength);
                sumProg +=progLength;
                minAge = Math.min(minAge, age);
                maxAge = Math.max(maxAge, age);
                sumAge +=age;
                minEnerg = Math.min(minEnerg, energ);
                maxEnerg = Math.max(maxEnerg, energ);
                sumEnerg += energ;
            }
        }
        progMin = (aliveRobs > 0 ? minProg : 0);
        progMax = maxProg;
        progSum = sumProg;
        ageMin = (aliveRobs > 0 ? minAge : 0);
        ageMax = maxAge;
        ageSum = sumAge;
        energyMin = (aliveRobs > 0 ? minEnerg : 0);
        energyMax = maxEnerg;
        energySum = sumEnerg;
    }


    public void registerDeadRob() {
        aliveRobs --;
    }

    private void simulateRound () {
        for (int i = 0; i < robs.size(); i++) {
            Rob robek = robs.get(i);
            if (robek.isAlive()) {
                robek.performProg();
                if (!robek.isAlive()) {
                    registerDeadRob();
                }
            }
        }
        int n = robs.size();
        for (int i = 0; i < n; i++) {
            Rob robek = robs.get(i);
            if (robek.isAlive()) {
                Rob robekJunior = robek.duplicate();
                if (robekJunior != null) {
                    robs.add(robekJunior);
                    aliveRobs++;
                }
                robek.surviveRound();
                if (!robek.isAlive()) {
                    registerDeadRob();
                }
            }
        }
        for (int i = 0; i < Parameters.sizeX(); i++) {
            for (int j = 0; j < Parameters.sizeY(); j++) {
                Parameters.board().getSquare(i, j).regenerate();
            }
        }
    }

    public void performEvolution() {
        printDetailed();
        for (int i = 0; i < Parameters.howManyRounds(); i++){
            simulateRound();
            calculateRoundState();
            printRound();
            if (roundNr % Parameters.howOftenPrint() == 0) {
                printDetailed();
            }
            roundNr++;
        }
        if (Parameters.howManyRounds() % Parameters.howOftenPrint() != 0) {
            printDetailed();
        }
    }
}