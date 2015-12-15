package Evenements;

import Utilities.Variables;
import java.awt.Color;


public class AsteroidIncoming{
    private Variables var = new Variables();
    public AsteroidIncoming(){
        launch();
    }
    
    private double rayonAsteroid = var.loinormale(120,100);
    private Color colorAsteroid;
    
    public void launch(){
        colorAsteroid = Color.red;
    }
    
    public double getRayonAsteroid(){
        return this.rayonAsteroid;
    }
    
    public Color getColorAsteroid(){
        return this.colorAsteroid;
    }
    
}
