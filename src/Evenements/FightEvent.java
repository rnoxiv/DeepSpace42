
package Evenements;

import GameObjects.Zones.Building;
import Utilities.JukeBox;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

//Evennement Bagarre, hérite de la classe Evennement
public class FightEvent extends Evenement{

    public FightEvent(ArrayList<Building> l) {
        super(l);
        taskPerformer = new TimerTask() {
            @Override
            public void run() {
                x = x + 1;
                Random r = new Random();
                if (x % 2 == 0) {
                    if (x <= 10 && !buildingListEvent.isEmpty()) {
                        for (int i = 0; i < buildingListEvent.size(); i++) {
                            System.out.println("Attention ! Il y a une bagarre dans " + buildingListEvent.get(i).getName() + " !");
                            if (buildingListEvent.get(i).getHappiness()>= 5) {
                                buildingListEvent.get(i).setHappinessInt(buildingListEvent.get(i).getHappiness()- 5);
                            }
                            if (buildingListEvent.get(i).getHappiness()< 5) {
                                buildingListEvent.get(i).setHappinessInt(0);
                            }
                            if (buildingListEvent.get(i).getCurrentCapacity() <= 0) {
                                buildingListEvent.get(i).setCapacity(0);
                                System.out.println(buildingListEvent.get(i).getName() + " ne contient plus aucun habitant");
                            }
                        }
                    }
                    if (x <= 10 && buildingListEvent.isEmpty()) {
                        Building fightBuilding = buildingList.get(r.nextInt(buildingList.size()));
                        if (!"COMMAND ROOM".equals(fightBuilding.getName()) && !"POLICE".equals(fightBuilding.getName()) && fightBuilding.getCurrentCapacity()>fightBuilding.getMaxCapacity()/10) {
                            if (fightBuilding.getFight()) {
                                System.out.println("La bagarre dégénère dans " +fightBuilding.getName() + " !");
                                if (fightBuilding.getCurrentCapacity() >= 1 && fightBuilding.getHappiness() >=10) {
                                    fightBuilding.setCapacity(fightBuilding.getCurrentCapacity() - 1);
                                    fightBuilding.setHappinessInt(fightBuilding.getHappiness()-10);
                                }
                                if (fightBuilding.getCurrentCapacity() >= 1 && fightBuilding.getHappiness() <10) {
                                    fightBuilding.setCapacity(fightBuilding.getCurrentCapacity() - 1);
                                    fightBuilding.setHappinessInt(0);
                                }
                                
                            }
                            if (!fightBuilding.getFight()) {
                                System.out.println("Attention ! Une bagarre éclate dans " + fightBuilding.getName() + " !!!");
                                fightBuilding.setFight(true);
                                buildingListEvent.add(fightBuilding);
                                started = true;
                                firstLoop = true;
                                JukeBox.play("fight");
                            }
                        }else{
                            x = 0;
                        }
                    }
                    if (x > 10 && !buildingListEvent.isEmpty()) {
                        for (int i = 0; i < buildingListEvent.size(); i++) {
                            if (buildingListEvent.get(i).getFight()){
                                System.out.println("La bagarre dégénère dans " +buildingListEvent.get(i).getName() + " !");
                                if (buildingListEvent.get(i).getCurrentCapacity() >= 1 && buildingListEvent.get(i).getHappiness() >=10) {
                                    buildingListEvent.get(i).setCapacity(buildingListEvent.get(i).getCurrentCapacity() - 1);
                                    buildingListEvent.get(i).setHappinessInt(buildingListEvent.get(i).getHappiness()-10);
                                }
                                if (buildingListEvent.get(i).getCurrentCapacity() >= 1 && buildingListEvent.get(i).getHappiness() <10) {
                                    buildingListEvent.get(i).setCapacity(buildingListEvent.get(i).getCurrentCapacity() - 1);
                                    buildingListEvent.get(i).setHappinessInt(0);
                                }
                            }
                        }
                        x = 0;
                    }
                }
            }
        };
    }
}
