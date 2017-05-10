package gen2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CreateTestImage
{

    public static void main(String[] args) throws IOException
    {
        BufferedImage img = new BufferedImage(320, 320, TYPE_INT_ARGB);
        Color[] colors = new Color[]
        {
            new Color(0, 0, 0), new Color(0, 0, 64),
            new Color(0, 0, 128), new Color(0, 0, 255),
            new Color(0, 64, 0), new Color(0, 64, 64),
            new Color(0, 64, 128), new Color(0, 64, 255),
            new Color(0, 128, 0), new Color(0, 128, 64),
            new Color(0, 128, 128), new Color(0, 128, 255),
            new Color(0, 255, 0), new Color(0, 255, 64),
            new Color(0, 255, 128), new Color(0, 255, 255),
            new Color(64, 0, 0), new Color(64, 0, 64),
            new Color(64, 0, 128), new Color(64, 0, 255),
            new Color(64, 64, 0), new Color(64, 64, 64),
            new Color(64, 64, 128), new Color(64, 64, 255),
            new Color(64, 128, 0), new Color(64, 128, 64),
            new Color(64, 128, 128), new Color(64, 128, 255),
            new Color(64, 255, 0), new Color(64, 255, 64),
            new Color(64, 255, 128), new Color(64, 255, 255),
            new Color(128, 0, 0), new Color(128, 0, 64),
            new Color(128, 0, 128), new Color(128, 0, 255),
            new Color(128, 64, 0), new Color(128, 64, 64),
            new Color(128, 64, 128), new Color(128, 64, 255),
            new Color(128, 128, 0), new Color(128, 128, 64),
            new Color(128, 128, 128), new Color(128, 128, 255),
            new Color(128, 255, 0), new Color(128, 255, 64),
            new Color(128, 255, 128), new Color(128, 255, 255),
            new Color(255, 0, 0), new Color(255, 0, 64),
            new Color(255, 0, 128), new Color(255, 0, 255),
            new Color(255, 64, 0), new Color(255, 64, 64),
            new Color(255, 64, 128), new Color(255, 64, 255),
            new Color(255, 128, 0), new Color(255, 128, 64),
            new Color(255, 128, 128), new Color(255, 128, 255),
            new Color(255, 255, 0), new Color(255, 255, 64),
            new Color(255, 255, 128), new Color(255, 255, 255),
        };

        Graphics g = img.getGraphics();
        int i = 0;

        for (int y = 0; y < 320; y += 20)
        {
            for (int x = 0; x < 320; x += 20)
            {
                g.setColor(colors[i]);
                g.fillRect(x, y, 20, 20);
                i = (i + 1) % colors.length;
            }
        }

        ImageIO.write(img, "png", new File("test.png"));
    }
}
