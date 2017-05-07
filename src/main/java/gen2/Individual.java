package gen2;

import static java.util.Objects.requireNonNull;

public class Individual
{
    private final Polygon[] polygons;
    private int age;
    private long fitness;
    
    public Individual (Polygon[] polygons)
    {
        this.polygons = requireNonNull(polygons);
    }
    
    public Polygon[] getPolygons()
    {
        return polygons;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public long getFitness()
    {
        return fitness;
    }

    public void setFitness(long fitness)
    {
        this.fitness = fitness;
    }
    
    @Override
    public String toString()
    {
        return "Individual@" +
                hashCode() +
                "{age=" + age +
                ", fitness=" + fitness +
                "}";
    }
}
