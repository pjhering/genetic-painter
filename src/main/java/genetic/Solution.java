package genetic;

import java.awt.Color;
import java.awt.Shape;
import static java.util.Objects.requireNonNull;

public class Solution
{

    public final Shape[] SHAPES;
    public final Color[] COLORS;
    private long fitness;
    private boolean hasBeenEvaluated;

    public Solution(Shape[] shapes, Color[] colors)
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

    boolean hasBeenEvaluated()
    {
        return hasBeenEvaluated;
    }
}
