package twgj.ch4.modern;

/**
 * Created by pramodh on 3/13/14.
 */
public class WorkUnit<T> {
    private final T workUnit;
    public T getWorkUnit(){ return workUnit;}
    public WorkUnit(T workUnit){
        this.workUnit = workUnit;
    }
}
