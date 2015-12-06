package Utilities;

/**
 *
 * @author David
 */

public class Time {
    
    public static final float DAMPING = 18000000f;
    private static long curTime;
    private static long lastTime;
    
    public Time(){
        lastTime= getTime();
        curTime = getTime();
    }
    
    public static long getTime(){
        return System.nanoTime();
    }
    
    public static void update(){
        lastTime = curTime;
        curTime = getTime();
    }
    
    public static float getDelta(){
        return (curTime - lastTime)/DAMPING;
    }
}
