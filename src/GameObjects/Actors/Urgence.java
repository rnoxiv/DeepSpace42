package GameObjects.Actors;

import GameObjects.Actor;
import GameObjects.Zone;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Urgence extends Actor {

    private String number;
    private boolean busy = false;
    private int time;
    private int used = 0;
    private static final int usedMax = 10;
    private int actualTime;

    private ActionListener taskPerformer;
    private Timer timer;

    public Urgence(Zone zone, String _number, int _time) {
        super(zone);
        this.number = _number;
        this.time = _time;

        taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                actualTime++;
                System.out.println("Urgence in action , time left : " + actualTime + " / " + time);
            }
        };

        timer = new Timer(1000 * (5 + used), taskPerformer);

    }

    public void inService() {
        timer = new Timer(1000 * (5 + used), taskPerformer);
        timer.start();
        if (this.actualTime >= this.time) {
            timer.stop();
            busy = !busy;
            addUsed();
            actualTime = 0;
        }
    }

    private int getUsed() {
        return this.used;
    }

    public String getNumber() {
        return this.number;
    }

    public boolean getBusy() {
        return this.busy;
    }

    public int getTime() {
        return this.time;
    }

    private void addUsed() {
        if (used < usedMax) {
            used++;
        } else {
            used = usedMax;
        }
    }

    public void setNumber(String _number) {
        this.number = _number;
    }

    public void setBusy(boolean _busy) {
        this.busy = _busy;
    }

    public void setTime(int _time) {
        this.time = _time;
    }

}
