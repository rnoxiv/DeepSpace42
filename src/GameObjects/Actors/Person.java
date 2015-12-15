/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects.Actors;
import GameObjects.Actor;
import GameObjects.Zones.Building;
import GameObjects.Zone;
import java.util.Random;

/**
 *
 * @author george
 */
public class Person extends Actor {
    
    // LISTE DES PRENOMS
    private final String[] firstNames = {"John", "Remy", "Andre", "Peter", "Simon", "Vincent", "Xzorb", "Mak"};
    // LISTE DES NOMS DE FAMILLE
    private final String[] lastNames = {"Smith", "DuClaire", "LeLand", "Thisse", "Pied", "Hunter", "McMillan"};
    // LE PRENOM DU PERSON
    private String firstName;
    // LE NOM DE FAMILLE DU PERSON
    private String lastName;
    // LA COLERE ALEATOIRE DE LA PERSON
    private int anger;
    // LE VAISSEAU DE LA PERSON
    private Ship ship;

    // UNE PERSONNE EST CONSTRUITE A PARTIR DE LA ZONE OU ELLE SETROUVE ET SON VAISSEAU
    public Person(Zone location,Ship s) {
        super(location);
        Random generator = new Random();
        this.firstName = this.firstNames[generator.nextInt(firstNames.length)];
        this.lastName = this.lastNames[generator.nextInt(lastNames.length)];
        this.anger = generator.nextInt(101);
        this.ship = s;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getColere() {
        return anger;
    }

    public void setColere(int colere) {
        this.anger = colere;
    }

    // DEPLACE UNE PERSONNE VERS UNE NOUVELLE ZONE SI IL Y A DE LA PLACE
    public boolean movePerson(Building destination) {
        if (destination.getActors().size() < destination.getMaxCapacity()) {
            this.getLocation().moveActor(this, destination);
            return true;
        }
        return false;
    }

    public Ship getVaisseau() {
        return ship;
    }

    public void setVaisseau(Ship vaisseau) {
        this.ship = vaisseau;
    }
    
    
}
