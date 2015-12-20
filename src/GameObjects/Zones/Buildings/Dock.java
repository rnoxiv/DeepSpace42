/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author george
 */
package GameObjects.Zones.Buildings;

import GameObjects.Actors.Ship;
import GameObjects.Zones.Building;
import java.util.ArrayList;

public class Dock extends Building {
    // VAISSEAUX DANS LE HANGAR
    private ArrayList<Ship> ships;
    
    /**
     * ATTENTION ICI LA CAPACITE shipCAPACITY EST LA CAPACITE EN METRES CUBES
     */
    
    public Dock(String name, int shipCapacity, int x, int y, int w, int h, ArrayList<Building> neighbours) {
        super(name, shipCapacity, x, y, "Hangar", w, h, neighbours);
        this.ships = new ArrayList();
    }
    
    public ArrayList<Ship> getShips() {
        return ships;
    }
    
    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
    
    public void addShips(Ship s){
        this.ships.add(s);
    }
    
    public void delShips(Ship s){
        this.ships.remove(s);
    }
    
    // RENVOIE LE MEILLEUR HALL D'ARRIVEE
    public Building findBestHall() {
        ArrayList<Building> hallsPossibles = new ArrayList();
        for (Building b : this.getNeighbours()) {
            if ("ARRIVALS/DEPARTURES".equals(b.getName())) {
                hallsPossibles.add(b);
            }
        }
        if (hallsPossibles.size() > 0) {
            Building bestHall = hallsPossibles.get(0);
            for (Building b : hallsPossibles) {
                if ((b.getMaxCapacity() - b.getNumberOfActors()) > bestHall.getNumberOfActors()) {
                    bestHall = b;
                }
            }
            return bestHall;
        } else {
            return null;
        }
    }
}
