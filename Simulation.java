import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * This is the Main class which does the simulation. This holds 4 variables to
 * control synchronization.
 *
 * busMutex     Ensures that only one bus is in the halt and signals when leaving.
 * riderMutex   Ensures atomic increment of waiting count.
 * waitForBus   Riders will wait on this mutex when they are in the halt. Initially
 *              the bus will signal riders by releasing the waitForBus mutex. Then
 *              the riders acquire the mutex one by one, get in the bus and then
 *              releases.
 * leavesBus    Bus has to acquire leavesBus to leave from the halt. This will be
 *              released when riders are boarded.
 *
 */
public class Simulation {
    public static Semaphore busMutex = new Semaphore(1);
    public static Semaphore riderMutex = new Semaphore(1);
    public static Semaphore waitForBus = new Semaphore(0);
    public static Semaphore leaveBus = new Semaphore(0);

    public static BusHalt busHalt = new BusHalt();

    /*
     * Fixed size thread pool is initialized for both rider threads and bus threads to
     * overcome the OutOfMemoryError when the input size is large.
     */
    private static ExecutorService riderExecutor = Executors.newFixedThreadPool(1000);
    private static ExecutorService busExecutor = Executors.newFixedThreadPool(400);

    public static void main(String[] args) throws InterruptedException {
        int noRiders = 14300;
        int nuBuses = 386;

        try {
            if(args.length == 2) {
                noRiders = args[0] != null ? Integer.parseInt(args[0]) : noRiders;
                nuBuses = args[1] != null ? Integer.parseInt(args[1]) : nuBuses;
            }
        }catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        //generating riders
        generateRiders(noRiders);
        //generating buses
        buildBuses(nuBuses);

        //Shutting down the executors
        riderExecutor.shutdown();
        while (!riderExecutor.isTerminated()) {
        }
        busExecutor.shutdown();
        while (!busExecutor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    /**
     * Creates new rider threads and execute.
     * @param amount number of riders to generate
     */
    public static void generateRiders(int amount) {
        for (int i = 0; i < amount; ++i) {
            riderExecutor.execute(new Rider(i));
        }
    }

    /**
     * Creates new bus threads and execute.
     * @param amount number of buses to generate
     */
    public static void buildBuses(int amount) {
        for (int i = 0; i < amount; ++i) {
            busExecutor.execute(new Bus(i));
        }
    }
}
