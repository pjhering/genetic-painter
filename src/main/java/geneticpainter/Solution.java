package geneticpainter;

import java.awt.Color;
import java.awt.Polygon;
import static java.util.Objects.requireNonNull;

public class Solution implements Comparable<Solution>
{
    public final Polygon[] SHAPES;
    public final Color[] COLORS;
    private long fitness;
    private boolean hasBeenEvaluated;
    
    public Solution(Polygon[] shapes, Color[] colors)
    {
        this.SHAPES = requireNonNull(shapes);
        this.COLORS = requireNonNull(colors);
    }

    public long getFitness()
    {
        return fitness;
    }

    public void setFitness(long fitness)
    {
        this.fitness = fitness;
        hasBeenEvaluated = true;
    }

    @Override
    public int compareTo(Solution o)
    {
        if(fitness < o.fitness)
        {
            return 1;
        }
        
        if(fitness > o.fitness)
        {
            return -1;
        }
        
        return 0;
    }

    boolean hasBeenEvaluated()
    {
        return hasBeenEvaluated;
    }
}
