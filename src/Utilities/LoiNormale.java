
package Utilities;

public class LoiNormale {
   
    static double alea(){
        double s = 0;
        for (int i = 0; i < 60; i++)
            s = s + Math.random();
        return s - 30;
    }
    
    static int nombre = 2;
    static int jets = 10;
    
    public static void main(String[] args){
        
        int AsteroidSize = 40;
        
        for (int j = 0; j < jets; j++){
            double somme, somcar;
            somme = somcar = 0;
            for (int i = 0; i < nombre; i++){
                double x = alea();
                somme = somme + x;
                somcar = somcar + x * x;
            }

            double moy = somme / nombre;
            double var = somcar / nombre - moy * moy;
            double resultat = AsteroidSize - moy*var;
            if (resultat < 0){
                resultat = resultat * -1;
            }
            System.out.println("resultat " + resultat);
        }
    }
}
