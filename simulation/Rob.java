package simulation;

import java.util.Random;

public class Rob {
    private boolean isAlive;
    private int coordX;
    private int coordY;
    private int direction; // 0->N 1->E 2->S 3-> W
    private double energy;
    private Instructions program;
    private int age;

    Rob(double energy, String programList, int x, int y) {
        this.isAlive = true;
        this.energy = energy;
        this.coordX = x;
        this.coordY = y;
        this.program = new Instructions (programList);
        this.age = 0;
        Random r = new Random();
        this.direction = r.nextInt(4);
    }

    Rob(double energy, Instructions programList, int x, int y) {
        this.isAlive = true;
        this.energy = energy;
        this.coordX = x;
        this.coordY = y;
        this.program = programList.cloneProgram();
        this.age = 0;
        Random r = new Random();
        this.direction = r.nextInt(4);
    }

    public boolean isAlive() {return isAlive;}
    public int coordX() {return coordX;}
    public int coordY() {return coordY;}
    public double energy() {
        return energy;
    }
    public Instructions program() {
        return program;
    }
    public int age() {return age;}


    public void goToSquare(Square p){
        if (p.hasFood()) {
            energy += Parameters.energyFromFood();
            p.feedRob();
        }
    }

    public void goForward() {
        if (direction == 0)
            coordY = (coordY + 1) % Parameters.sizeY();
        else if (direction == 2)
            coordY = (coordY - 1 + Parameters.sizeY()) % Parameters.sizeY();
        else if (direction == 1)
            coordX = (coordX + 1) % Parameters.sizeX();
        else
            coordX = (coordX - 1 + Parameters.sizeX()) % Parameters.sizeX();
        Square p = Parameters.board().getSquare(coordX, coordY);
        goToSquare(p);
    }

    public void turnRight() {
        direction = (direction + 1) % 4;
    }

    public void turnLeft() {
        direction = (direction + 3) % 4;
    }

    private boolean sniffSquare (int x, int y) {
        return Parameters.board().getSquare(x, y).hasFood();
    }

    public void sniff() {
        if (sniffSquare(coordX + 1, coordY))
            direction = 0;
        else if (sniffSquare(coordX, coordY + 1))
            direction = 1;
        else if (sniffSquare(coordX - 1, coordY))
            direction = 2;
        else if (sniffSquare(coordX, coordY - 1))
            direction = 3;
    }

    public void consume() {
        for (int i = coordX - 1; i <= coordX + 1; i++){
            for (int j = coordX - 1; j <= coordX + 1; j++){
                if (i == coordX && j == coordY)
                    continue;
                if (sniffSquare(i, j)){
                    coordX = i;
                    coordY = j;
                    Square p = Parameters.board().getSquare(coordX, coordY);
                    goToSquare(p);
                    break;
                }
            }
        }
    }

    public void useEnergy(double ile) {
        energy -= ile;
        if (energy < 0)
            die();
    }

    public void die() {
        isAlive = false;
    }

    public void performProg() {
        program.controlRob(this);
    }

    public void surviveRound() {
        useEnergy(Parameters.roundCost());
        age ++;
    }

    public Rob duplicate(){
        Duplication cloningMachine = new Duplication();
        Rob robekJunior = null;
        if (energy >= Parameters.duplicationLimit() && Parameters.draw(Parameters.duplicationProb())) {
            robekJunior = cloningMachine.clone(this);
        }
        return robekJunior;
    }

    public void loadNewProgram (Instructions pr) {
        program = pr;
    }

}