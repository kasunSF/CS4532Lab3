/**
 * This class simulates a BusHalt.
 */
public class BusHalt {
    private int waitingRiderCount;
    private int boardedRiderCount;

    public BusHalt() {
        this.waitingRiderCount = 0;
        this.boardedRiderCount = 0;
    }

    /**
     * Increment waiting rider count when a rider arrived to the bus halt.
     */
    public synchronized void waitingRider() {
        ++waitingRiderCount;
    }

    /**
     * Decrement waiting rider count and increment boarded rider count when a rider boarded to the bus.
     */
    public synchronized void boardRider() {
        --waitingRiderCount;
        ++boardedRiderCount;
    }

    /**
     * Clear boarded riders count once the bus left the halt.
     */
    public synchronized void clearBoardedCount() {
        boardedRiderCount = 0;
    }

    /**
     *
     * @return How many rider are currently waiting for the bus.
     */
    public int getWaitingRiderCount() {
        return waitingRiderCount;
    }

    /**
     *
     * @return Number of riders who boarded to the bus arrived to the halt
     */
    public int getBoardedRiderCount() {
        return boardedRiderCount;
    }
}
