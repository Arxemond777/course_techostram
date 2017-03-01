package courses;

/**
 * Created by arxemond777 on 27.01.17.
 */
public class ThreeLearning
{
    static class a{public static void voidr(){
        System.out.println(666);
    }}

    class C
    {
        void test() {
            System.out.println(this.getClass().getSimpleName());
        }

        void test(int i){
            System.out.println(this.getClass().getSimpleName() + " : " + i);
        }
    }

    class B extends C {
        @Override
        void test() {
            System.out.println(1);
        }
    }

    public static void main(String[] args) {
//        a a = a.class.newInstance();
//        a.voidr();
//        System.out.println(a.class.newInstance());
        B b = new ThreeLearning().new B();
        b.test();

        B b1 = new ThreeLearning().new B();
        C c = b1;
        c.test();

        C b2 = new ThreeLearning().new B();
        /**
         * На этапе компиляции по типу ссылки
         * на этапе runtime по типу объекта
         */
        use(b2);//Потому что это позднее сязывание, и в момент создания мы еще не знаем, что в реале это class B

    }

    abstract class D
    {
        //В абстрактном классе свойства могут быть переопрелены, в абстрактном все свойства статические
        public int i = 1;
        private final int r = 687;
    }

    static void use(C c) {
        System.out.println("C");
        c.test();
    }

    static void use(B b) {
        System.out.println("B");
        b.test();
    }

    interface E {
        void e();
    }
}
