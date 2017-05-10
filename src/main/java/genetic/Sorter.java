package genetic;

import java.util.Comparator;

public class Sorter implements Comparator<Solution>
{

    @Override
    public int compare(Solution o1, Solution o2)
    {
        if (o1.getFitness() < o2.getFitness())
        {
            return -1;
        }

        if (o1.getFitness() > o2.getFitness())
        {
            return 1;
        }

        return 0;
    }
}
