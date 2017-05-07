package gen2;

import java.awt.Color;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import java.util.Random;

public class PolygonFactory
{

    private static final Random R = new Random();
    private final int MINX, MINY, MAXX, MAXY;
    private final int WIDTH;
    private final int HEIGHT;

    public PolygonFactory(int minx, int miny, int maxx, int maxy)
    {
        this.MINX = min(minx, maxx);
        this.MAXX = max(minx, maxx);
        this.MINY = min(miny, maxy);
        this.MAXY = max(miny, maxy);
        this.WIDTH = MAXX - MINX;
        this.HEIGHT = MAXY - MINY;
    }

    public String getSVG(Polygon[] ps)
    {
        StringBuilder builder = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                .append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n\t")
                .append("\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n")
                .append("<svg xmlns=\"http://www.w3.org/2000/svg\"\n\t")
                .append("xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n\t")
                .append("xmlns:ev=\"http://www.w3.org/2001/xml-events\"\n\t")
                .append("version=\"1.1\"\n\t")
                .append("baseProfile=\"full\"\n\t")
                .append("width=\"")
                .append(WIDTH)
                .append("px\"\n\t")
                .append("height=\"")
                .append(HEIGHT)
                .append("px\">\n");

        builder.append("<polygon points=\"0 0 ")
                .append(WIDTH).append(" 0 ")
                .append(WIDTH).append(" ").append(HEIGHT).append(" ")
                .append("0 ").append(HEIGHT)
                .append("\" fill=\"rgb(255,255,255)\" ")
                .append("opacity=\"0.9999999999999999\"/>\n");
                
        for(Polygon p : ps)
        {
            builder.append(getSVG(p));
        }

        builder.append("</svg>");

        return builder.toString();
    }

    public String getSVG(Polygon p)
    {
        int[] xs = p.getXs();
        int[] ys = p.getYs();
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();
        double a = p.getAlpha() / 255.0;
        
        StringBuilder builder = new StringBuilder()
                .append("<polygon points=\"");
        
        for(int i = 0; i < xs.length; i++)
        {
            builder.append(xs[i])
                    .append(" ")
                    .append(ys[i])
                    .append(" ");
        }
        
        builder.append("\" fill=\"rgb(")
                .append(r).append(",")
                .append(g).append(",")
                .append(b).append(")\" ")
                .append("opacity=\"")
                .append(a)
                .append("\"/>\n");
        
        return builder.toString();
    }

    public Polygon getRotated(Polygon p, double theta)
    {
        throw new UnsupportedOperationException("NOT IMPLEMENTED");
        
//        int[] xs = p.getXs();
//        int[] ys = p.getYs();
//        int size = p.getSize();
//
//        int[] newxs = new int[size];
//        int[] newys = new int[size];
//
//        int x1 = _min(xs);
//        int x2 = _max(xs);
//        int y1 = _min(ys);
//        int y2 = _max(ys);
//
//        double cx = x1 + ((x2 - x1) / 2.0) - x2;
//        double cy = y1 + ((y2 - y1) / 2.0) - y2;
//
//        double sin = sin(theta);
//        double cos = cos(theta);
//
//        for(int i = 0; i < size; i++)
//        {
//            double ox = xs[i] - cx;
//            double oy = ys[i] - cy;
//            newxs[i] = (int) round((ox * cos + oy * sin) + cx);
//            newys[i] = (int) round((ox * sin + oy * cos) + cy);
//        }
//
//        return new Polygon(newxs, newys, p.getColor());
    }

    private int _min(int[] a)
    {
        int min = a[0];

        for(int i = 1; i < a.length; i++)
        {
            if(a[i] < min)
            {
                min = a[i];
            }
        }

        return min;
    }

    private int _max(int[] a)
    {
        int max = a[0];

        for(int i = 1; i < a.length; i++)
        {
            if(a[i] > max)
            {
                max = a[i];
            }
        }

        return max;
    }

    public Polygon getRecolored(Polygon p, int dr, int dg, int db)
    {
        int r = R.nextInt(dr) - (dr / 2);
        int g = R.nextInt(dg) - (dg / 2);
        int b = R.nextInt(db) - (db / 2);

        return new Polygon(
                p.getXs(),
                p.getYs(),
                new Color(
                        min(255, max(0, p.getRed() + r)),
                        min(255, max(0, p.getGreen() + g)),
                        min(255, max(0, p.getBlue() + b)),
                        p.getAlpha()));
    }

    public Polygon getTranslated(Polygon p, int dx, int dy)
    {
        int size = p.getSize();
        int[] xs = new int[size];
        int[] ys = new int[size];

        for(int i = 0; i < size; i++)
        {
            xs[i] = min(MAXX, max(MINX, p.getX(i) + dx));
            ys[i] = min(MAXY, max(MINY, p.getY(i) + dy));
        }

        return new Polygon(xs, ys, p.getColor());
    }

    public Polygon getScaled(Polygon p, double f)
    {
        int size = p.getSize();
        int[] xs = new int[size];
        int[] ys = new int[size];

        for(int i = 0; i < size; i++)
        {
            xs[i] = min(MAXX, max(MINX, (int) round(p.getX(i) * f)));
            ys[i] = min(MAXY, max(MINY, (int) round(p.getY(i) * f)));
        }

        return new Polygon(xs, ys, p.getColor());
    }

    public Polygon[] getRandom(int size, int count)
    {
        Polygon[] array = new Polygon[count];

        for(int i = 0; i < count; i++)
        {
            array[i] = getRandom(size);
        }

        return array;
    }

    public Polygon getRandom(int size)
    {
        if(size < 3)
        {
            throw new IllegalArgumentException("size < 3");
        }

        int[] xs = new int[size];
        int[] ys = new int[size];

        for(int i = 0; i < size; i++)
        {
            xs[i] = MINX + R.nextInt(WIDTH);
            ys[i] = MINY + R.nextInt(HEIGHT);
        }

        Color color = new Color(
                R.nextInt(256),
                R.nextInt(256),
                R.nextInt(256),
                128);

        return new Polygon(xs, ys, color);
    }
}
