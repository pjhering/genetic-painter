package gen2;

import java.awt.Graphics;
import static java.util.Objects.requireNonNull;
import javax.swing.JComponent;

public class PolygonPanel extends JComponent
{

    private Polygon[] polygons;
    
    public PolygonPanel(Polygon[] polygons)
    {
        this.polygons = requireNonNull(polygons);
    }
    
    public PolygonPanel()
    {
        this(new Polygon[0]);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        g.clearRect(0, 0, getWidth(), getHeight());
        
        for(Polygon p : polygons)
        {
            p.draw(g);
        }
    }

    public Polygon[] getPolygons()
    {
        return polygons;
    }

    public void setPolygons(Polygon[] polygons)
    {
        this.polygons = polygons;
    }
}
