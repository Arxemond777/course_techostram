package courses.SevenLearningAboutClass;

import java.util.Arrays;

/**
 * Created by arxemond777 on 06.02.17.
 */
public class ManyUseful
{
    @FunctionalInterface
    interface Converter<T, C> {
        T convert(T t, C c);
    }

    @FunctionalInterface
    interface ConverterNew<T, C> {
        C convert(T t, C c);
        //Если БЕЗ @FunctionalInterface то Error unchecked
    }

    class Something {
        //МБ быть в рантайме ошибка
        Integer startsWith(String s, Integer integer) {
            return integer + Integer.parseInt(s);
        }
    }
    public static void main(String[] args) {
        ManyUseful te = new ManyUseful();

        ManyUseful.Converter<String, Integer> tep = (s, i) ->  s + i;
        System.out.println(tep.convert("a ", 123));
        //////////////////////////////////////////////////
        Something s = new ManyUseful().new Something();
        /** Т.е. наш метод s::startsWith реализует нужную сигнатуру и действия
         * которая задана в функциональном интерфесе и ему не нужны параметры */
        ManyUseful.ConverterNew<String, Integer> converter = s::startsWith;
        try {
            System.out.println(converter.convert("1", 10));
            System.out.println(converter.convert("ф", 10));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        //////////////////////////////////////////////////
        final int[] array = {1, 2, 3, 4, 5};
        new Object() {
            void twice() {
                for (int i = 0; i < array.length; ++i) {
                    array[i] *= 2;
                }
            }
        }.twice();
        System.out.println(Arrays.toString(array));
    }

//    class Y implements Closeable {}
    enum Test {
        UP(1), DOWN(2);
        int i;

        Test(int i) {
            this.i = i;
        }

        public int getInt() {
            return this.i;
        }
    }

    interface A {

    }

    interface B {

    }

    public void D() {

        A a = new A(){};
    }
}
