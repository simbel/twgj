package twgj.ch4.modern.pets;

/**
 * Created by dliu2 on 2015/5/27.
 */
public class Dog extends Pet {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void examine() {
        System.out.println("Woof, woof");
    }
}
