package Interface.Main;

import GameObjects.Zones.Building;
import GameObjects.Zones.Buildings.Dock;
import Interface.MainPanel;
import Utilities.Variables;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class SpaceStationView extends MainPanel {

    private static final String IPANELNAME = "Buildings";

    private final ArrayList<Building> neighbours;

    private Variables var = new Variables();

    private int rbWidth = 0;

    private int totalCapOut = 0;
    
    private boolean shipInSpaceDirection = false;
    
    private static final int numBuildings = 24;

    private static final int HALLARRIVE = 17;

    private ArrayList<Building> docksList = new ArrayList();

    private int fireBuildingNumber = 0;

    private final ArrayList<Building> listBuilding;
    private final ArrayList<Dock> listHangars;
    private final String[] namesBuildings;
    private final int[] posBuildings;
    private final int[] maxCapBuilding;
    private final int[] tail;
    private final int[] head;

    public SpaceStationView(String n, int sW, int mW, int tH, int tW, String sound) {
        super(n, sW, mW, tW, tH, Building.class, IPANELNAME, sound);

        namesBuildings = new String[]{"COMMAND ROOM", "CINEMA", "BEDROOMS", "POLICE", "PRISON", "HOSTEL", "BAR", "BEDROOMS", "FIRE DEPARTMENT", "RESTAURANT", "MAIN HALL", "SHOPS", "GARDENS", "HOSPITAL", "WAREHOUSES",
            "ARMS DEALER", "ENGINE ROOM", "ARRIVALS/DEPARTURES", "DEFENCE ROOM", "DOCK A", "DOCK B", "DOCK C", "DOCK D", "DOCK E"};

        posBuildings = new int[]{width / 2, height / 8, width / 6, height / 4, width / 3, height / 8, 2 * width / 3, height / 8, 5 * width / 6, height / 4, width / 6, 3 * height / 8, width / 3, height / 4, 2 * width / 3, height / 4, 5 * width / 6, 3 * height / 8, width / 6, height / 2, width / 2, height / 2, 5 * width / 6, height / 2, width / 6, 5 * height / 8, width / 3, 5 * height / 8, 2 * width / 3, 5 * height / 8, 5 * width / 6, 5 * height / 8, width / 6, 3 * height / 4, width / 2, 3 * height / 4, 5 * width / 6, 3 * height / 4, width / 6, 7 * height / 8, width / 3, 7 * height / 8, width / 2, 7 * height / 8, 2 * width / 3, 7 * height / 8, 5 * width / 6, 7 * height / 8};

        maxCapBuilding = new int[]{1, 1000, 3000, 250, 500, 2000, 50, 3000, 300, 1000, 5000, 1000, 4000, 2500, 300, 50, 50, 1500, 1000, 1500, 500, 750, 500, 1000};
        tail = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        head = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 17, 10, 17, 17, 17, 17, 17, 17};
        //#ROP

        listBuilding = new ArrayList();
        listHangars = new ArrayList();
        neighbours = new ArrayList();
        for (int i = 0; i < numBuildings; i++) {
            //listBuilding.add(new Building(namesBuildings[i], maxCapBuilding[i], tailleBuildings[2 * i], tailleBuildings[(2 * i) + 1], sW + posBuildings[2 * i] - (tailleBuildings[2 * i] / 2),topHeight +  posBuildings[(2 * i) + 1] - (tailleBuildings[(2 * i) + 1] / 2), i));
            if (i < numBuildings - 5) {
                listBuilding.add(new Building(namesBuildings[i], maxCapBuilding[i], sW + posBuildings[2 * i], topHeight + posBuildings[(2 * i) + 1], "test", sWidth, topHeight));
            } else {
                //test!
                neighbours.add(listBuilding.get(HALLARRIVE));
                Dock hangar = new Dock(namesBuildings[i], maxCapBuilding[i], sW + posBuildings[2 * i], topHeight + posBuildings[(2 * i) + 1], sWidth, topHeight, neighbours);
                listHangars.add(hangar);
                listBuilding.add(hangar);
            }

        }
        
        for(int i = 0; i< listBuilding.size();i++){
            listBuilding.get(i).setNeighbour(listBuilding);
        }
        
        listBuilding.get(0).setCapacity(1);
        
        this.iPanel.setBuildingsList(listBuilding);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.setStroke(PASSIVE_STROKE);
        for (int j = 0; j <= tail.length - 1; j++) {
            int coordX = listBuilding.get(tail[j]).getPosX() + (listBuilding.get(tail[j]).getWidth() / 2);
            int coordY = listBuilding.get(tail[j]).getPosY() + (listBuilding.get(tail[j]).getHeight() / 2);
            int coordXb = listBuilding.get(head[j]).getPosX() + (listBuilding.get(head[j]).getWidth() / 2);
            int coordYb = listBuilding.get(head[j]).getPosY() + (listBuilding.get(head[j]).getWidth() / 2);

            g.drawLine(coordX, coordY, coordXb, coordYb);
        }

        int fontSize = tWidth / 100;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        for (Building b : listBuilding) {
            b.draw(g);
        }
        g.setStroke(ACTIVE_STROKE);
    }


    public ArrayList<Building> docksList() {
        for (int l = 0; l < listBuilding.size(); l++) {
            if ("DOCK A".equals(listBuilding.get(l).getName()) | "DOCK B".equals(listBuilding.get(l).getName()) | "DOCK C".equals(listBuilding.get(l).getName()) | "DOCK D".equals(listBuilding.get(l).getName()) | "DOCK E".equals(listBuilding.get(l).getName())) {
                docksList.add(listBuilding.get(l));
            }
        }
        return docksList;
    }



    public void peopleQuitDocksInSpaceDirection() {
        for (int i = 0; i < docksList.size(); i++) {
            int randCap = var.randNum(0, docksList.get(i).getCurrentCapacity())/100;
            if (docksList.get(i).getCurrentCapacity()>randCap){    
            docksList.get(i).setCapacity(docksList.get(i).getCurrentCapacity() - randCap);
        }
            totalCapOut = totalCapOut + randCap;
    }
        double u = var.loinormale(30, 4);
        if (totalCapOut>=u){
            shipInSpaceDirection = true;
            totalCapOut = 0;
        }
    }

    public void peopleQuitBuildingInBuildingDirection() {
        for (int i = 0; i < listBuilding.size(); i++) {
            if (!"COMMAND ROOM".equals(listBuilding.get(i).getName()) | !"DOCK A".equals(listBuilding.get(i).getName()) | !"DOCK B".equals(listBuilding.get(i).getName()) | !"DOCK C".equals(listBuilding.get(i).getName()) | !"DOCK D".equals(listBuilding.get(i).getName()) | !"DOCK E".equals(listBuilding.get(i).getName())) {
                int randCap = var.randNum(0, listBuilding.get(i).getCurrentCapacity()) / 10;
                Random s = new Random();
                Building b = listBuilding.get(s.nextInt(listBuilding.size()));
                if (!"COMMAND ROOM".equals(b.getName()) | !"DOCK A".equals(b.getName()) | !"DOCK B".equals(b.getName()) | !"DOCK C".equals(b.getName()) | !"DOCK D".equals(b.getName()) | !"DOCK E".equals(b.getName())) {
                    if (b.getCurrentCapacity() + randCap < b.getMaxCapacity()) {
                        listBuilding.get(i).setCapacity(listBuilding.get(i).getCurrentCapacity() - randCap);
                        b.setCapacity(b.getCurrentCapacity() + randCap);
                    }
                }
            }
        }
    }

    public void peopleTraffic() {
        peopleQuitDocksInSpaceDirection();
        peopleQuitBuildingInBuildingDirection();
    }

    @Override
    public void update() {
        super.update();
        
        if (isSliding) {
            slideMap();
        }

        for (Building b : listBuilding) {
            Color colorBuilding = b.getColorBuilding();
            if (colorBuilding == Color.orange) {
                fireBuildingNumber++;
            }
        }
        if (fireBuildingNumber >= 2 * listBuilding.size() / 3) {
            gameOver = true;
        }
        fireBuildingNumber = 0;
    }

    public void slideMap() {
        if (detailBarOn && isSliding) {
            for (Building b : listBuilding) {
                if (b.getPosX() - this.rightBarWidth + rbWidth > b.getInitPosX() - (tWidth - (tWidth / 9)) / 10) { //(tWidth - (tWidth / 5)) / 10)
                    b.setPosX(b.getPosX() - this.rightBarWidth + rbWidth);
                }
            }
        } else {
            for (Building b : listBuilding) {
                if (b.getPosX() < b.getInitPosX()) {
                    b.setPosX(b.getPosX() - this.rightBarWidth + rbWidth);
                }
            }

        }
        rbWidth = this.rightBarWidth;
    }

    public ArrayList<Building> getListBuildings() {
        return listBuilding;
    }

    public ArrayList<Dock> getListHangars() {
        return listHangars;
    }
    
    public boolean getShipInSpaceDirection(){
        return this.shipInSpaceDirection;
}
    
    public void setShipInSpaceDirection(boolean b){
        this.shipInSpaceDirection = b;
    }
    
    public int getTotalCapOut(){
        return this.totalCapOut;
    }
    
}
