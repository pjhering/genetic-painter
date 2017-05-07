package gen2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;

public class Expresser
{
    
    private final BufferedImage CANVAS;
    private final int WIDTH, HEIGHT;
    
    public Expresser (BufferedImage canvas)
    {
        this.CANVAS = requireNonNull(canvas);
        this.WIDTH = CANVAS.getWidth();
        this.HEIGHT = CANVAS.getHeight();
    }
    
    public void express(Individual individual)
    {
        Graphics g = CANVAS.getGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        for(Polygon p : individual.getPolygons())
        {
            p.draw(g);
        }
    }
    
    public BufferedImage getCanvas()
    {
        return CANVAS;
    }
}
