package Evenements;

import GameObjects.Zones.Building;
import Utilities.JukeBox;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class FireEvent extends Evenement{

    public FireEvent(ArrayList<Building> l) {
        super(l);
        
        this.taskPerformer = new TimerTask() {
            @Override
            public void run() {
                x = x + 1;
                Random r = new Random();
                if (x % 2 == 0) {
                    if (x <= 10 && !buildingListEvent.isEmpty()) {
                        for (int i = 0; i < buildingListEvent.size(); i++) {
                            System.out.println("Attention ! " + buildingListEvent.get(i).getName() + " en feu !");
                            if (buildingListEvent.get(i).getCurrentCapacity() >= 5) {
                                buildingListEvent.get(i).setCapacity(buildingListEvent.get(i).getCurrentCapacity() - 5);
                            }
                            if (buildingListEvent.get(i).getCurrentCapacity() <= 0) {
                                buildingListEvent.get(i).setCapacity(0);
                                System.out.println(buildingListEvent.get(i).getName() + " ne contient plus aucun habitant");
                            }
                        }
                    }
                    if (x <= 10 && buildingListEvent.isEmpty()) {
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
                                buildingListEvent.add(fireBuilding);
                                started = true;
                                firstLoop = true;
                                JukeBox.play("fire");
                            }
                        }
                    }
                    if (x > 10 && !buildingListEvent.isEmpty()) {
                        for (int i = 0; i < buildingListEvent.size(); i++) {
                            neighboursList.add(buildingListEvent.get(buildingListEvent.size() - 1).getNeighbours());
                        }
                        Random s = new Random();
                        if (neighboursList.size() > 0) {
                            a = neighboursList.get(s.nextInt(neighboursList.size()));
                            Building b = a.get(s.nextInt(a.size()));
                            if (b.getFire() == false && !"ARRIVALS/DEPARTURES".equals(b.getName()) && !"FIRE DEPARTMENT".equals(b.getName())) {
                                b.setFire(true);
                                buildingListEvent.add(b);
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
        };
    }
}
