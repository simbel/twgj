package twgj.ch4.modern.pets;

/**
 * Created by dliu2 on 2015/5/27.
 */
public abstract class Pet {
    protected final String name;

    protected Pet(String name) {
        this.name = name;
    }
    public abstract void examine();
}
