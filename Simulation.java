import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * This is the Main class which does the simulation. This holds
 */
public class Simulation {
    public static Semaphore busMutex = new Semaphore(1);
    public static Semaphore riderMutex = new Semaphore(1);
    public static Semaphore waitForBus = new Semaphore(0);
    public static Semaphore leaveBus = new Semaphore(0);

    public static BusHalt busHalt = new BusHalt();

    private static ExecutorService riderExecutor = Executors.newFixedThreadPool(80);
    private static ExecutorService busExecutor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        generateRiders(130);
        buildBuses(3);
        riderExecutor.shutdown();
        while (!riderExecutor.isTerminated()) {
        }
        busExecutor.shutdown();
        while (!busExecutor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    public static void generateRiders(int amount) {
        for (int i = 0; i < amount; ++i) {
            riderExecutor.execute(new Rider(i));
        }
    }

    public static void buildBuses(int amount) {
        for (int i = 0; i < amount; ++i) {
            busExecutor.execute(new Bus(i));
        }
    }
}
