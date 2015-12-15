package Interface.Main;

import GameObjects.Ressource;
import GameObjects.Zones.Building;
import GameObjects.Zones.Buildings.Dock;
import Interface.MainPanel;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class RessourcesView extends MainPanel {

    private int rbWidth = 0;

    private ArrayList<Ressource> listRessources;
    private ArrayList<Building> listPop;
    private ArrayList<Dock> listDocks;

    private float currentHangar;

    private boolean shieldOn = false, purge = false;

    private Integer command = null;

    private int missile;

    private static final String IPANELNAME = "Ressources";

    public RessourcesView(String n, int sW, int mW, int tH, int tW, String sound, ArrayList<Building> l, ArrayList<Dock> h) {
        super(n, sW, mW, tW, tH, Ressource.class, IPANELNAME, sound);
        listPop = l;
        listDocks = h;
        init();
        this.iPanel.setRessourcesList(listRessources);
    }

    private void init() {

        int popMax = 0;
        for (int i = 0; i < listPop.size(); i++) {
            popMax = popMax + listPop.get(i).getMaxCapacity();
        }
        listRessources = new ArrayList();
        listRessources.add(new Ressource("POPULATION", popMax, 0, sWidth + width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("WATER", 50000, 50000, sWidth + 2 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("FOOD", 20000, 20000, sWidth + 3 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("OXYGENE", 10000, 10000, sWidth + 5 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("ELECTRICITY", 50000, 50000, sWidth + 6 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("GAS", 50000, 50000, sWidth + 7 * width / 9, height / 5, width, height - 2 * topHeight));
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        for (Ressource listRessource : listRessources) {
            listRessource.draw(g);
        }
    }

    @Override
    public void update() {
        super.update();
        int curPop = 0;
        for (int i = 0; i < this.listPop.size(); i++) {
            curPop += this.listPop.get(i).getCurrentCapacity();
        }
        if (curPop <= 1) {
            gameOver = true;
        }
        currentHangar = 0;
        for (int i = 0; i < listDocks.size(); i++) {
            for (int j = 0; j < listDocks.get(i).getShips().size(); j++) {
                currentHangar += (float) listDocks.get(i).getShips().get(j).getVolume();
            }
        }
        listRessources.get(0).setCurrentcap(curPop);
        for (int i = 0; i < this.listRessources.size(); i++) {
            evolve(i);
            listRessources.get(i).coolDown();
            if (listRessources.get(i).getCreateShip()) {
                command = i;
            }
        }
        if (isSliding) {
            slideMap();
        }
    }

    public void evolve(int i) {

        switch (this.listRessources.get(i).getName()) {
            case "POPULATION":
                break;
            case "WATER":
                float currentNewW = (listRessources.get(0).getCurrentcap()) / 50000;
                float newValueW = listRessources.get(1).getCurrentcap() - currentNewW;
                listRessources.get(1).setCurrentcap(newValueW);
                break;
            case "GAS":
                float missCons = 0;
                missCons = 5000 * (float) missile;
                float currentNewE = (currentHangar) / 50000;
                float newValueE = listRessources.get(5).getCurrentcap() - currentNewE - missCons;
                listRessources.get(5).setCurrentcap(newValueE);
                missile = 0;
                break;
            case "ELECTRICITY":
                float shieldCons = 0;
                if (shieldOn) {
                    shieldCons = 10;
                }
                float currentNewElec = (listRessources.get(0).getCurrentcap()) / 100000;
                float newValueElec = listRessources.get(4).getCurrentcap() - currentNewElec - shieldCons;
                listRessources.get(4).setCurrentcap(newValueElec);
                break;
            case "FOOD":
                float currentNewN = (listRessources.get(0).getCurrentcap()) / 100000;
                float newValueN = listRessources.get(2).getCurrentcap() - currentNewN;
                listRessources.get(2).setCurrentcap(newValueN);
                break;
            case "OXYGENE":
                float purgeNeg = 0;
                if (purge) {
                    purgeNeg = listRessources.get(3).getMaxcap() / listPop.size();
                    purge = false;
                }
                float currentNewO = (listRessources.get(0).getCurrentcap()) / 500000;
                float newValueO = listRessources.get(3).getCurrentcap() - currentNewO - purgeNeg;
                purgeNeg = 0;
                listRessources.get(3).setCurrentcap(newValueO);
                break;
        }
    }

    public ArrayList<Ressource> getListRessources() {
        return listRessources;
    }

    public void slideMap() {
        if (detailBarOn && isSliding) {
            for (int i = 0; i < listRessources.size() - 3; i++) {
                if (listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth > listRessources.get(i).getInitPosX() - (tWidth - (tWidth / 10)) / 50) {
                    listRessources.get(i).setPosx(listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth);
                }
            }
            for (int i = listRessources.size() - 3; i < listRessources.size(); i++) {
                if (listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth > listRessources.get(i).getInitPosX() - (tWidth - (tWidth / 5)) / 7) {
                    listRessources.get(i).setPosx(listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth);
                }
            }
        } else {
            for (Ressource listRessource : listRessources) {
                if (listRessource.getPosx() < listRessource.getInitPosX()) {
                    listRessource.setPosx(listRessource.getPosx() - this.rightBarWidth + rbWidth);
                }
            }

        }
        rbWidth = this.rightBarWidth;
    }

    public void setListPopulation(ArrayList<Building> l) {
        this.listPop = l;
    }

    public void setShieldOn(boolean b) {
        this.shieldOn = b;
    }

    public void setPurge(boolean b) {
        this.purge = b;
    }

    public void setMissile(int _m) {
        this.missile = _m;
    }

    public Integer getCommand() {
        return command;
    }

    public void resetCommand() {
        listRessources.get(command).resetCreateShip();
        command = null;
    }
}
