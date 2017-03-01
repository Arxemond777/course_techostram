package courses.generic;

import java.util.Collection;

/**
 * Created by arxemond777 on 31.01.17.
 */
public interface Stack<E>
{
    void push(E element) throws StackException;

    E pop() throws StackException;

    E peek();

    boolean isEmpty();

    boolean isFull();

    void pushAll(Collection<? extends E> src) throws StackException;

    void popAll(Collection<? super E> dst) throws StackException;
}