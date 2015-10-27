
package Actors;

public abstract class Actors {
    
    private String name;
    
    public Actors (String _name){
        this.name = _name;
    }
 
    public String getName(){
        return this.name;
    }
    
    public void setName(String _name){
        this.name = _name;
    }
    
}

