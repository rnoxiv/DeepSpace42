package GameObjects;




public abstract class Actor {
    
    // L'EMPLACEMENT DE L'ACTOR
    private Zone location;

    public Actor(Zone location) {
        this.location = location;
    }
    
    public Zone getLocation() {
        return location;
    }

    public void setLocation(Zone location) {
        this.location = location;
    }

}

