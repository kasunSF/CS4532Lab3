import java.util.Random;
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
    private static ExecutorService riderExecutor = Executors.newFixedThreadPool(888);
    private static ExecutorService busExecutor = Executors.newFixedThreadPool(48);

    public static void main(String[] args) throws InterruptedException {
        int noRiders = 14300;

        try
        {
            if (args.length == 1)
            {
                noRiders = args[0] != null ? Integer.parseInt(args[0]) : noRiders;
            }
        } catch (NumberFormatException e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        //Randomly generate riders and buses
        randomGenerate(noRiders);

        //Shutting down the executors
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

    /**
     * Generates riders and buses randomly.
     * This method generates buses until all the riders have boarded.
     *
     * @param riders number of riders to generate
     */
    public static void randomGenerate(int riders) {
        int generatedRiders = 0;

        Random random = new Random();
        int randomNum;
        //Generate riders and buses until all the riders have generated or none of the riders are waiting for a bus
        while (generatedRiders < riders || busHalt.getWaitingRiderCount() > 0)
        {
            //If random number is 0 and there are more riders to be generated, generate a random number of riders
            if (random.nextInt(2) == 0 && generatedRiders < riders)
            {
                randomNum = random.nextInt(34);//Generate a random number between 0(inclusive) and 34(exclusive)
                /*
                If generatedRiders + randomNum is greater than required number of riders,
                generate only required number of riders.
                 */
                if ((generatedRiders + randomNum) > riders)
                {
                    randomNum = riders - generatedRiders;
                }
                generatedRiders += randomNum;
                generateRiders(randomNum);//Generate riders
            }

            //If random number is 1 and there are waiting riders at busHalt, generate a random number of buses
            else if (random.nextInt(5) == 1 && busHalt.getWaitingRiderCount() > 0)
            {
                randomNum = random.nextInt(5);//Generate a random number between 0(inclusive) and 5(exclusive)
                buildBuses(randomNum);//Build buses

                try
                {
                    Thread.sleep(4);// To reduce overbuilding buses
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Creates new rider threads and execute.
     *
     * @param amount number of riders to generate
     */
    public static void generateRiders(int amount) {
        for (int i = 0; i < amount; ++i)
        {
            riderExecutor.execute(new Rider(i));
        }
    }

    /**
     * Creates new bus threads and execute.
     *
     * @param amount number of buses to generate
     */
    public static void buildBuses(int amount) {
        for (int i = 0; i < amount; ++i)
        {
            busExecutor.execute(new Bus(i));
        }
    }
}
