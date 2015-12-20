package GameObjects;

import java.util.ArrayList;

/**
 *
 * @author george
 */
public class Zone {

    protected ArrayList<Actor> actors; // LA LISTE DES ACTORS DANS LA ZONE
    protected String name; // LE NOM DE LA ZONE

    public Zone(String pname) {
        actors = new ArrayList();
        this.name = pname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    // RENVOIE UN ACTOR EN FONCTION DE SON INDEX DANS actors
    public Actor getActor(int index) {
        try {
            return this.actors.get(index);
        } catch (Exception e) {
            System.out.println("Index trop Ã©levÃ©.");
            return null;
        }
    }
    //AJOUTE UN ACTOR A LA ZONE
    public boolean addActor(Actor actor) {
        if (this.actors.contains(actor)) {
            System.out.println("Cet actor est dÃ©jÃ  prÃ©sent.");
            return false;
        } else {
            this.actors.add(actor);
            return true;
        }
    }

    //SUPPRIME UN ACTOR DE LA ZONE EN FONCTION DE SON INDEX DANS actors
    public void removeActor(int index) {
        try {
            this.actors.remove(index);
        } catch (Exception e) {
            System.out.println("Index trop Ã©levÃ©.");
        }
    }
    //SUPPRIME UN ACTOR DE LA ZONE
    public void removeActor(Actor actor) {
        try {
            this.actors.remove(actor);
        } catch (Exception e) {
            System.out.println("Cet objet n'existe pas actuellement dans cette liste.");
        }
    }
    
    //DEPLACE UN ACTOR VERS UNE NOUVELLE ZONE
    public void moveActor(Actor actor, Zone newZone) {
        this.removeActor(actor);
        newZone.addActor(actor);
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }
}
