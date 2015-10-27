
package Actors;

import Main.Variables;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

public class Vehicle extends Actors{
    
    private final float GREEN_FIX = 1.0f;
    private Color _clrObject = new Color(0, GREEN_FIX, 0, 0.0f);
    
    private int nbPassenger,size;
    
    private Variables var;

    private Rectangle _rdrDetectRect;
    
    private boolean visible, isOnRadar;

    private float _objectX,_objectY,_objSpeedX,_objSpeedY,_objOpacity = 0;
    
    public Vehicle (String _name, int _nbPassenger, int _size, int _speed, float objectX, float objectY, float objSpeedX, float objSpeedY){
        super(_name);
        this.var = new Variables();
        this._objectX = objectX;
        this._objectY = objectY;
        this._objSpeedX = objSpeedX;
        this._objSpeedY = objSpeedY;
        
        this.visible = false;
        this.isOnRadar = false;
        
        _rdrDetectRect = new Rectangle(30, 30);
        this.nbPassenger = _nbPassenger;
        this.size = _size;
    }

    public void radarBeamCollisionCheck(Shape radarLine) {
        if (radarLine == null) {
            return;
        }
        
        
        if (radarLine.intersects(_rdrDetectRect)) {
            _objOpacity = GREEN_FIX;
            _clrObject = new Color(0.0f, GREEN_FIX, 0, _objOpacity);
        } else {
            _objOpacity -= 0.01;
            if (_objOpacity < 0) {
                _objOpacity = 0;
            }
            _clrObject = new Color(0.0f, GREEN_FIX, 0.0f, _objOpacity);
        }
    }
    
    public void draw(Graphics g) {
        _rdrDetectRect.setLocation((int) _objectX, (int) _objectY);
        if(!visible) g.setColor(_clrObject);
        else g.setColor(Color.GREEN);

        g.fillOval(_rdrDetectRect.x, _rdrDetectRect.y, _rdrDetectRect.width, _rdrDetectRect.height);
    }

    public void move() {
        _objectX += _objSpeedX;
        _objectY += _objSpeedY;
    }
    public float getObjectX() {return _objectX;}
    public float getObjectY() {return _objectY;}
    public boolean getVisible(){return this.visible;}
    public boolean getIsOnRadar(){return this.isOnRadar;}
    public int getNbPassenger(){return this.nbPassenger;}
    public int getSize(){return this.size;}
    
    public void setNbPassenger(int _nbPassenger){this.nbPassenger = _nbPassenger;}
    public void setSize(int _size){this.size = _size;}
    public void setVisible(boolean b){this.visible = b;}
    public void setIsOnRadar(boolean b){this.isOnRadar = b;}
    
}
