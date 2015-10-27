package Main;

import java.awt.Color;
import java.awt.Shape;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Variables {

    private final String lexic = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

    private final Random rand = new java.util.Random();

    final Set<String> identifiers = new HashSet<String>();

    public static final Color RED = new Color(195, 20, 20);
    public static final Color GREEN = new Color(50, 190, 70);

    private Color mainColor = GREEN;

    public Variables() {

    }

    public String randomName() {
        StringBuilder b = new StringBuilder();
        while (b.toString().length() == 0) {
            int length = rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                b.append(lexic.charAt(rand.nextInt(lexic.length())));
            }
            if (identifiers.contains(b.toString())) 
             {
                b = new StringBuilder();
            }
        }
        System.out.println(b.toString());
        return b.toString();
    }
    
    public double calculateDistanceFromRadar(float cx, float cy, float x, float y){
        return Math.sqrt((cx-x)*(cx-x) + (cy-y)*(cy-y));
    }
    
    public Color getMainColor() {
        return mainColor;
    }

    public void setMainColor(Color c) {
        mainColor = c;
    }
}
