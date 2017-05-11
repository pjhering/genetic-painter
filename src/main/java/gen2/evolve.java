package gen2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;

public class evolve implements Observer
{

    private static File save;
    
    private final BufferedImage image;
    private final PolygonFactory factory;
    private final Viewer viewer;
    
    private evolve(BufferedImage image)
    {
        this.image = requireNonNull(image);
        factory = new PolygonFactory(0, 0, image.getWidth(), image.getHeight());
        this.viewer = new Viewer(image.getWidth(), image.getHeight());
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            log("expected one argument: the name of an image file");
            return;
        }

        URL url = evolve.class.getResource(args[0]);

        if (url == null)
        {
            log("unable to find " + args[0]);
        }

        try
        {
            BufferedImage img = ImageIO.read(url);
            evolve e = new evolve(img);
            e.start();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void log(Object o)
    {
        out.println(o);
    }
    
    private void start()
    {
        Individual[] pop = new Individual[100];
        
        for(int i = 0; i < 100; i++)
        {
            Polygon[] ps = factory.getRandomRegular(4, 25, 500);
            pop[i] = new Individual(ps);
        }
        
        viewer.open();
        
        Evolver ev = new Evolver(3, new Fitness(image), new Breeder(), new Mutator());
        ev.addObserver(this);
        ev.evolve(pop);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        Evolver e = (Evolver) o;
        Individual i = e.getCurrentPopulation()[0];
        viewer.show(i.getPolygons());
    }
}
