package courses.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by arxemond777 on 31.01.17.
 */
public class ArrayStack<E> implements Stack<E>
{
    private Object[] elements;
    private int capacity, size;

    public static void main(String[] args) throws Exception {
        ArrayStack<Integer> ints = new ArrayStack<>(10);
        ints.push(10);
        ints.push(12);

        /** Stack -> Call (int -> number) */
        List<Number> nums = new ArrayList<>();
        ints.popAll(nums); //Смотреть на реализацию popAll и его реализацию
        System.out.println(ints); //extend

        /** Call -> Stack (number -> int) */
        List<Integer> numMs = Arrays.asList(1, 2, 3);
        ints.pushAll(numMs);
        System.out.println(ints);//super
    }

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        size = 0;
        elements = new Object[capacity];
    }

    @Override
    public void push(E element) throws StackException {

    }

    @Override
    public E pop() throws StackException {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void pushAll(Collection<? extends E> src) throws StackException {

    }

    //поменяли с <E> на <? super E>
    @Override
    public void popAll(Collection<? super E> dst) throws StackException {

    }
}
