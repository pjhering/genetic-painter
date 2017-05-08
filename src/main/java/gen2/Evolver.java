package gen2;

import static java.util.Objects.requireNonNull;

public class Evolver
{
    
    private final Fitness FITNESS;
    private final Sorter SORTER;
    private final Breeder BREEDER;
    
    public Evolver (Fitness fitness, Sorter sorter, Breeder breeder)
    {
        this.FITNESS = requireNonNull(fitness);
        this.SORTER = requireNonNull(sorter);
        this.BREEDER = requireNonNull(breeder);
    }
    
    /*
    for each generation
        evaluate individual fitness
        sort population from most to least fit
        create next generation
    */
    public void evolve (Individual[] population)
    {
    }
}
