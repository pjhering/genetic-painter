package gen2;

import static java.lang.Math.random;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

public class TestSorter
{

    public static void main(String[] args)
    {
        List<Individual> list = createList(10);
        out.println(list.toString().replace("}, ", "},\n "));
        out.println();

        Sorter s = new Sorter();
        s.sort(list);
        out.println(list.toString().replace("}, ", "},\n "));
    }

    private static List<Individual> createList(int size)
    {
        ArrayList<Individual> list = new ArrayList<>();
        Polygon[] p = new Polygon[0];

        for (int i = 0; i < size; i++)
        {
            Individual id = new Individual(p);
            id.setAge(10 + i);
            id.setFitness((long) (random() * 1000L));
            list.add(id);
        }

        return list;
    }
}
