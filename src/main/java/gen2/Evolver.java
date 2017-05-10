package gen2;

import static java.util.Objects.requireNonNull;
import java.util.Observable;

public class Evolver extends Observable
{

    private final int COUNT;
    private final Fitness FITNESS;
    private final Breeder BREEDER;
    private final Mutator MUTATOR;
    private final Sorter SORTER;
    private Individual[] currentPopulation;

    public Evolver(int count, Fitness fitness, Breeder breeder, Mutator mutator)
    {
        this.COUNT = count;
        this.FITNESS = requireNonNull(fitness);
        this.BREEDER = requireNonNull(breeder);
        this.MUTATOR = requireNonNull(mutator);
        this.SORTER = new Sorter();
    }

    /*
     for each generation
     evaluate individual fitness
     sort population from most to least fit
     breed and mutate new population
     notify observers
     */
    public void evolve(Individual[] population)
    {
        for(int gen = 0; gen < COUNT; gen++)
        {
            currentPopulation = population;
            setChanged();
            notifyObservers();
            
            for(Individual ind : population)
            {
                long score = FITNESS.evaluate(ind);
                ind.setFitness(score);
                ind.setAge(ind.getAge() + 1);
            }
            
            SORTER.sort(population);
            population = BREEDER.nextGeneration(population);
            SORTER.sort(population);
            MUTATOR.mutate(population);
        }
    }
    
    public Individual[] getCurrentPopulation()
    {
        return currentPopulation;
    }
}
