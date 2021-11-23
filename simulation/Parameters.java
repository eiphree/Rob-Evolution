package simulation;

import java.util.*;

public class Parameters {
    public static class WrongParameters extends Exception {
        WrongParameters (String s) {
            super (s);
        }
    }
    private static final int parametersNumber = 15;

    private static int howManyRounds;
    private static int howOftenPrint;
    private static int sizeX;
    private static int sizeY;
    private static int beginRobs;
    private static double beginEnergy;
    private static int howLongGrows;
    private static double energyFromFood;
    private static double roundCost;
    private static double duplicationProb;
    private static double parentEnergy;
    private static double duplicationLimit;
    private static double deleteProb;
    private static double addProb;
    private static double replaceProb;
    private static String beginProgram;
    private static String instrList;
    private static Board board;

    public static int howManyRounds() {return howManyRounds;}
    public static int howOftenPrint() {return howOftenPrint;}
    public static int sizeX() {return sizeX;}
    public static int sizeY() {return sizeY;}
    public static int beginRobs() {return beginRobs;}
    public static int howLongGrows() {return howLongGrows;}
    public static double energyFromFood() {return energyFromFood;}
    public static double roundCost() {return roundCost;}
    public static double duplicationProb() {return duplicationProb;}
    public static double parentEnergy() {return parentEnergy;}
    public static double duplicationLimit() {return duplicationLimit;}
    public static double deleteProb() {return deleteProb;}
    public static double addProb() {return addProb;}
    public static double replaceProb() {return replaceProb;}
    public static Board board() {return board;}
    public static String instrList() {return instrList;}
    public static String beginProgram() {return beginProgram;}
    public static double beginEnergy() {return beginEnergy;}


    private static int getOneParameter(String s) throws WrongParameters {
        String[] split = s.split(" ");
        if (split.length != 2) {
            throw new WrongParameters("Incorrect format of parameters");
        }
        int paramNumber = -1;
        String param = split[0];
        String value = split[1];

        switch (param) {
            case "how_often_print" : {
                paramNumber = 0;
                howOftenPrint = Integer.parseInt(value);
                break;
            }
            case "energy_from_food" : {
                paramNumber = 1;
                energyFromFood = Double.parseDouble(value);
                break;
            }
            case "how_long_grows" : {
                paramNumber = 2;
                howLongGrows = Integer.parseInt(value);
                break;
            }
            case "how_many_rounds" : {
                paramNumber = 3;
                howManyRounds = Integer.parseInt(value);
                break;
            }
            case "round_cost" :{
                paramNumber = 4;
                roundCost = Double.parseDouble(value);
                break;
            }
            case "duplication_limit" : {
                paramNumber = 5;
                duplicationLimit = Double.parseDouble(value);
                break;
            }
            case "begin_energy" : {
                paramNumber = 6;
                beginEnergy = Double.parseDouble(value);
                break;
            }
            case "begin_how_many_robs" : {
                paramNumber = 7;
                beginRobs = Integer.parseInt(value);
                break;
            }
            case "begin_progr" : {
                paramNumber = 8;
                beginProgram = value;
                break;
            }
            case "add_instr_prob" : {
                paramNumber = 9;
                addProb = Double.parseDouble(value);
                break;
            }
            case "duplication_prob" : {
                paramNumber = 10;
                duplicationProb = Double.parseDouble(value);
                break;
            }
            case "delete_instr_prob" : {
                paramNumber = 11;
                deleteProb = Double.parseDouble(value);
                break;
            }
            case "replace_instr_prob" : {
                paramNumber = 12;
                replaceProb = Double.parseDouble(value);
                break;
            }
            case "instr_register" : {
                paramNumber = 13;
                instrList = value;
                break;
            }
            case "parent_energy_fraction" : {
                paramNumber = 14;
                parentEnergy = Double.parseDouble(value);
                break;
            }
        }
        return paramNumber;
    }

    public static void getParameters(ArrayList<String> parList) throws WrongParameters {
        if (parList.size() != parametersNumber) {
            throw new WrongParameters("Wrong number of parameters");
        }
        boolean[] isLoaded = new boolean[parametersNumber];
        for (int i = 0; i < parametersNumber; i++) isLoaded[i] = false;

        int j;
        for (int i = 0; i < parametersNumber; i++) {
            j = getOneParameter(parList.get(i));
            if (j < 0) {
                throw new WrongParameters("Incorrect parameter value");
            }
            isLoaded[j] = true;
        }

        for (int i = 0; i < parametersNumber; i++) {
            if (!isLoaded[i]) {
                throw new WrongParameters("Necessary parameter not provided");
            }
        }
        if (!isInstrInRegister()) {
            throw new WrongParameters("Given instruction is not in the register");
        }
    }

    public static void loadBoard (Board b) {
        board = b;
        sizeX = board.sizeX();
        sizeY = board.sizeY();
    }

    public static boolean draw (double prob) {
        Random r = new Random();
        int p = r.nextInt(1000);
        return (p < 1000 * prob);
    }

    public static boolean isInstrInRegister() {
        for (int i = 0; i < beginProgram.length(); i++) {
            char c = beginProgram.charAt(i);
            boolean isInRegister = false;
            for (int j = 0; j < instrList.length(); j++) {
                if (instrList.charAt(j) == c) {
                    isInRegister = true;
                    break;
                }
            }
            if (!isInRegister) {
                return false;
            }
        }
        return true;
    }
}