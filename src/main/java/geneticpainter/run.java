package geneticpainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class run
{
    private static final Random R = new Random();
    private static final int POP_SIZE = 50;
    private static final int SHAPE_COUNT = 50;
    private static final int VERTEX_COUNT = 3;
    private static final int GENERATIONS = 10;
    
    
    public static void main(String[] args)
    {
        for(String arg : args)
        {
            try
            {
                process(arg);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private static void process(String arg) throws IOException
    {
        BufferedImage img = typeIntArgb(arg);
        Solution[] pop = initialPopulation(img);
        int count = 0;
        
        while(count < GENERATIONS)
        {
            evaluateFitness(img, pop);
            Arrays.sort(pop);
            save(pop[0], img.getWidth(), img.getHeight());
            recombine(pop);
            count += 1;
        }
    }

    private static BufferedImage typeIntArgb(String arg) throws IOException
    {
        URL url = run.class.getResource(arg);
        BufferedImage img = ImageIO.read(url);
        
        if(img.getType() != TYPE_INT_ARGB)
        {
            return typeIntArgb(img);
        }
        
        return img;
    }
    
    private static BufferedImage typeIntArgb(BufferedImage in)
    {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), TYPE_INT_ARGB);
        Graphics g = out.getGraphics();
        g.drawImage(in, 0, 0, null);
        
        return out;
    }

    private static Solution[] initialPopulation(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        int minX = 0 - (w / 2);
        int maxX = w + (w / 2);
        int minY = 0 - (h / 2);
        int maxY = h + (h / 2);
        
        Solution[] pop = new Solution[POP_SIZE];
        
        for(int i = 0; i < POP_SIZE; i++)
        {
            pop[i] = randomSolution(minX, minY, maxX, maxY);
        }
        
        return pop;
    }

    private static Solution randomSolution(int minX, int minY, int maxX, int maxY)
    {
        Shape[] shapes = new Shape[SHAPE_COUNT];
        Color[] colors = new Color[SHAPE_COUNT];
        
        for(int i = 0; i < SHAPE_COUNT; i++)
        {
            shapes[i] = randomShape(minX, minY, maxX, maxY);
            colors[i] = randomColor();
        }
        
        return new Solution(shapes, colors);
    }

    private static Shape randomShape(int minX, int minY, int maxX, int maxY)
    {
        int w = maxX - minX;
        int h = maxY - minY;
        int[] xs = new int[VERTEX_COUNT];
        int[] ys = new int[VERTEX_COUNT];
        
        for(int i = 0; i < VERTEX_COUNT; i++)
        {
            xs[i] = minX + R.nextInt(w);
            ys[i] = minY + R.nextInt(h);
        }
        
        return new Polygon(xs, ys, VERTEX_COUNT);
    }

    private static Color randomColor()
    {
        int r = R.nextInt(256);
        int g = R.nextInt(256);
        int b = R.nextInt(256);
        int a = R.nextInt(256);
        
        return new Color(r, g, b, a);
    }

    private static void evaluateFitness(BufferedImage img, Solution[] pop)
    {
    }

    private static void save(Solution solution, int width, int height)
    {
    }

    private static void recombine(Solution[] pop)
    {
    }
}
