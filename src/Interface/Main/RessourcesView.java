package Interface.Main;

import GameObjects.Ressource;
import GameObjects.Zones.Building;
import Interface.MainPanel;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class RessourcesView extends MainPanel {
    
    private int rbWidth = 0;
    
    
    private ArrayList<Ressource> listRessources;
    private ArrayList<Building> listPop;
    
    private float currentHangar;
    
    private boolean shieldOn=false;
    
    private static final String IPANELNAME = "Ressources";

    public RessourcesView(String n, int sW, int mW, int tH, int tW, String sound,ArrayList<Building> l) {
        super(n, sW, mW, tW, tH, Ressource.class, IPANELNAME, sound);
        listPop = l;
        init();
        this.iPanel.setRessourcesList(listRessources);
    }

    private void init() {
        
        int popMax=0;
        for (int i=0; i<this.listPop.size()-5;i++){
            popMax = popMax + this.listPop.get(i).getMaxCapacity();
        }
        listRessources = new ArrayList();
        listRessources.add(new Ressource("POPULATION", popMax, popMax/2, sWidth + width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("EAU", 50000, 50000, sWidth + 2 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("NOURRITURE", 20000, 20000, sWidth + 3 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("OXYGENE", 10000, 10000, sWidth + 5 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("ELECTRICITE", 50000, 50000, sWidth + 6 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("ESSENCE", 50000, 50000, sWidth + 7 * width / 9, height / 5, width, height - 2 * topHeight));
        //System.out.println(listRessources.size());
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
        int curPop =0;
        for (int i=0; i<this.listPop.size()-5;i++){
            curPop += this.listPop.get(i).getCurrentCapacity();
        }
        currentHangar=0;
        for (int i=19; i<this.listPop.size();i++){
            currentHangar += this.listPop.get(i).getCurrentCapacity();
        }
        listRessources.get(0).setCurrentcap(curPop);
        for (int i=0; i<this.listRessources.size();i++){
            evolve(i);
        }
        if (isSliding) {
            slideMap();
        }
    }
    
    public void evolve(int i){
        
        
            switch(this.listRessources.get(i).getName()){
                case "POPULATION":
                    break;
                case "EAU":
                    float currentNewW =(listRessources.get(0).getCurrentcap())/50000;
                    float newValueW =listRessources.get(1).getCurrentcap() - currentNewW;
                    listRessources.get(1).setCurrentcap(newValueW);
                    break;
                case "ESSENCE":
                    float currentNewE =(currentHangar)/200000;
                    float newValueE =listRessources.get(5).getCurrentcap() - currentNewE;
                    listRessources.get(5).setCurrentcap(newValueE);
                    break;
                case "ELECTRICITE":
                    float shieldCons =0;
                    if (shieldOn){
                        shieldCons = 10;
                    }
                    float currentNewElec =(listRessources.get(0).getCurrentcap())/100000;
                    float newValueElec =listRessources.get(4).getCurrentcap() - currentNewElec-shieldCons;
                    listRessources.get(4).setCurrentcap(newValueElec);
                    break;
                case "NOURRITURE":
                    float currentNewN =(listRessources.get(0).getCurrentcap())/100000;
                    float newValueN =listRessources.get(2).getCurrentcap() - currentNewN;
                    listRessources.get(2).setCurrentcap(newValueN);
                    break;
                case "OXYGENE":
                    float currentNewO =(listRessources.get(0).getCurrentcap())/500000;
                    float newValueO =listRessources.get(3).getCurrentcap() - currentNewO;
                    listRessources.get(3).setCurrentcap(newValueO);
                    break;
            }
        }
    
    public ArrayList<Ressource> getListRessources() {
        return listRessources;
    }

    public void slideMap() {
        if (detailBarOn && isSliding) {
            for (int i = 0; i < listRessources.size()-3; i++) {
                if (listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth > listRessources.get(i).getInitPosX() - (tWidth - (tWidth / 10)) / 50) {
                    listRessources.get(i).setPosx(listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth);
                }
            }for (int i = listRessources.size()-3; i < listRessources.size(); i++){
                if (listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth > listRessources.get(i).getInitPosX() - (tWidth - (tWidth / 5))/7) {
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
    
    public void setListPopulation(ArrayList<Building> l){this.listPop = l;}
    
    public void setShieldOn(boolean b){this.shieldOn = b;}

}

