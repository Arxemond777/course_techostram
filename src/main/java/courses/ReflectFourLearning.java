package courses;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

import static java.lang.System.out;
import static java.lang.System.setOut;

/**
 * Created by arxemond777 on 30.01.17.
 */
public class ReflectFourLearning
{
    public static void main(String[] args) throws Exception {
        Class clazz = Test.class;
        Test test = (Test)clazz.newInstance();

        clazz.getConstructors();

        Method method = clazz.getMethod("foo");
        out.println(method.toString());
        method.invoke(test);

        Field field0 = clazz.getDeclaredField("field");
        field0.setAccessible(true);
        field0.set(test, 100);
        out.println("test " + test);

        Package pack = clazz.getPackage();
        out.println("Package " + pack.getName() + ";");

        int modifers = clazz.getModifiers();
        out.println(getModifiers(modifers));

        out.println("class " + clazz.getSimpleName() + ";");
        out.println("extend " + clazz.getSuperclass().getSimpleName() + ";");

        Class[] intefaces = clazz.getInterfaces();
        for (int i = 0, size = intefaces.length; i < size; i++) {
            out.print(i == 0 ? "implements " : ", ");
            out.print(intefaces[i].getSimpleName());
        }

        out.println("{");

        Field[] fields = clazz.getDeclaredFields();
        for(Field filed : fields) {
            out.println(
                    "\t" + getModifiers(filed.getModifiers()) +
                    getType(filed.getType()) + " " + filed.getName()
            );
        }

        Constructor[] constructors = clazz.getDeclaredConstructors();

        //a(constructors);
        for (Constructor constructor : constructors) {
            out.println("\t" + getModifiers(constructor.getModifiers())
            + clazz.getSimpleName() + "(");
//            System.out.println("____________________");
//            System.out.println(constructor.getParameterTypes());
//            System.exit(1);
            out.print(getParameters(constructor.getParameterTypes()));
            out.println(") {}");
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method1 : methods) {
            Annotation[] annotations = method1.getAnnotations();
            out.print("\t");
            for (Annotation annotation : annotations) {
                out.println("@" + annotation.annotationType().getSimpleName());
            }
            out.println();
            out.print("\t" + getModifiers(method1.getModifiers())
            + getType(method1.getReturnType()) + " " + method1.getName());
            out.println(getParameters(method1.getParameterTypes()));
            out.println(") {}");
        }
    }

    public static String getModifiers(int mod) {
        String modifiers = "";
        if (Modifier.isPublic(mod))
            modifiers += "public ";
        if (Modifier.isProtected(mod))
            modifiers += "protected ";
        if (Modifier.isPrivate(mod))
            modifiers += "private ";
        if (Modifier.isStatic(mod))
            modifiers += "static ";
        if (Modifier.isAbstract(mod))
            modifiers += "abstract ";

        return modifiers;
    }

    static String getType(Class clazz) {
        String type = clazz.isArray() ? clazz.getComponentType().getSimpleName() : clazz.getSimpleName();
        if (clazz.isArray())
            type += "[]";
        return type;
    }

    static void a(Constructor[] constructors) {
        for (int i = 0; i < constructors.length; i++) {
            Constructor c = constructors[i];
            Class[] paramTypes = c.getParameterTypes();
            String name = c.getName();
            System.out.print(Modifier.toString(c.getModifiers()));
            System.out.print(" " + name + "(");
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0)
                    System.out.print(", ");
                System.out.print(paramTypes[j].getCanonicalName());
            }
            System.out.println(");");
        }
        System.exit(1);
    }

    static String getParameters(Class[] param) {
        String parameters = "";

        for (int j = 0; j < param.length; j++) {
            if (j > 0)
                parameters += ", ";
            parameters += param[j].getCanonicalName();
        }

        return "a";
    }
}

class Test implements Serializable, Closeable
{
    private int field;

    public Test() {}

    public Test(Object filed) {}

    @Deprecated
    protected static void method(String[] params) {}

    public void foo() { out.println("FOO"); }

    @Override
    public String toString() {
        return "Test{filed=" + field + "}";
    }

    @Override
    public void close() throws IOException {

    }
}