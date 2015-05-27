package twgj.ch4.old;

/**
 * Created by dliu2 on 2015/5/26.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MicroBlogNode {
    private final String identifier;

    private final Map<Update, Long> arrivalTime = new HashMap<>();

    private final Lock lock = new ReentrantLock();

    public MicroBlogNode(String identifier) {
        this.identifier = identifier;
    }

    // All Synchronized methods
    public synchronized String getIdentifier() {
        return this.identifier;
    }

    public void propagateUpdate(Update update, MicroBlogNode backup) {
        boolean acquired = false;
        boolean done = false;

        while (!done) {

            int wait = (int) (Math.random() * 10);
            try {
                acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
                if (acquired) {
                    System.out.println(identifier + ": recvd: " + update.getUpdateText() + "; backup: " + backup.getIdentifier());
                    done = backup.confirmUpdateReceived(this, update);
                }
            } catch (InterruptedException e) {
            } finally {
                if (acquired) {
                    lock.unlock();
                }
                if (!done) {
                    try {
                        System.out.println(identifier + ": abort: " + update.getUpdateText() + "; backup: " + backup.getIdentifier());
                        Thread.sleep(wait);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean confirmUpdateReceived(MicroBlogNode other, Update update) {
        boolean acquired = false;

        try {
            int wait = (int) (Math.random() * 10);
            acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
            if (acquired) {
                System.out.println(identifier + ": recvd confirm : " + update.getUpdateText() + "; from : " + other.getIdentifier());
                return true;
            }
        } catch (InterruptedException e) {
        } finally {
            if (acquired) {
                lock.unlock();
            }
        }

        return false;
    }
}