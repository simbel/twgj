package twgj.ch4.modern;

/**
 * Created by dliu2 on 2015/5/26.
 */
import java.util.concurrent.*;

/**
 * Created by pramodh on 3/17/14.
 */
public class ScheduledThreadPoolTester {
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture<?> hndl;

    private BlockingQueue<WorkUnit<String>> lbq = new LinkedBlockingQueue<>();
    private void run(){
        scheduledExecutorService = Executors.newScheduledThreadPool(2); // Executor factory method
        final Runnable msgReader = new Runnable() {
            @Override
            public void run() {
                String nextMessage = lbq.poll().getWorkUnit();
                if(nextMessage != null)
                    System.out.println("Message Received: " + nextMessage);

            }
        };
        hndl = scheduledExecutorService.scheduleAtFixedRate(msgReader, 10, 10, TimeUnit.MILLISECONDS);
    }

    public void cancel(){
        final ScheduledFuture<?> myHndl = hndl;
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                myHndl.cancel(true);
            }
        }, 10,TimeUnit.MILLISECONDS);
    }

}