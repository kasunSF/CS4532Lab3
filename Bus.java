public class Bus implements Runnable {
    private int id;

    public Bus(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Simulation.busMutex.acquire();
            if (Simulation.busHalt.getWaitingRiderCount() > 0) {
                Simulation.waitForBus.release();
                Simulation.leaveBus.acquire();
            }
            Simulation.busMutex.release();
            depart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void depart() {
        System.out.println(this.id + " Bus departed.");
    }
}
