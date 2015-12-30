package objects;

import java.util.concurrent.Semaphore;

/**
 * Created by Kasun on 12/29/2015.
 */
public class Rider implements Runnable {
    private int id;
    private static Semaphore mutex;
    private static Semaphore bus;

    public Rider(Semaphore mutex, Semaphore bus) {
        this.mutex = mutex;
        this.bus = bus;
    }

    public Rider(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        //Wait for a bus
        //Get in if there's a bus
    }

    public int getID() {
        return id;
    }

    private void board() {
        System.out.println(getID() + " boarded");
    }
}
