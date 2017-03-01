package courses.SevenLearningAboutClass;

/**
 * Created by arxemond777 on 06.02.17.
 */
public class SevenLearning
{
    int i = 1;
    Object o;

    public static void main(String[] args) {
            SevenLearning sevenLearning = new SevenLearning();
            sevenLearning.localMethod1(sevenLearning.i);
            sevenLearning.check();
        System.out.println(sevenLearning.o.toString());
        sevenLearning.o = new SevenLearning().new B();
        SevenLearning.B b = sevenLearning.new B();

        System.out.println(sevenLearning.o);
    }

    class B {
        public int i = 666;
    }

    void localMethod() {
        class Local {
            int i = 2;
        }

        Local local = new Local();
        i = local.i;
    }

    void localMethod1(int i) {//если через аргумент, то не будет
        class Local {
            public Local(SevenLearning out) {
                this.i2 = out.i + out.i;
            }

            public int i = 2;
            int i2;

            void r() {
                System.out.println(123);
                i = i2;
                System.out.println(i);
            }

            @Override
            public String toString() {
                return "sa";
            }
        }

        Local local = new Local(this);
        this.o = local;
        local.r();
        this.i = local.i;
        System.out.println(this.i);
    }

    void check() {
        System.out.println(i);
    }
}

