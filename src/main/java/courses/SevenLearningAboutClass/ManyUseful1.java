package courses.SevenLearningAboutClass;

//interface A
//{
//    @NotNull
//    String s();
//}

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import java.util.Optional;

class B //implements A
{

    public static String s(@Nullable String s) {
        return s;
    }

    public static String s1(@NotNull String s) {
        return s;
    }
}

public class ManyUseful1
{
    public static void main(String[] args) {
        B b = new B();
        try {
        System.out.println(b.s1(null).substring(0));
        } catch (NullPointerException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        /** с Optional.of работает get */
        /*Optional<String> o = Optional.of(b.s1("a"));
        System.out.println(o.get());*/

        Optional<String> o = Optional.ofNullable(b.s1(null));
        System.out.println(o.isPresent());
        System.out.println(o.orElse("Ttt").substring(1));

        Optional<String> o1 = Optional.ofNullable(b.s1("abcdef"));
        o1.ifPresent((s) -> System.out.println("Result: " + s.charAt(2))); //Если не null, то выполняется эта анонимка
    }
}
