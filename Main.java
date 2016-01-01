import objects.Bus;
import objects.BusHalt;
import objects.Rider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    static Semaphore busMutex = new Semaphore(1);
    static Semaphore riderMutex = new Semaphore(1);
    static Semaphore waitForBus = new Semaphore(0, true);//Guarantees first-come first-serve
    static Semaphore leaveBus = new Semaphore(0);

    static BusHalt busHalt = new BusHalt();

    static ExecutorService riderExecutor = Executors.newFixedThreadPool(80);
    static ExecutorService busExecutor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        generateRiders(188994);
        buildBuses(5000);
        riderExecutor.shutdown();
        while (!riderExecutor.isTerminated())
        {
        }
        busExecutor.shutdown();
        while (!busExecutor.isTerminated())
        {
        }
        System.out.println("Finished all threads");
    }

    public static void generateRiders(int amount) {
        for (int i = 0; i < amount; ++i)
        {
            riderExecutor.execute(new Rider(i, busHalt, riderMutex, waitForBus, leaveBus));
        }
    }

    public static void buildBuses(int amount) {
        for (int i = 0; i < amount; ++i)
        {
            busExecutor.execute(new Bus(i, busHalt, busMutex, waitForBus, leaveBus));
        }
    }

    public static void buildBus() {
        busExecutor.execute(new Bus(0, busHalt, busMutex, waitForBus, leaveBus));
    }
}
