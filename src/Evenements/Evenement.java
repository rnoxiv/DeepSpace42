package Evenements;

public abstract class Evenement {
 
    public Evenement(){
        
    }
    
    private int ecartType;
    private int moyenne;
    static final int facteur = 2;
    private double somme = 0, somcar = 0;

    public double alea(int _ecartType){
        double s = 0;
        for (int i = 0; i < _ecartType; i++)
            s = s + Math.random();
        return s - _ecartType/2;
    }
    
    public double loinormale(int moyenne,int _ecartType){
        for (int i = 0; i < facteur; i++){
            double x = alea(_ecartType);
            somme = somme + x;
            somcar = somcar + x * x;
        }

        double moy = somme / facteur;
        double var = somcar / facteur - moy * moy;
        double resultat = moyenne - moy*var;
        if (resultat < 0){
            resultat = resultat * -1;
        }
        return resultat;
    } 
    
}
