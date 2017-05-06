package geneticpainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import static java.awt.geom.PathIterator.SEG_CLOSE;
import static java.awt.geom.PathIterator.SEG_LINETO;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.System.arraycopy;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class run
{

    private static final File SAVE = new File("genetic-painter");
    private static final Random R = new Random();
    private static final int POP_SIZE = 50;
    private static final int SHAPE_COUNT = 50;
    private static final int VERTEX_COUNT = 3;
    private static final int GENERATIONS = 10;
    private static final double MUTATION_RATE = 0.05;

    public static void main(String[] args)
    {
        if (SAVE.exists() || SAVE.mkdir())
        {
            for (String arg : args)
            {
                try
                {
                    process(arg);
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void process(String arg) throws IOException
    {
        BufferedImage img = typeIntArgb(arg);
        int w = img.getWidth();
        int h = img.getHeight();
        int minX = 0 - (w / 2);
        int minY = 0 - (h / 2);
        int maxX = w + (w / 2);
        int maxY = h + (h / 2);
        
        Solution[] pop = initialPopulation(img);
        int count = 0;

        while (count < GENERATIONS)
        {
            evaluateFitness(img, pop);
            save(count, arg, pop[0], w, h);
            recombine(pop, minX, minY, maxX, maxY);
            count += 1;
        }
    }

    private static BufferedImage typeIntArgb(String arg) throws IOException
    {
        URL url = run.class.getResource(arg);
        BufferedImage img = ImageIO.read(url);

        if (img.getType() != TYPE_INT_ARGB)
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

        for (int i = 0; i < POP_SIZE; i++)
        {
            pop[i] = randomSolution(minX, minY, maxX, maxY);
        }

        return pop;
    }

    private static Solution randomSolution(int minX, int minY, int maxX, int maxY)
    {
        Polygon[] shapes = new Polygon[SHAPE_COUNT];
        Color[] colors = new Color[SHAPE_COUNT];

        for (int i = 0; i < SHAPE_COUNT; i++)
        {
            shapes[i] = randomShape(minX, minY, maxX, maxY);
            colors[i] = randomColor();
        }

        return new Solution(shapes, colors);
    }

    private static Polygon randomShape(int minX, int minY, int maxX, int maxY)
    {
        int w = maxX - minX;
        int h = maxY - minY;
        int[] xs = new int[VERTEX_COUNT];
        int[] ys = new int[VERTEX_COUNT];

        for (int i = 0; i < VERTEX_COUNT; i++)
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
        for (Solution s : pop)
        {
            if(!s.hasBeenEvaluated())
            {
                evaluateFitness(img, s);
            }
        }
    }

    private static void evaluateFitness(BufferedImage ideal, Solution s)
    {
        int w = ideal.getWidth();
        int h = ideal.getHeight();

        BufferedImage test = express(s, w, h);
        s.setFitness(compare(ideal, test));
    }

    private static long compare(BufferedImage a, BufferedImage b)
    {
        long score = Long.MIN_VALUE;
        int w = min(a.getWidth(), b.getWidth());
        int h = min(a.getHeight(), b.getHeight());

        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                score += compare(argb(a.getRGB(x, y)), argb(b.getRGB(x, y)));
            }
        }

        return score;
    }

    private static int compare(int[] a, int[] b)
    {
        return abs(a[0] - b[0]) + abs(a[1] - b[1]) + abs(a[2] - b[2]) + abs(a[3] - b[3]);
    }

    private static int[] argb(int p)
    {
        return new int[]
        {
            (p >> 24) & 0xFF,
            (p >> 16) & 0xFF,
            (p >> 8) & 0xFF,
            p & 0xFF,
        };
    }

    private static BufferedImage express(Solution s, int width, int height)
    {
        BufferedImage test = new BufferedImage(width, height, TYPE_INT_ARGB);
        Graphics2D g = test.createGraphics();

        for (int i = 0; i < SHAPE_COUNT; i++)
        {
            g.setColor(s.COLORS[i]);
            g.fill(s.SHAPES[i]);
        }

        g.dispose();
        return test;
    }

    private static void save(int generation, String arg, Solution solution, int width, int height) throws IOException
    {
        String name = format("%d08-%s.png", generation, arg);
        File file = new File(SAVE, name);
        ImageIO.write(express(solution, width, height), name, file);
    }

    private static void recombine(Solution[] pop, int x1, int y1, int x2, int y2)
    {
        Arrays.sort(pop);
        int count = pop.length * 9 / 10; // ten percent of the population is elite
        
        for(int a = 0; a < pop.length; a++)
        {
            for(int b = a + 1; b < pop.length; b++)
            {
                if(count < pop.length)
                {
                    pop[count] = recombine(pop[a], pop[b], x1, y1, x2, y2);
                    count += 1;
                }
                else
                {
                    return;
                }
            }
        }
    }
    
    private static Solution recombine(Solution a, Solution b, int x1, int y1, int x2, int y2)
    {
        int length = min(a.SHAPES.length, b.SHAPES.length);
        int c = R.nextInt(length);
        Polygon[] shapes = new Polygon[length];
        Color[] colors = new Color[length];
        
        arraycopy(a.SHAPES, 0, shapes, 0, c);
        arraycopy(b.SHAPES, c, shapes, c, shapes.length);
        
        arraycopy(a.COLORS, 0, colors, 0, c);
        arraycopy(b.COLORS, c, colors, c, colors.length);
        
        if(R.nextDouble() < MUTATION_RATE)
        {
            mutate(shapes, colors, x1, y1, x2, y2);
        }
        
        return new Solution(shapes, colors);
    }

    private static void mutate(Polygon[] shapes, Color[] colors, int x1, int y1, int x2, int y2)
    {
        int i = R.nextInt(shapes.length);
        shapes[i] = mutate(shapes[i]);
        
        int j = R.nextInt(colors.length);
        colors[j] = mutate(colors[j]);
    }

    private static Color mutate(Color color)
    {
        int r = min(255, max(0, (color.getRed() + R.nextInt(64) - 32)));
        int g = min(255, max(0, (color.getGreen() + R.nextInt(64) - 32)));
        int b = min(255, max(0, (color.getBlue() + R.nextInt(64) - 32)));
        int a = min(255, max(0, (color.getAlpha() + R.nextInt(64) - 32)));
        
        return new Color(r, g, b, a);
    }

    private static Polygon mutate(Polygon shape, int x1, int y1, int x2, int y2)
    {
        double theta = R.nextDouble() * PI / 2;
        double cx = (x2 - x1) / 2.0;
        double cy = (y2 - y1) / 2.0;
        AffineTransform at = AffineTransform.getRotateInstance(theta, cx, cy);
        PathIterator pi = shape.getPathIterator(at);
        
        float[] pts = new float[6];
        int[] xs = new int[VERTEX_COUNT];
        int[] ys = new int[VERTEX_COUNT];
        int type = 0;
        int i = 0;
        
        while((type = pi.currentSegment(pts)) != SEG_CLOSE)
        {
            if(type == SEG_LINETO)
            {
                xs[i] = round(pts[0]);
                ys[i] = round(pts[1]);
                i += 1;
            }
        }
        
        return new Polygon(xs, ys, VERTEX_COUNT);
    }
}
