package gen2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URL;
import javax.imageio.ImageIO;

public class evolve
{

    private static File save;

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
}
