package Evenements;

import Utilities.Variables;
import java.awt.Color;

//CREE UN ASTEROID DE TAILLE ALEATOIRE!
public class AsteroidIncoming{
    private final Variables var = new Variables();
    public AsteroidIncoming(){
        launch();
    }
    
    private final double rayonAsteroid = var.loinormale(120,100);
    private Color colorAsteroid;
    
    private void launch(){
        colorAsteroid = Color.red;
    }
    
    public double getRayonAsteroid(){
        return this.rayonAsteroid;
    }
    
    public Color getColorAsteroid(){
        return this.colorAsteroid;
    }
    
}
