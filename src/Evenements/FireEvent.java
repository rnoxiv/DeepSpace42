package Evenements;

import GameObjects.Zones.Building;
import Utilities.JukeBox;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FireEvent {

    private int x = 0;
    private final int y = 0;
    private final ArrayList<Building> buildingList;

    ArrayList<ArrayList<Building>> neighboursList = new ArrayList();
    ArrayList<Building> a = new ArrayList();
    ArrayList<Building> fireBuildingList = new ArrayList();

    private Timer timerFire;
    private TimerTask taskPerformer;
    
    private boolean fireStarted = false, launched = false, firstFire = false;

    public FireEvent(ArrayList<Building> l) {
        buildingList = l;
        
        timerFire = new Timer();
        
        taskPerformer = new TimerTask() {
            @Override
            public void run() {
                x = x + 1;
                Random r = new Random();
                if (x % 2 == 0) {
                    if (x <= 10 && !fireBuildingList.isEmpty()) {
                        for (int i = 0; i < fireBuildingList.size(); i++) {
                            System.out.println("Attention ! " + fireBuildingList.get(i).getName() + " en feu !");
                            if (fireBuildingList.get(i).getCurrentCapacity() >= 5) {
                                fireBuildingList.get(i).setCapacity(fireBuildingList.get(i).getCurrentCapacity() - 5);
                            }
                            if (fireBuildingList.get(i).getCurrentCapacity() <= 0) {
                                fireBuildingList.get(i).setCapacity(0);
                                System.out.println(fireBuildingList.get(i).getName() + " ne contient plus aucun habitant");
                            }
                        }
                    }
                    if (x <= 10 && fireBuildingList.isEmpty()) {
                        Building fireBuilding = buildingList.get(r.nextInt(buildingList.size()));
                        if (!"ARRIVALS/DEPARTURES".equals(fireBuilding.getName()) && !"FIRE DEPARTMENT".equals(fireBuilding.getName())) {
                            if (fireBuilding.getFire()) {
                                System.out.println(fireBuilding.getName() + " est en feu, des personnes succombent dans l'incendie !");
                                if (fireBuilding.getCurrentCapacity() >= 20) {
                                    fireBuilding.setCapacity(fireBuilding.getCurrentCapacity() - 20);
                                }
                            }
                            if (!fireBuilding.getFire()) {
                                System.out.println("Attention ! " + fireBuilding.getName() + " en feu !!!");
                                fireBuilding.setFire(true);
                                fireBuildingList.add(fireBuilding);
                                fireStarted = true;
                                firstFire = true;
                                JukeBox.play("fire");
                            }
                        }
                    }
                    if (x > 10 && !fireBuildingList.isEmpty()) {
                        for (int i = 0; i < fireBuildingList.size(); i++) {
                            neighboursList.add(fireBuildingList.get(fireBuildingList.size() - 1).getNeighbours());
                        }
                        Random s = new Random();
                        if (neighboursList.size() > 0) {
                            a = neighboursList.get(s.nextInt(neighboursList.size()));
                            Building b = a.get(s.nextInt(a.size()));
                            if (b.getFire() == false && !"ARRIVALS/DEPARTURES".equals(b.getName()) && !"FIRE DEPARTMENT".equals(b.getName())) {
                                b.setFire(true);
                                fireBuildingList.add(b);
                            }
                            if (b.getFire() == true && !"ARRIVALS/DEPARTURES".equals(b.getName()) && !"FIRE DEPARTMENT".equals(b.getName())) {
                                if (b.getCurrentCapacity() >= 20) {
                                    b.setCapacity(b.getCurrentCapacity() - 20);
                                }
                                if (b.getCurrentCapacity() <= 0) {
                                    b.setCapacity(0);
                                }
                            }
                        }
                        x = 0;
                    }
                }
            }
        }
    }
    
    public boolean getLaunched() {
        return launched;
    }

    public void initFireSystem() {
        launched = false;
        fireStarted = false;
        timerFire = new Timer();
    }
    
    public boolean getFireStarted() {
        return fireStarted;
    }
    
    public boolean getFirstFire() {
        return firstFire;
    }
    
    public void initFirstFire(){
        firstFire = false;
    }
    
    public void launch() {
        timerFire.scheduleAtFixedRate(taskPerformer,0,5000);
        launched = true;
    }
    
    public void fireNeutralizedBuilding(Building b) {
        this.fireBuildingList.remove(b);
    }

    public Timer getTimer() {
        return timerFire;
    }
}
