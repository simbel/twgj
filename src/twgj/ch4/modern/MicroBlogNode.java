package twgj.ch4.modern;

import twgj.ch4.old.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MicroBlogNode {
    private final String identifier;
    private final Lock lock = new ReentrantLock();

    private final Map<Update, Long> arrivalTime = new HashMap<>();

    public MicroBlogNode(String identifier) {
        this.identifier = identifier;
    }

    // All Synchronized methods
    public synchronized String getIdentifier(){
        return this.identifier;
    }

    public void propagateUpdate(Update update, MicroBlogNode backup){
        lock.lock();
        try {
            System.out.println(identifier + ": recvd: " + update.getUpdateText() + "; backup: " + backup.getIdentifier());
            backup.confirmUpdateReceived(this, update);
        } finally {
            lock.unlock();
        }
    }

    public void propagateUpdateProblem(Update update, MicroBlogNode backup){
        boolean acquired = false;
        while (!acquired){
            try{
                int wait = (int) (Math.random() * 10);
                acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
                if(acquired){
                    System.out.println(identifier + ": recvd: " + update.getUpdateText() + "; backup: " + backup.getIdentifier());
                    backup.confirmUpdateReceived(this, update);
                } else {
                    Thread.sleep(wait);
                }
            }catch (InterruptedException ex){

            } finally {
                if(acquired) lock.unlock();
            }
        }
        lock.lock();
        try {
        } finally {
            lock.unlock();
        }
    }

    public void propagateUpdateSol(Update update, MicroBlogNode backup){
        boolean acquired = false;
        boolean done = false;
        while (!done){
            int wait = (int) (Math.random() * 10);
            try{
                acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
                if(acquired){
                    System.out.println(identifier + ": recvd: " + update.getUpdateText() + "; backup: " + backup.getIdentifier());
                    done = backup.confirmUpdateReceivedSol(this, update);
                } else {
                    Thread.sleep(wait);
                }
            }catch (InterruptedException ex){

            } finally {
                if(acquired) lock.unlock();
            }
            if(!done) try{
                Thread.sleep(wait);
            } catch(InterruptedException ex){

            }
        }
        lock.lock();
        try {
        } finally {
            lock.unlock();
        }
    }


    public void confirmUpdateReceived(MicroBlogNode other, Update update){
        lock.lock();
        try {
            System.out.println(identifier + ": recvd confirm : " + update.getUpdateText() + "; from : " + other.getIdentifier());
        } finally {
            lock.unlock();
        }
    }

    public boolean confirmUpdateReceivedSol(MicroBlogNode other, Update update){
        boolean acquired = false;
        try {
            int wait = (int)(Math.random() * 10);
            long startTime = System.currentTimeMillis();
            acquired = lock.tryLock(wait, TimeUnit.MILLISECONDS);
            if(acquired){
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println(identifier + ": recvd confirm : " + update.getUpdateText() + "; from : " + other.getIdentifier());
                return true;
            }
        } catch(InterruptedException ex){

        }
        finally {
            if(acquired) lock.unlock();
        }
        return false;
    }

}