package objects;

import java.util.concurrent.Semaphore;

/**
 * Created by Kasun on 12/29/2015.
 */
public class Bus implements Runnable {
    private static Semaphore mutex;
    private static Semaphore bus;

    public Bus(Semaphore mutex, Semaphore bus) {
        this.mutex = mutex;
        this.bus = bus;
    }

    @Override
    public void run() {
        //Arrive to bus stop
        //Notify
        //Depart if all the riders have boarded or bus is full
    }

    private void depart() {
        System.out.println("objects.Bus departed");
    }
}
