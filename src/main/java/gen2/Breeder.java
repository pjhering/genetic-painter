package gen2;

import static java.util.Objects.requireNonNull;

public class Breeder
{
    
    private final Mutator MUTATOR;
    
    public Breeder(Mutator mutator)
    {
        this.MUTATOR = requireNonNull(mutator);
    }
    
    /*
    elitism - the best individual is always moved into the next generation
    selection - the best 25% of individuals are bred. offspring are assigned a
                fitness equal to the sum of the parents divied by two.
    age - new individuals have age zero, otherwise age is incremented by one.
    */
    public Individual[] nextGeneration(Individual[] current)
    {
        return current;
    }
}
