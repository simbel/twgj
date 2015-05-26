package twgj.ch4.modern;

import java.util.concurrent.CountDownLatch;

/**
 * Created by pramodh on 3/13/14.
 */
public class ProcessingThread extends Thread {
    private final String ident;
    private final CountDownLatch latch;

    public ProcessingThread(String ident, CountDownLatch latch) {
        this.ident = ident;
        this.latch = latch;
    }

    public String getIdent() {
        return ident;
    }

    public void initialize() {
        latch.countDown(); //initialize Node
    }

    public void run() {
        initialize();
    }
}