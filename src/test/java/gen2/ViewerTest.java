package gen2;

import static java.lang.Math.PI;
import static java.lang.Math.random;
import static java.lang.System.exit;
import static java.lang.System.out;

public class ViewerTest
{

    public static void main(String[] args)
    {
        PolygonFactory factory = new PolygonFactory(0, 0, 640, 480);
        Polygon[] polygons = factory.getRandom(3, 100);
        Viewer viewer = new Viewer(640, 480);
        viewer.open(polygons);

        for(int i = 0; i < 5; i++)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
                exit(0);
            }

            for(int p = 0; p < polygons.length; p++)
            {
                polygons[p] = factory.getRecolored(polygons[p], 128, 128, 128);
            }

            viewer.show(polygons);
        }

        out.println(factory.getSVG(polygons));

        viewer.close();
        exit(0);
    }
}
