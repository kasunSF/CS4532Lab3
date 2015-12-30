import java.util.concurrent.Semaphore;

/**
 * Created by Kasun on 12/30/2015.
 */
public class Main {
    public static void main(String[] args) {
        Semaphore mutex = new Semaphore(1);
        Semaphore bus = new Semaphore(0, true);//Guarantees first-come first-serve
    }
}
