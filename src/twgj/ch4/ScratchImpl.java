package twgj.ch4;

/**
 * Created by dliu2 on 2015/5/29.
 */
public class ScratchImpl {

    private static ScratchImpl inst = null;

    private ScratchImpl() {}

    private void run() {

    }

    public static void main(String[] args) {
        inst = new ScratchImpl();
        inst.run();
    }
}
