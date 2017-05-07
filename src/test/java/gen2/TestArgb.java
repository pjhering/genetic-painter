package gen2;

import java.awt.Color;
import static java.lang.System.out;

public class TestArgb
{
    public static void main(String[] args)
    {
        Color color = new Color(32, 64, 128, 255);
        int value = color.getRGB();
        int[] argb = new int[]
        {
            (value >> 24) & 0xFF,
            (value >> 16) & 0xFF,
            (value >> 8) & 0xFF,
            value & 0xFF
        };
        
        out.println(color);
        out.println(value);
        out.println(argb);
        for(int i : argb) out.print(" " + i);
        out.println();
    }
}
