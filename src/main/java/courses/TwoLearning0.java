package courses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Camel Application
 */
public class TwoLearning0
{

    Integer i1 = 1;
    Integer i2 = 2;
    Integer i3 = i1;
    IntegerWrapper iw1 = new IntegerWrapper(i1);
    IntegerWrapper iw2 = new IntegerWrapper(i2);


    public static void main(String... args) throws Exception {

        TwoLearning0 ma = new TwoLearning0();
        ma.a(ma.i1, ma.i2);
        System.out.println(ma.i1 + " : " + ma.i2);
        ma.b(ma.i1, ma.i2);

        ma.c();
    }
    void c() throws StackOverflowError, OutOfMemoryError {
        List<String> s = new ArrayList<>();
        String counter = "a";
        System.exit(1);
        while (true) {
//            System.out.println("counter: " + ++counter);
            System.out.println("total: " + Runtime.getRuntime().totalMemory() / 1048576);
            System.out.println("used: " + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) / 1048576);
            s.add(counter + java.lang.Math.random());
        }
    }


    void a(Integer int1, Integer int2) {
        Integer int3 = int1;
        int1 = int2;
        int2 = int3;

        System.out.println(int1 + " : " + int2 + " : i3 = i1 " + this.i3);

        Integer int4 = iw1.getInt();
        iw1.setInt(iw2);
        iw2.setInt(int4);

        System.out.println(123);
        System.out.println(iw1.getInt() + " : " + iw2.getInt());
        System.out.println(123);
    }

    void b(Integer int1, Integer int2) {
        System.out.println(int1 + " : " + int2);
    }

    class IntegerWrapper
    {
        private Integer i;

        IntegerWrapper(Integer i) {
            this.i = i;
        }

        void setInt(IntegerWrapper i) {
            this.i = i.getInt();
        }

        void setInt(Integer i) {
            this.i = i;
        }

        Integer getInt() {
            return this.i;
        }
    }

}

