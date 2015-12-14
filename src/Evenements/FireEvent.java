package Evenements;

import GameObjects.Zones.Building;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class FireEvent extends Evenement{
 
    private int x=0;
    private final int y=0;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final ArrayList<Building> buildingList;
    
    private boolean launched = false;
    
    public FireEvent(ArrayList<Building> l){
        buildingList = l;
    }
    
    public boolean getLaunched(){
        return launched;
    }
    
    public void launch(){
        Timer timer1;
        timer1 = new Timer(5000, new ActionListener() {
            ArrayList<Building> fireBuildingList = new ArrayList();
            ArrayList<ArrayList<Building>> neighboursList = new ArrayList();
            ArrayList<Building> a = new ArrayList();
            Building fireBuilding;
            public void actionPerformed(ActionEvent e) {
                launched = true;
                x=x+1;
                Random r = new Random();
                if(x % 2 == 0){
                    if(x<=10 && !fireBuildingList.isEmpty()){
                        for (int i=0;i<fireBuildingList.size();i++){
                            System.out.println("Attention ! " + fireBuildingList.get(i).getName() + " en feu !");
                            if (fireBuildingList.get(i).getCurrentCapacity()>=5){
                                fireBuildingList.get(i).setCapacity(fireBuildingList.get(i).getCurrentCapacity()-5);
                            }
                            if (fireBuildingList.get(i).getCurrentCapacity()<=0){
                                fireBuildingList.get(i).setCapacity(0);
                                System.out.println(fireBuildingList.get(i).getName() + " ne contient plus aucun habitant");
                            }
                        }
                    }
                    if(x<=10 && fireBuildingList.isEmpty()){
                        Building fireBuilding = buildingList.get(r.nextInt(buildingList.size()));
                        if (!"ARRIVALS/DEPARTURES".equals(fireBuilding.getName()) && !"FIRE DEPARTMENT".equals(fireBuilding.getName())){
                            if (fireBuilding.getFire() == true){
                                System.out.println(fireBuilding.getName() + " est en feu, des personnes succombent dans l'incendie !");
                                if (fireBuilding.getCurrentCapacity()>=20){
                                    fireBuilding.setCapacity(fireBuilding.getCurrentCapacity()-20);
                                }
                            }
                            if (fireBuilding.getFire() == false){
                                System.out.println("Attention ! " + fireBuilding.getName() + " en feu !!!");
                                fireBuilding.setFire(true);
                                fireBuilding.setColorBuilding(Color.orange);
                                fireBuildingList.add(fireBuilding);
                            }
                        }
                    }
                    if(x>10 && !fireBuildingList.isEmpty()){
                        for (int i=0;i<fireBuildingList.size();i++){
                            neighboursList.add(fireBuildingList.get(fireBuildingList.size() - 1).addNeighbourFire(buildingList,fireBuildingList.get(fireBuildingList.size() - 1)));
                            //System.out.println("neighboursList =" + neighboursList);
                        }
                        Random s = new Random();
                        if (neighboursList.size()>0){
                            a = neighboursList.get(s.nextInt(neighboursList.size()));
                            Building b = a.get(s.nextInt(a.size()));
                            if (b.getFire()==false && !"ARRIVALS/DEPARTURES".equals(b.getName()) && !"FIRE DEPARTMENT".equals(b.getName())){
                                b.setFire(true);
                                b.setColorBuilding(Color.orange);
                                fireBuildingList.add(b);
                                //System.out.println("fireBuildingList = " + fireBuildingList);
                            }
                            if (b.getFire()==true && !"ARRIVALS/DEPARTURES".equals(b.getName()) && !"FIRE DEPARTMENT".equals(b.getName())){
                                System.out.println(b.getName() + " est en feu, des personnes succombent dans l'incendie !");
                                if (b.getCurrentCapacity()>=20){
                                    b.setCapacity(b.getCurrentCapacity()-20);
                                }
                                if (b.getCurrentCapacity()<=0){
                                    b.setCapacity(0);
                                    System.out.println(b.getName() + " ne contient plus aucun habitant");
                                }
                            }
                        }
                        if (neighboursList.isEmpty()){
                            System.out.println("erreur : la taille de la liste des voisins est vide");
                        }
                        x=0;
                    }
                }
            }
        });
        timer1.start();
    }
    
}