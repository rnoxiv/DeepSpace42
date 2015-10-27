package Radar;

import Utilities.Variables;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

public final class RadarObject {

    private final float GREEN_FIX = 1.0f;
    private Color _clrObject = new Color(0, GREEN_FIX, 0, 0.0f);
    
    private Variables var;
    private String name;
    
    private float _objectX;
    private float _objectY;

    private Rectangle _rdrDetectRect;
    
    private boolean visible, isVisible;
    
    private float _objOpacity = 0;

    private float _objSpeedX;
    private float _objSpeedY;

    public RadarObject(float objectX, float objectY, float objSpeedX, float objSpeedY) {
        this.var = new Variables();
        this._objectX = objectX;
        this._objectY = objectY;
        this._objSpeedX = objSpeedX;
        this._objSpeedY = objSpeedY;
        
        this.visible = false;
        this.isVisible = false;
        
        this.name = var.randomName();
        
        _rdrDetectRect = new Rectangle(30, 30);

    }

    public void radarBeamCollisionCheck(Shape radarLine) {
        if (radarLine == null) {
            return;
        }
        
        
        if (radarLine.intersects(_rdrDetectRect)) {
            _objOpacity = GREEN_FIX;
            _clrObject = new Color(0.0f, GREEN_FIX, 0, _objOpacity);
            isVisible = true;
        } else {
            _objOpacity -= 0.01;
            if (_objOpacity < 0) {
                _objOpacity = 0;
                isVisible = false;
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

    public float getObjectX() {
        return _objectX;
    }
    
    
    public String getName(){ return name;}
    
    public void move() {
        _objectX += _objSpeedX;
        _objectY += _objSpeedY;
    }
    
    public boolean getVisible(){return this.visible;}
    public boolean getIsVisible(){return this.isVisible;}
    public void setVisible(boolean b){this.visible = b;}
    public void setIsVisible(boolean b){this.isVisible = b;}
}
