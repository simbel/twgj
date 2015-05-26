package twgj.ch4;

/**
 * Created by dliu2 on 2015/5/26.
 */
class TestOuter1 {
    static int data = 30;
    int variable = 20;

    private TestOuter1() {
        variable = 10;
    }

    public int getVariable() {
        return variable;
    }

    static class Inner {
        void msg() {
            TestOuter1 to1 = new TestOuter1();
            System.out.println("data is " + data + ", variable is " + to1.getVariable());
        }
    }

    public static void main(String args[]) {
        TestOuter1.Inner obj = new TestOuter1.Inner();
        obj.msg();
    }
}
