package twgj.ch4.old;

/**
 * Created by dliu2 on 2015/5/26.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pramodh on 3/14/14.
 */
public class MicroBlogNode {
    private final String identifier;

    private final Map<Update, Long> arrivalTime = new HashMap<>();

    public MicroBlogNode(String identifier) {
        this.identifier = identifier;
    }

    // All Synchronized methods
    public synchronized String getIdentifier(){
        return this.identifier;
    }

    public synchronized void propagateUpdate(Update update, MicroBlogNode backup){
        System.out.println(identifier + ": recvd: " + update.getUpdateText() + "; backup: " + backup.getIdentifier());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        backup.confirmUpdateReceived(this, update);
    }

    public synchronized void confirmUpdateReceived(MicroBlogNode other, Update update){
        System.out.println(identifier + ": recvd confirm : " + update.getUpdateText() + "; from : " + other.getIdentifier());
    }
}