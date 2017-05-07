package gen2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.Math.abs;
import static java.util.Objects.requireNonNull;

public class Fitness
{

    private final int WIDTH;
    private final int HEIGHT;
    private final BufferedImage TARGET;
    private final int[][][] ARGB;
    private final BufferedImage CANVAS;

    public Fitness(BufferedImage target)
    {
        this.TARGET = requireNonNull(target);
        this.WIDTH = TARGET.getWidth();
        this.HEIGHT = TARGET.getHeight();
        this.CANVAS = new BufferedImage(WIDTH, HEIGHT, TYPE_INT_ARGB);
        this.ARGB = new int[WIDTH][HEIGHT][];

        for (int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                ARGB[x][y] = argb(TARGET.getRGB(x, y));
            }
        }
    }

    private int[] argb(int i)
    {
        return new int[]
        {
            (i >> 24) & 0xFF,
            (i >> 16) & 0xFF,
            (i >> 8) & 0xFF,
            i & 0xFF
        };
    }

    public long evaluate(Polygon[] polygons)
    {
        Graphics g = CANVAS.getGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        for(Polygon p : polygons)
        {
            p.draw(g);
        }
        
        long score = 0L;
        
        for(int x = 0; x < WIDTH; x += 2)
        {
            for(int y = 0; y < HEIGHT; y += 2)
            {
                int value = CANVAS.getRGB(x, y);
                int[] argb = argb(value);
                score += abs(ARGB[x][y][1] - argb[1]);
                score += abs(ARGB[x][y][2] - argb[2]);
                score += abs(ARGB[x][y][3] - argb[3]);
            }
        }
        
        return 0l;
    }
}
