
package Evenements;

import GameObjects.Zones.Building;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FightEvent {
    
    private int x = 0;
    private final int y = 0;
    private final ArrayList<Building> buildingList;
    private ArrayList <Building> BuildingListInFight = new ArrayList();

    private Timer timerFight;
    private TimerTask taskPerformer;
    
    private boolean fightStarted = false, launched = false, firstFight = false;

    public FightEvent(ArrayList<Building> l) {
        buildingList = l;
        
        timerFight = new Timer();
        
        taskPerformer = new TimerTask() {
            @Override
            public void run() {
                x = x + 1;
                Random r = new Random();
                if (x % 2 == 0) {
                    if (x <= 10 && !BuildingListInFight.isEmpty()) {
                        for (int i = 0; i < BuildingListInFight.size(); i++) {
                            System.out.println("Attention ! Il y a une bagarre dans " + BuildingListInFight.get(i).getName() + " !");
                            if (BuildingListInFight.get(i).getHappiness()>= 5) {
                                BuildingListInFight.get(i).setHappinessInt(BuildingListInFight.get(i).getHappiness()- 5);
                            }
                            if (BuildingListInFight.get(i).getCurrentCapacity() <= 0) {
                                BuildingListInFight.get(i).setCapacity(0);
                                System.out.println(BuildingListInFight.get(i).getName() + " ne contient plus aucun habitant");
                            }
                        }
                    }
                    if (x <= 10 && BuildingListInFight.isEmpty()) {
                        Building fightBuilding = buildingList.get(r.nextInt(buildingList.size()));
                        if (!"COMMAND ROOM".equals(fightBuilding.getName()) && !"POLICE".equals(fightBuilding.getName())) {
                            if (fightBuilding.getFight()) {
                                System.out.println("La bagarre dégénère dans " +fightBuilding.getName() + " !");
                                if (fightBuilding.getCurrentCapacity() >= 1 && fightBuilding.getHappiness() >=10) {
                                    fightBuilding.setCapacity(fightBuilding.getCurrentCapacity() - 1);
                                    fightBuilding.setHappinessInt(fightBuilding.getHappiness()-10);
                                }
                            }
                            if (!fightBuilding.getFight()) {
                                System.out.println("Attention ! Une bagarre éclate dans " + fightBuilding.getName() + " !!!");
                                fightBuilding.setFight(true);
                                BuildingListInFight.add(fightBuilding);
                                fightStarted = true;
                                firstFight = true;
                                //JukeBox.play("fire");
                            }
                        }
                    }
                    if (x > 10 && !BuildingListInFight.isEmpty()) {
                        for (int i = 0; i < BuildingListInFight.size(); i++) {
                            if (BuildingListInFight.get(i).getFight()){
                                System.out.println("La bagarre dégénère dans " +BuildingListInFight.get(i).getName() + " !");
                                if (BuildingListInFight.get(i).getCurrentCapacity() >= 1 && BuildingListInFight.get(i).getHappiness() >=10) {
                                    BuildingListInFight.get(i).setCapacity(BuildingListInFight.get(i).getCurrentCapacity() - 1);
                                    BuildingListInFight.get(i).setHappinessInt(BuildingListInFight.get(i).getHappiness()-10);
                                }
                            }
                        }
                        x = 0;
                    }
                }
            }
        };
    }
    
    public boolean getLaunched() {
        return launched;
    }

    public void initFightSystem() {
        launched = false;
        fightStarted = false;
        timerFight = new Timer();
    }
    
    public boolean getFightStarted() {
        return fightStarted;
    }
    
    public boolean getFirstFight() {
        return firstFight;
    }
    
    public void initFirstFight(){
        firstFight = false;
    }
    
    public void launch() {
        timerFight.scheduleAtFixedRate(taskPerformer,0,5000);
        launched = true;
    }
    
    public void fightNeutralizedBuilding(Building b) {
        this.BuildingListInFight.remove(b);
    }

    public Timer getTimer() {
        return timerFight;
    }
    
}
