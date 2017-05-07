package gen2;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class Viewer implements WindowListener
{

    public final int WIDTH;
    public final int HEIGHT;
    private final JFrame FRAME;
    private final PolygonPanel CANVAS;
    private boolean open;

    public Viewer(int width, int height)
    {
        WIDTH = width;
        HEIGHT = height;
        Dimension size = new Dimension(WIDTH, HEIGHT);
        
        CANVAS = new PolygonPanel();
        CANVAS.setSize(size);
        CANVAS.setPreferredSize(size);
        CANVAS.setMinimumSize(size);
        CANVAS.setMaximumSize(size);

        FRAME = new JFrame("viewer");
        FRAME.setContentPane(CANVAS);
        FRAME.pack();
        FRAME.setResizable(false);
        FRAME.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        FRAME.addWindowListener(this);
    }

    public void open()
    {
        if(!open)
        {
            open(new Polygon[0]);
        }
    }

    public void open(Polygon[] polygons)
    {
        if(!open)
        {
            CANVAS.setPolygons(polygons);
            FRAME.setLocationRelativeTo(null);
            FRAME.setVisible(true);
        }
    }
    
    public void close()
    {
        FRAME.setVisible(false);
        FRAME.dispose();
    }

    public void show(Polygon[] polygons)
    {
        CANVAS.setPolygons(polygons);
        CANVAS.repaint();
    }

    @Override
    public void windowOpened(WindowEvent e)
    {
        open = true;
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        FRAME.setVisible(false);
        open = false;
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }
}
