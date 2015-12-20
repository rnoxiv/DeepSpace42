package Evenements;

import GameObjects.Zones.Building;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//CLASSE EVENEMENT, DEFINI UN EVENEMENT ET SA PROGRESSION REGULIERE (A PARTIR D'UN TIMER) DANS LES BATIMENTS 
public class Evenement {
    
    protected int x = 0;
    protected final int y = 0;
    
    protected final ArrayList<Building> buildingList; //LISTE DE TOUS LES BATIMENTS
    protected ArrayList<ArrayList<Building>> neighboursList = new ArrayList(); //LISTE DES BATIMENTS VOISINS
    protected ArrayList<Building> a = new ArrayList();
    protected ArrayList<Building> buildingListEvent = new ArrayList(); //LISTE DES BATIMENTS TOUCHES PAR L'EVENT
    
    //TIMER 
    protected Timer timerEvent;
    protected TimerTask taskPerformer;
    
    //BOOLEANS GERANT L'EVENT
    protected boolean launched = false, started = false, firstLoop = false;
    
    public Evenement(ArrayList<Building> l) {
        this.buildingList = l;        
        this.timerEvent = new Timer();            
        this.taskPerformer = new TimerTask(){
            @Override
            public void run(){
                
            }
        };
    }
    
    public boolean getLaunched() {
        return this.launched;
    }

    public void initSystem() {
        this.launched = false;
        this.started = false;
        this.timerEvent = new Timer();
    }
    
    public boolean getStarted() {
        return this.started;
    }
    
    public boolean getFirstLoop() {
        return this.firstLoop;
    }
    
    public void initFirstLoop(){
        this.firstLoop = false;
    }
    
    public void launch() {
        this.timerEvent.scheduleAtFixedRate(taskPerformer,0,5000);
        this.launched = true;
    }
    
    public void neutralizedBuilding(Building b) {
        this.buildingListEvent.remove(b);
    }

    public Timer getTimer() {
        return timerEvent;
    }
}
