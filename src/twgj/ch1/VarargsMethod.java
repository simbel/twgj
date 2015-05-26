package twgj.ch1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dliu2 on 2015/5/21.
 */
public class VarargsMethod {
    
    final <T extends List<?>> void foo(T... args) {
        List<String>[] array2 = (List<String>[])args;
        array2[0] = Arrays.asList("a", "b");
    }


    void test2() {
        List<Integer>[] args = new List[]{Arrays.asList(1,2)};
        foo(args);
        Integer i = args[0].get(0);
    }

    public static void main(String[] args) {
        VarargsMethod vm = new VarargsMethod();
        vm.test2();
    }
}
