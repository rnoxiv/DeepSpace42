package Interface.Main;

import GameObjects.Ressource;
import Interface.MainPanel;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class RessourcesView extends MainPanel {
    
    private int rbWidth = 0;
    
    private ArrayList<Ressource> listRessources;

    private static final String IPANELNAME = "Ressources";

    public RessourcesView(String n, int sW, int mW, int tH, int tW, String sound) {
        super(n, sW, mW, tW, tH, Ressource.class, IPANELNAME, sound);
        init();
    }

    public void init() {
        listRessources = new ArrayList();
        listRessources.add(new Ressource("POPULATION", 1800, 0, sWidth + width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("EAU", 1000, 450, sWidth + 2 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("NOURITURE", 1800, 100, sWidth + 3 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("OXYGENE", 1800, 1800, sWidth + 5 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("ESSENCE", 1800, 1400, sWidth + 6 * width / 9, height / 5, width, height - 2 * topHeight));
        listRessources.add(new Ressource("ELECTRICITE", 1800, 1800, sWidth + 7 * width / 9, height / 5, width, height - 2 * topHeight));

        System.out.println(listRessources.size());
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        for (int i = 0; i < listRessources.size(); i++) {
            listRessources.get(i).draw(g);
        }
    }

    @Override
    public void update() {
        super.update();
        
        if (isSliding) {
            slideMap();
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
            for (int i = 0; i < listRessources.size(); i++) {
                if (listRessources.get(i).getPosx() < listRessources.get(i).getInitPosX()) {
                    listRessources.get(i).setPosx(listRessources.get(i).getPosx() - this.rightBarWidth + rbWidth);
                }
            }

        }
        rbWidth = this.rightBarWidth;
    }

}
