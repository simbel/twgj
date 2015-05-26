package twgj.ch1;

/**
 * Created by dliu2 on 2015/5/21.
 */
public class NumericLiteral {

    public static void main(String[] args) {
        int a1 = Integer.parseInt("110", 2);
        int a2 = 0b110;

        assert a1 == a2;


        long b1 = 10_000_000_00L;
        long b2 = 1000000000L;

        assert b1 == b2;

    }
}
