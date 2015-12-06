package Utilities;

/**
 *
 * @author David
 */

public class Delay {
    
    private int timeDelay;
    private long endTime;
    private boolean started;
    
    public Delay(int t){
        this.timeDelay = t;
        started = false;
    }
    
    public boolean isOver(){
        if(!started)
            return false;
        
        return Time.getTime() >= endTime;
    }
    
        
    public boolean isActive(){
        return started;
    }
    
    public void restart(){
        started = true;
        endTime = timeDelay*1000000000 + Time.getTime();
    }
    
    public void stop(){
        started = false;
    }

    
    public void terminate(){
        started = true;
        endTime = 0;
    }
}
