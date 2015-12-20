/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects.Actors;

import GameObjects.Actor;
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
    private final String firstName;
    // LE NOM DE FAMILLE DU PERSON
    private final String lastName;
    // LE VAISSEAU DE LA PERSON
    private final Ship ship;

    // UNE PERSONNE EST CONSTRUITE A PARTIR DE LA ZONE OU ELLE SETROUVE ET SON VAISSEAU
    public Person(Zone location, Ship s) {
        super(location);
        Random generator = new Random();
        this.firstName = this.firstNames[generator.nextInt(firstNames.length)];
        this.lastName = this.lastNames[generator.nextInt(lastNames.length)];
        this.ship = s;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Ship getShip() {
        return ship;
    }

}
