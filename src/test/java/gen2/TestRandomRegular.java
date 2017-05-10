package gen2;

import static java.lang.System.exit;

public class TestRandomRegular
{

    public static void main(String[] args)
    {
        PolygonFactory factory = new PolygonFactory(0, 0, 640, 480);
        Polygon[] array = factory.getRandomRegular(3, 50, 400);
        Viewer viewer = new Viewer(640, 480);
        viewer.open(array);

        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            exit(0);
        }
    }
}
