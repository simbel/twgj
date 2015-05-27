package twgj.ch4.modern;

import java.util.HashSet;
import java.util.Set;
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
        int wait = (int)(Math.random()*100);
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("Sub thread: " + ident + " has been initialized.");

        latch.countDown(); //initialize Node
    }

    public void run() {
        initialize();
    }

    final static int MAX_THREADS = 20;

    public static void main(String[] args) {
        final int quorum = 1 + (int) (MAX_THREADS / 2);
        final CountDownLatch cdl = new CountDownLatch(quorum);

        final Set<ProcessingThread> nodes = new HashSet<>();

        try {
            for (int i = 0; i < MAX_THREADS; i++) {
                ProcessingThread local = new ProcessingThread("localhost:" + (9000 + i), cdl);
                nodes.add(local);
                //System.out.println("Create thread [localhost:" + (9000 + i) + "] done");
                local.start();
            }
            cdl.await();
        } catch (InterruptedException e) {

        } finally {

        }
        System.out.println("Half " + MAX_THREADS + " threads have been started, System is ready to go...");
    }

}