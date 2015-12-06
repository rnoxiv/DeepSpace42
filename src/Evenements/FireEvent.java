package Evenements;

import GameObjects.Zones.Building;
import Interface.Main.SpaceStationView;
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
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int tWidth = (int) screenSize.getWidth();
    private int mWidth = tWidth - tWidth / 5;
    private int sWidth = tWidth - mWidth;
    private int tHeight = (int) screenSize.getHeight();
    SpaceStationView SSView = new SpaceStationView("Space Station View", sWidth, mWidth, tWidth, tHeight,null);
    
    public FireEvent(){
        launch();
    }
    
    public void launch(){
        Timer timer1 = new Timer(5000, new ActionListener() {
            ArrayList<Building> buildingList = SSView.getListBuildings();
            ArrayList<Building> fireBuildingList = new ArrayList();
            ArrayList<ArrayList<Building>> neighboursList = new ArrayList();
            ArrayList<Building> a = new ArrayList();
            Building fireBuilding;
            public void actionPerformed(ActionEvent e) {
                x=x+1;
                Random r = new Random();
                for (int j=0;j<buildingList.size();j++){
                    if (buildingList.get(buildingList.size() - 1 - j).getName() == "Pompiers"){
                        buildingList.remove(buildingList.get(buildingList.size() - 1 - j));
                    }
                }
                if(x % 2 == 0){
                    if(x<=10 && fireBuildingList.size() != 0){
                        for (int i=0;i<fireBuildingList.size();i++){
                            System.out.println("Attention ! " + fireBuildingList.get(fireBuildingList.size() - 1).getName() + " en feu !");
                        }
                    }
                    if(x<=10 && fireBuildingList.size() == 0){
                        Building fireBuilding = buildingList.get(r.nextInt(buildingList.size()));
                        System.out.println("Attention ! " + fireBuilding.getName() + " en feu !");
                        fireBuilding.setFire(true);
                        fireBuilding.setColorBuilding(Color.orange);
                        fireBuildingList.add(fireBuilding);
                    }
                    if(x>10 && fireBuildingList.size() != 0){
                        for (int i=0;i<fireBuildingList.size();i++){
                            if (fireBuildingList.get(fireBuildingList.size() - 1).getNeighbours() != null){
                                neighboursList.add(fireBuildingList.get(fireBuildingList.size() - 1).getNeighbours());
                            }
                            System.out.println("neighboursList =" + neighboursList);
                        }
                        Random s = new Random();
                        if (neighboursList.size()>0){
                            a = neighboursList.get(s.nextInt(neighboursList.size()));
                            Building b = a.get(s.nextInt(a.size()));
                            fireBuildingList.add(b);
                            System.out.println("fireBuildingList = " + fireBuildingList);
                        }
                        if (neighboursList.size()==0){
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