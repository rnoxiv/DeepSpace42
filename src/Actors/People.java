
package Actors;

public class People extends Actors {
    
    private int anger;
    private int age;
    
    public People (String _name, int _anger, int _age){
        super(_name);
        this.anger = _anger;
        this.age = _age;
    }
    
    public int getAnger(){
        return this.anger;
    }
    
    public int getAge(){
        return this.age;
    }
    
    public void setAnger(int _anger){
        this.anger = _anger;
    }
        
    public void setAge(int _age){
        this.age = _age;
    }
    
}
