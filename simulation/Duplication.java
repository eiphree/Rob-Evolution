package simulation;

public class Duplication {
    Duplication (){}

    public Rob clone(Rob robek)  {
        double transferredEnergy = Parameters.parentEnergy() * robek.energy();
        Rob robekJunior = new Rob(transferredEnergy, robek.program(), robek.coordX(), robek.coordY());
        robek.useEnergy(transferredEnergy);

        Mutation ProgramChanger = new Mutation();
        ProgramChanger.mutate(robekJunior.program());
        return robekJunior;
    }
}