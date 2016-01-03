public class Rider implements Runnable {
    private int id;

    public Rider(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Simulation.riderMutex.acquire();
            Simulation.busHalt.waitingRider();
            System.out.println("Riders Waiting: " + Simulation.busHalt.getWaitingRiderCount());
            Simulation.riderMutex.release();

            Simulation.waitForBus.acquire();
            board();
            if (Simulation.busHalt.getWaitingRiderCount() == 0 || Simulation.busHalt.getBoardedRiderCount() == 50) {
                System.out.println("----------------------Riders Waiting: " + Simulation.busHalt.getWaitingRiderCount());
                System.out.println("----------------------Riders Boarded: " + Simulation.busHalt.getBoardedRiderCount());
                Simulation.busHalt.clearBoardedCount();
                Simulation.leaveBus.release();
            } else {
                Simulation.waitForBus.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void board() {
        Simulation.busHalt.boardRider();
        System.out.println(this.id + " boarded");
    }
}
