
package GameObjects.Actors;

import GameObjects.Actor;
import GameObjects.Zone;

public class Urgence extends Actor{
    
    private String number;
    private boolean busy;
    private int time;
    
    public Urgence (Zone zone, String _number, boolean _busy, int _time){
        super(zone);
        this.number = _number;
        this.busy = _busy;
        this.time = _time;
    }
    
    public String getNumber(){
        return this.number;
    }
        
    public boolean getBusy(){
        return this.busy;
    }
    
    public int getTime(){
        return this.time;
    }
    
    public void setNumber(String _number){
        this.number = _number;
    }
        
    public void setBusy(boolean _busy){
        this.busy = _busy;
    }
    
    public void setTime(int _time){
        this.time = _time;
    }
    
}
