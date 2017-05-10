package gen2;

import static java.lang.Math.random;
import static java.lang.Math.round;
import static java.lang.System.arraycopy;

public class Breeder
{

    public Breeder()
    {
    }

    /*
     elitism - the best individual is always moved into the next generation
     selection - the best 25% of individuals are bred. offspring are assigned a
     fitness equal to the sum of the parents divied by two.
     age - new individuals have age zero, otherwise age is incremented by one.
     */
    public Individual[] nextGeneration(Individual[] current)
    {
        Individual[] next = new Individual[current.length];
        next[0] = current[0];
        next[0].setAge(next[0].getAge() + 1);

        int i = 1;
        double limit = (int) (next.length / 4.0);

        LOOP:
        while (i < next.length)
        {
            int a = (int) round(random() * limit);
            int b = (int) round(random() * limit);

            if (a == b)
            {
                continue LOOP;
            }

            next[i] = breed(current[a], current[b]);
            i += 1;
        }

        return next;
    }

    private Individual breed(Individual mom, Individual dad)
    {
        long fitness = (mom.getFitness() + dad.getFitness()) / 2;

        Polygon[] moms = mom.getPolygons();
        Polygon[] dads = dad.getPolygons();

        int p = (int) round(random() * moms.length);
        Polygon[] kids = new Polygon[moms.length];
        arraycopy(moms, 0, kids, 0, p);
        arraycopy(dads, p, kids, p, dads.length - p);

        return new Individual(kids);
    }
}
