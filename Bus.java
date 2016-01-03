/**
 * This class simulates a Bus.
 */
public class Bus implements Runnable {
    private int id;

    public Bus(int id) {
        this.id = id;
    }

    /*
        First the bus acquires the busMutex and then if there are no riders waiting, releases the
        mutex and leave. If there are riders waiting, bus acquires the riderMutex and releases the
        waitForBus mutex. Then the bus waits on leaveBus mutex. Then the bus releases the riderMutex
        and the busMutex and departs.

        The bus acquiring the riderMutex will guarantee that only the riders who were waiting before
        the bus arrives gets the chance to board. Others have to wait for the next ride.
     */
    @Override
    public void run() {
        try
        {
            Simulation.busMutex.acquire();
            if (Simulation.busHalt.getWaitingRiderCount() > 0)
            {
                Simulation.riderMutex.acquire();
                Simulation.waitForBus.release();
                Simulation.leaveBus.acquire();
                Simulation.riderMutex.release();
            }
            Simulation.busMutex.release();
            depart();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    private void depart() {
        System.out.println(this.id + " Bus departed.");
    }
}
