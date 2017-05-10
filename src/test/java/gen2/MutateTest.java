package gen2;

import static java.lang.System.exit;

public class MutateTest
{

    public static void main(String[] args)
    {
        PolygonFactory factory = new PolygonFactory(0, 0, 640, 480);
        Polygon[] polygons = factory.getRandomRegular(4, 25, 400);
        Individual ind = new Individual(polygons);
        Mutator m = new Mutator();

        Viewer viewer = new Viewer(640, 480);
        viewer.open(ind.getPolygons());

        for (int i = 0; i < 60; i++)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
                break;
            }

            m.mutate(ind);
            viewer.show(ind.getPolygons());
        }

        exit(0);
    }
}
