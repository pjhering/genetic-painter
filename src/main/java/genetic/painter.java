package genetic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.PathIterator;
import static java.awt.geom.PathIterator.SEG_CLOSE;
import static java.awt.geom.PathIterator.SEG_LINETO;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.System.arraycopy;
import static java.lang.System.out;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class painter
{

    private static final File SAVE = new File("genetic-painter");
    private static final Random R = new Random();
    private static final int POP_SIZE = 50;
    private static final int SHAPE_COUNT = 150;
    private static final int VERTEX_COUNT = 3;
    private static final int GENERATIONS = 100_000;
    private static final int ELITE_COUNT = 2;
    private static final double MUTATION_RATE = 0.05;
    private static final Sorter SORTER = new Sorter();
    private static int[][][] test;
    private static long bestFitness, worstFitness;

    public static void main(String[] args)
    {
//        log("main");
        
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
        log("process");
        
        bestFitness = Long.MAX_VALUE;
        worstFitness = Long.MIN_VALUE;
        
        BufferedImage img = typeIntArgb("/" + arg);
        test = argbArray(img);
        
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
            log(count);
            
            evaluateFitness(pop);
            log(bestFitness + " - " + worstFitness);
            
            if(count % 50 == 0)
            {
                save(count, arg, pop[0], w, h);
            }
            
            recombine(pop, minX, minY, maxX, maxY);
            count += 1;
        }
    }
    
    private static int[][][] argbArray(BufferedImage i)
    {
        int w = i.getWidth();
        int h = i.getHeight();
        int[][][] array = new int[w][h][];
        
        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                array[x][y] = argb(i.getRGB(x, y));
            }
        }
        
        return array;
    }

    private static BufferedImage typeIntArgb(String arg) throws IOException
    {
//        log("typeIntArgb");
        
        URL url = painter.class.getResource(arg);
        BufferedImage img = ImageIO.read(url);

        if (img.getType() != TYPE_INT_ARGB)
        {
            return typeIntArgb(img);
        }

        return img;
    }

    private static BufferedImage typeIntArgb(BufferedImage in)
    {
//        log("typeIntArgb");
        
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), TYPE_INT_ARGB);
        Graphics g = out.getGraphics();
        g.drawImage(in, 0, 0, null);

        return out;
    }

    private static Solution[] initialPopulation(BufferedImage img)
    {
        log("initialPopulation");
        
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
//        log("randomSolution");
        
        Shape[] shapes = new Shape[SHAPE_COUNT];
        Color[] colors = new Color[SHAPE_COUNT];

        for (int i = 0; i < SHAPE_COUNT; i++)
        {
            shapes[i] = randomShape(minX, minY, maxX, maxY);
            colors[i] = randomColor();
        }

        return new Solution(shapes, colors);
    }

    private static Shape randomShape(int minX, int minY, int maxX, int maxY)
    {
//        log("randomShape");
        
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
//        log("randomColor");
        
        int r = R.nextInt(256);
        int g = R.nextInt(256);
        int b = R.nextInt(256);

        return new Color(r, g, b, 128);
    }

    private static void evaluateFitness(Solution[] pop)
    {
        log("evaluateFitness");
        
        Arrays.sort(pop, SORTER);
        
        for (Solution s : pop)
        {
            if(!s.hasBeenEvaluated())
            {
                evaluateFitness(s);
            }
        }
    }

    private static void evaluateFitness(Solution s)
    {
//        log("evaluateFitness");

        BufferedImage test = express(s);
        s.setFitness(compare(test));
        
        if(s.getFitness() < bestFitness)
        {
            bestFitness = s.getFitness();
        }
        
        if(s.getFitness() > worstFitness)
        {
            worstFitness = s.getFitness();
            
        }
    }

    private static long compare(BufferedImage b)
    {
//        log("compare");
        
        int w = test.length;
        int h = test[0].length;
        
        long score = 0L;

        for (int x = 0; x < w; x += 2)
        {
            for (int y = 0; y < h; y += 2)
            {
                score += compare(x, y, argb(b.getRGB(x, y)));
            }
        }

        return score;
    }

    private static int compare(int x, int y, int[] b)
    {
//        log("compare");
        
        return abs(test[x][y][1] - b[1]) + 
                abs(test[x][y][2] - b[2]) +
                abs(test[x][y][3] - b[3]);
    }

    private static int[] argb(int p)
    {
//        log("argb");
        
        return new int[]
        {
            (p >> 24) & 0xFF,
            (p >> 16) & 0xFF,
            (p >> 8) & 0xFF,
            p & 0xFF,
        };
    }

    private static BufferedImage express(Solution s)
    {
//        log("express");
        
        int width = test.length;
        int height = test[0].length;
        
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
        log("save");
        
        String name = format("%06d-%s.png", generation, arg);
        File file = new File(SAVE, name);
        out.println(file.getAbsolutePath());
        ImageIO.write(express(solution), "png", file);
    }

    private static void recombine(Solution[] pop, int x1, int y1, int x2, int y2)
    {
        int count = 0;
        int a = 0;
        int b = 1;
        int killed = 0;
        
        while(count < pop.length)
        {
            long meanFitness = round(worstFitness - bestFitness / 2.0);
            
            if(pop[count].getFitness() > worstFitness)
            {
                pop[count] = randomSolution(x1, y1, x2, y2);
                killed += 1;
            }
            else if(pop[count].getFitness() > bestFitness)
            {
                boolean mutate = pop[a].getFitness() > meanFitness || 
                        pop[b].getFitness() > meanFitness;
                
                pop[count] = recombine(pop[a], pop[b], x1, y1, x2, y2, mutate);
                killed += 1;
            }
            
            count += 1;
            a = (a + 1) % (pop.length / 4);
            b = (b + 1) % (pop.length / 3);
        }
        
        log("recombined " + killed + " out of " + pop.length);
    }
    
    private static Solution recombine(Solution a, Solution b, int x1, int y1, int x2, int y2, boolean mutate)
    {
        int length = min(a.SHAPES.length, b.SHAPES.length);
        int c = R.nextInt(length);
        Shape[] shapes = new Shape[length];
        Color[] colors = new Color[length];
        
        arraycopy(a.SHAPES, 0, shapes, 0, c);
        arraycopy(b.SHAPES, c, shapes, c, shapes.length - c);
        
        arraycopy(a.COLORS, 0, colors, 0, c);
        arraycopy(b.COLORS, c, colors, c, colors.length - c);
        
        if(mutate)
        {
            mutate(shapes, colors, x1, y1, x2, y2);
        }
        
        return new Solution(shapes, colors);
    }

    private static void mutate(Shape[] shapes, Color[] colors, int x1, int y1, int x2, int y2)
    {
//        log("mutate");
        
        int i = 2 + R.nextInt(shapes.length - 2);
        shapes[i] = mutate(shapes[i], x1, y1, x2, y2);
        
        int j = 2 + R.nextInt(colors.length - 2);
        colors[j] = mutate(colors[j]);
    }

    private static Color mutate(Color color)
    {
//        log("mutate");
        
        int r = min(255, max(0, (color.getRed() + R.nextInt(33) - 16)));
        int g = min(255, max(0, (color.getGreen() + R.nextInt(33) - 16)));
        int b = min(255, max(0, (color.getBlue() + R.nextInt(33) - 16)));
        
        return new Color(r, g, b, 128);
    }

    private static Shape mutate(Shape shape, int x1, int y1, int x2, int y2)
    {
        log("mutate");
        
        PathIterator path = shape.getPathIterator(null);
        float[] p = new float[6];
        int t = 0;
        int i = 0;
        int[] xs = new int[VERTEX_COUNT];
        int[] ys = new int[VERTEX_COUNT];
        
        while((t = path.currentSegment(p)) != SEG_CLOSE)
        {
            if(t == SEG_LINETO)
            {
                xs[i] = round(p[0]) + (R.nextInt(21) - 10);
                ys[i] = round(p[1]) + (R.nextInt(21) - 10);
                i += 1;
            }
            
            path.next();
        }
        
        return new Polygon(xs, ys, VERTEX_COUNT);
    }
    
    private static void log(Object o)
    {
        out.println(o);
    }
}
