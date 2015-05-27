package twgj.ch4.modern.pets;

/**
 * Created by dliu2 on 2015/5/27.
 */
public class Appointment<T> {
    private final T toBeSeen;

    public T getPatient() {
        return toBeSeen;
    }

    public Appointment(T incoming) {
        toBeSeen = incoming;
    }
}
