import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * This is the Main class which does the simulation. This holds 4 variables to
 * control synchronization.
 *
 * busMutex     ensures that only one bus is in the halt and signals when leaving.
 * riderMutex   ensures atomic increment of waiting count.
 * waitForBus   //
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
    private static ExecutorService riderExecutor = Executors.newFixedThreadPool(80);
    private static ExecutorService busExecutor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        int noRiders = 10;
        int nuBuses = 1;

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
     * @param amount number of riders to generate
     */
    public static void buildBuses(int amount) {
        for (int i = 0; i < amount; ++i) {
            busExecutor.execute(new Bus(i));
        }
    }
}
