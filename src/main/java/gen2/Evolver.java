package gen2;

import static java.util.Objects.requireNonNull;

public class Evolver
{

    private final Fitness FITNESS;
    private final Breeder BREEDER;
    private final Mutator MUTATOR;
    private final Sorter SORTER;

    public Evolver(Fitness fitness, Breeder breeder, Mutator mutator)
    {
        this.FITNESS = requireNonNull(fitness);
        this.BREEDER = requireNonNull(breeder);
        this.MUTATOR = requireNonNull(mutator);
        this.SORTER = new Sorter();
    }

    /*
     for each generation
     evaluate individual fitness
     sort population from most to least fit
     create next generation
     */
    public void evolve(Individual[] population)
    {
    }
}
