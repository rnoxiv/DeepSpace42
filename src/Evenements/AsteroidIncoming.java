package Evenements;

import java.awt.Color;


public class AsteroidIncoming extends Evenement{
    
    public AsteroidIncoming(){
        launch();
    }
    
    private double rayonAsteroid = loinormale(120,100);
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
