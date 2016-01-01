package objects;

import java.util.concurrent.Semaphore;

public class Bus implements Runnable {
    private int id;
    private static Semaphore mutex;
    private static Semaphore waitForBus;
    private static Semaphore leaveBus;

    private BusHalt busHalt;

    public Bus(int id, BusHalt busHalt, Semaphore busMutex, Semaphore waitForBus, Semaphore leaveBus) {
        this.id = id;
        this.busHalt = busHalt;
        this.mutex = busMutex;
        this.waitForBus = waitForBus;
        this.leaveBus = leaveBus;
    }

    @Override
    public void run() {
        try
        {
            mutex.acquire();
            if (busHalt.getWaitingRiderCount() > 0)
            {
                waitForBus.release();
                leaveBus.acquire();
            }
            mutex.release();
            depart();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public int getID() {
        return id;
    }

    private void depart() {
        System.out.println(getID() + " Bus departed");
    }
}
