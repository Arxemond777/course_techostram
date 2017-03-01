package courses.generic;

/**
 * Created by arxemond777 on 31.01.17.
 */
import java.lang.instrument.Instrumentation;
import java.util.*;
import java.util.concurrent.Callable;

public class FiveLearning
{

    public static void main(String[] args) {
        List<Cat> cats = new ArrayList<>();
        cats.add(new Cat());
        cats.add(new Cat());

        List<? extends Pet> pets = cats;

//        List<Dog> dogs = new ArrayList<>();
//        dogs.add(new Dog());
//        dogs.add(new Dog());

        callPets(cats);//Вызовется методы Pet (родителя) чтоб не было ошибки

        List<Animal> animals = new ArrayList<>();
        fillPets(animals);
    }

    static class Animal {
        void feed() {
            System.out.println("Animal feed");
        }
    }

    static class Pet extends Animal {
        void call() {
            System.out.println("Pet call");
        }
    }

    static class Dog extends Pet {
        void bark() {
            System.out.println("Dog bark");
        }
    }

    static class Cat extends Pet {
        void mew() {
            System.out.println("Cat mew");
        }
    }

    /**
     * Producer
     * Можно Pet и наследников (Pet Dog & Cat)
     */
    static <T extends Pet> void callPets(List<T> pets) { //если интерфес, то все равно extends SomeInterface
        for (T item : pets)
            item.call(); //сокращенно pets.stream().forEach(Pet::call);
    }

    /**
     * Consumer - поставщик
     * Можно Pet и родителей (Pet Animal)
     */
    static void fillPets(List<? super Pet> pets) {
        pets.add(new Dog());
        pets.add(new Cat());
    }

}
