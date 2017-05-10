package gen2;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter implements Comparator<Individual>
{

    @Override
    public int compare(Individual o1, Individual o2)
    {
        if (o1.getFitness() < o2.getFitness())
        {
            return -1;
        }

        if (o1.getFitness() > o2.getFitness())
        {
            return 1;
        }

        if (o1.getAge() < o2.getAge())
        {
            return 1;
        }

        if (o1.getAge() > o2.getAge())
        {
            return -1;
        }

        return 0;
    }

    void sort(List<Individual> list)
    {
        Collections.sort(list, this);
    }
}
