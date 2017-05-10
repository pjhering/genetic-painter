package gen2;

import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

public class TestFitness
{

    public static void main(String[] args) throws IOException
    {
        URL url = TestFitness.class.getResource("/test.png");
        BufferedImage original = ImageIO.read(url);
        int width = original.getWidth();
        int height = original.getHeight();
        PolygonFactory factory = new PolygonFactory(0, 0, width, height);
        List<Individual> population = new ArrayList<>();
        Fitness fitness = new Fitness(original);

        for (int i = 0; i < 100; i++)
        {
            Individual individual = new Individual(factory.getRandom(3, 250));
            population.add(individual);
            long score = fitness.evaluate(individual);
            individual.setFitness(score);
            individual.setAge(1);
        }

        Sorter sorter = new Sorter();
        Collections.sort(population, sorter);

        population.forEach(i -> out.println(i));

        Viewer viewer = new Viewer(width, height);
        viewer.open(population.get(0).getPolygons());
    }
}
