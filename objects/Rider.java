package objects;

import java.util.concurrent.Semaphore;

public class Rider implements Runnable {
    private int id;
    private static Semaphore mutex;
    private static Semaphore waitForBus;
    private static Semaphore leaveBus;

    private BusHalt busHalt;

    public Rider(int id, BusHalt busHalt, Semaphore riderMutex, Semaphore waitForBus, Semaphore leaveBus) {
        this.id = id;
        this.busHalt = busHalt;
        this.mutex = riderMutex;
        this.waitForBus = waitForBus;
        this.leaveBus = leaveBus;
    }

    @Override
    public void run() {
        try
        {
            mutex.acquire();
            busHalt.waitingRider();
            System.out.println("Riders Waiting: " + busHalt.getWaitingRiderCount());
            mutex.release();

            waitForBus.acquire();
            board();
            if (busHalt.getWaitingRiderCount() == 0 || busHalt.getBoardedRiderCount() == 50)
            {
                System.out.println("----------------------Riders Waiting: " + busHalt.getWaitingRiderCount());
                System.out.println("----------------------Riders Boarded: " + busHalt.getBoardedRiderCount());
                busHalt.clear();
                leaveBus.release();
            }
            else
            {
                waitForBus.release();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public int getID() {
        return id;
    }

    private void board() {
        busHalt.boardRider();
        System.out.println(getID() + " boarded");
    }
}
