package gen2;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.min;
import static java.lang.System.arraycopy;
import static java.util.Objects.requireNonNull;

public class Polygon
{
    private final int[] XS;
    private final int[] YS;
    private final int SIZE;
    private final Color COLOR;
    
    public Polygon(int[] xs, int[] ys, Color color)
    {
        this.SIZE = min(xs.length, ys.length);
        
        if(SIZE < 3)
        {
            throw new IllegalArgumentException("size < 3");
        }
        
        this.XS = new int[SIZE];
        this.YS = new int[SIZE];
        arraycopy(xs, 0, XS, 0, SIZE);
        arraycopy(ys, 0, YS, 0, SIZE);
        this.COLOR = requireNonNull(color);
    }
    
    public void draw(Graphics g)
    {
        g.setColor(COLOR);
        g.fillPolygon(XS, YS, SIZE);
    }
    
    int[] getXs()
    {
        return XS;
    }
    
    int getX(int i)
    {
        return XS[i];
    }
    
    int getY(int i)
    {
        return YS[i];
    }
    
    int[] getYs()
    {
        return YS;
    }
    
    int getSize()
    {
        return SIZE;
    }
    
    Color getColor()
    {
        return COLOR;
    }

    int getRed()
    {
        return COLOR.getRed();
    }

    int getGreen()
    {
        return COLOR.getGreen();
    }

    int getBlue()
    {
        return COLOR.getBlue();
    }

    int getAlpha()
    {
        return COLOR.getAlpha();
    }
}
