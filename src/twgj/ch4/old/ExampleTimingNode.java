package twgj.ch4.old;

/**
 * Created by dliu2 on 2015/5/26.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pramodh on 3/14/14.
 */
public class ExampleTimingNode {
    private final String identifier;

    private final Map<Update, Long> arrivalTime = new HashMap<>();

    public ExampleTimingNode(String identifier) {
        this.identifier = identifier;
    }

    // All Synchronized methods
    public synchronized String getIdentifier(){
        return this.identifier;
    }

    public synchronized void propagateUpdate(Update update){
        long currentTime = System.currentTimeMillis();
        arrivalTime.put(update, currentTime);
    }

    public synchronized boolean confirmUpdateReceived(Update update){
        Long timeReceived = arrivalTime.get(update);
        return timeReceived != null;
    }
}