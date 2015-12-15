package GameObjects.Actors;

import GameObjects.Actor;
import GameObjects.Zone;
import java.util.Timer;
import java.util.TimerTask;

public class Urgence extends Actor {

    private String number;
    private boolean busy = false, started = false, done = false;
    private int time;
    private int used = 0;
    private static final int usedMax = 5;
    private int actualTime;

    private TimerTask taskPerformer;
    private Timer timer;

    public Urgence(Zone zone, String _number, int _time) {
        super(zone);
        this.number = _number;
        this.time = _time;

        timer = new Timer();

    }

    public void inService() {
        if (!started) {
            done = false;
            taskPerformer = new TimerTask() {
                @Override
                public void run() {
                    if (actualTime < time) {
                        actualTime++;
                        System.out.println("Urgence in action , time left : " + actualTime + " / " + time);
                    } else {
                        timer.cancel();
                        busy = !busy;
                        done = true;
                        addUsed();
                        actualTime = 0;
                        started = false;
                        timer = new Timer();
                    }
                }
            };

            timer.scheduleAtFixedRate(taskPerformer, 0, (1000 * (1 + used)));
            started = true;
        }

    }

    public String getNumber() {
        return this.number;
    }

    public boolean getBusy() {
        return this.busy;
    }
    
    public boolean getOnTheirWay() {
        return this.started;
    }

    public int getActualTime() {
        return this.actualTime;
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
