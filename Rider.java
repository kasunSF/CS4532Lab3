/**
 * This class simulates a Rider.
 */
public class Rider implements Runnable {
    private int id;

    public Rider(int id) {
        this.id = id;
    }

    /**
     * When the rider arrives at the bus stop, he acquire the rider mutex, then increment the waiting
     * rider count and then release the mutex. Then the writers are waiting on the waitForBus and once
     * the bus signals who are waiting on the waitForBus, one of the riders acquires the waitForBus and
     * gets in to the bus. If the bus is filled or there's no one waiting in the halt, rider release the
     * leaveBus mutex. Else rider signals the waitForBus in order to wake up another rider who are waiting
     * on the waitForBus.
     */
    @Override
    public void run() {
        try
        {
            Simulation.riderMutex.acquire();
            Simulation.busHalt.waitingRider();
            //System.out.println("Riders Waiting: " + Simulation.busHalt.getWaitingRiderCount());
            Simulation.riderMutex.release();

            Simulation.waitForBus.acquire();
            board();
            if (Simulation.busHalt.getWaitingRiderCount() == 0 || Simulation.busHalt.getBoardedRiderCount() == 50)
            {
                System.out.println("---------------------- Riders Waiting: " + Simulation.busHalt.getWaitingRiderCount());
                System.out.println("---------------------- Riders Boarded: " + Simulation.busHalt.getBoardedRiderCount());
                Simulation.busHalt.clearBoardedCount();
                Simulation.leaveBus.release();
            }
            else
            {
                Simulation.waitForBus.release();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    private void board() {
        Simulation.busHalt.boardRider();
        System.out.println(this.id + " boarded");
    }
}
