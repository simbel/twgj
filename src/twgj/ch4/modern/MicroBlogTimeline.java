package twgj.ch4.modern;

/**
 * Created by dliu2 on 2015/5/26.
 */
import twgj.ch4.old.Update;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pramodh on 3/13/14.
 */
public class MicroBlogTimeline {
    private final CopyOnWriteArrayList<Update> updates;
    private final ReentrantLock lock;
    private final String name;
    private Iterator<Update> it;

    public MicroBlogTimeline(CopyOnWriteArrayList<Update> updates, ReentrantLock lock, String name) {
        this.updates = updates;
        this.lock = lock;
        this.name = name;
    }

    public void addUpdate(Update update){
        updates.add(update);
    }

    public void prep(){
        it = updates.iterator();
    }

    public void printTimeline(){
        lock.lock();
        try {
            if(it != null){
                System.out.println(name + ": ");
                while(it.hasNext()){
                    Update s = it.next();
                    System.out.println(s+", ");
                }
                System.out.println();
            }
        } finally {
            lock.unlock();
        }
    }
}