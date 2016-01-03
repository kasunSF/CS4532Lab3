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

    public synchronized void waitingRider() {
        ++waitingRiderCount;
    }

    public synchronized void boardRider() {
        --waitingRiderCount;
        ++boardedRiderCount;
    }

    public synchronized void clearBoardedCount() {
        boardedRiderCount = 0;
    }

    public int getWaitingRiderCount() {
        return waitingRiderCount;
    }

    public int getBoardedRiderCount() {
        return boardedRiderCount;
    }
}
