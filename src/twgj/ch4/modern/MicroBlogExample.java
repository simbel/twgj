package twgj.ch4.modern;

import twgj.ch4.old.Update;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * Created by pramodh on 3/14/14.
 */
public abstract class MicroBlogExample extends Thread {
    protected final BlockingQueue<Update> updates;
    protected String text = "";
    protected final int pauseTime;
    protected boolean shutdown = false;

    public MicroBlogExample(BlockingQueue<Update> updates, int pauseTime){
        this.updates = updates;
        this.pauseTime = pauseTime;
    }

    public synchronized void shutdown(){
        shutdown = true;
    }

    @Override
    public void run() {
        while(!shutdown){
            doAction();
            try {
                Thread.sleep(pauseTime);
            } catch (InterruptedException e) {
                shutdown = true;
            }
        }
    }

    public abstract void doAction();
}