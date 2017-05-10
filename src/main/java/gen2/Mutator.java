package gen2;

import java.awt.Color;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.random;
import static java.lang.Math.round;

public class Mutator
{

    public Mutator()
    {
    }

    /*
     mutation - the rate of mutation ranges from 0% for the most fit to 2% for
     the least fit.
     */
    public void mutate(Individual[] population)
    {
        for (int i = 0; i < population.length; i++)
        {
            double rate = 0.2 * (i / (population.length - 1));

            if (random() < rate)
            {
                mutate(population[i]);
            }
        }
    }

    public void mutate(Individual i)
    {
        Polygon[] ps = i.getPolygons();
        int n = (int) floor(random() * ps.length);
        Polygon p = ps[n];

        int dx = (int) round(random() * 100 - 50);
        int dy = (int) round(random() * 100 - 50);

        int[] xs = p.getXs();
        int[] ys = p.getYs();

        for (int d = 0; d < xs.length; d++)
        {
            xs[d] += dx;
            ys[d] += dy;
        }

        int r = p.getRed() + (int) round(random() * 64 - 32);
        int g = p.getGreen() + (int) round(random() * 64 - 32);
        int b = p.getBlue() + (int) round(random() * 64 - 32);
        int a = p.getAlpha() + (int) round(random() * 64 - 32);

        Color color = new Color(
                max(0, min(255, r)),
                max(0, min(255, g)),
                max(0, min(255, b)),
                max(0, min(255, a)));

        ps[n] = new Polygon(xs, ys, color);
    }
}
