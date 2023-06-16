package project1;

import java.util.Timer;
import java.util.TimerTask;

public class timer {
    public static int time = configuration.NodeDuration * (-1);
    public static Timer timer = new Timer();
    public static TimerTask task;
    static boolean IsRunning = false;

    public static void StartAndStop() {
        if (IsRunning == false) {
            timer.purge();
            if (task == null) {
                task = new TimerTask() {
                    public void run() {
                        time++;
                    }
                };
                timer.scheduleAtFixedRate(task, 1, 1);
            }
            IsRunning = true;
        } else {
            if (task != null) {
                task.cancel();
                task = null;
            }
            IsRunning = false;
        }
    }

    public static void reset() {
        if (IsRunning == true) {
            if (task != null) {
                task.cancel();
                task = null;
            }
            IsRunning = false;
        }
        time = configuration.NodeDuration * (-1);
    }
}
