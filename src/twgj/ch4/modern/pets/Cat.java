package twgj.ch4.modern.pets;

/**
 * Created by dliu2 on 2015/5/27.
 */
public class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void examine() {
        System.out.println("Meow");
    }

}
