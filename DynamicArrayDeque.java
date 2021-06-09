// Name: Ratanak Rin
// Email: ratanak.rin@stonybrook.edu

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class DynamicArrayDeque<E> implements Deque<E> {
    private E[] data;
    private int f;
    private int size;
    private static final int capacity = 16;

    public DynamicArrayDeque() {
        this(capacity);
    }
    public DynamicArrayDeque(int capacity) {
        data = (E[]) new Object[capacity];
    }

    //TODO: implement DynamicArrayDeque
    //      in addFirst and addLast, resize when the array is full
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int index(int i) {
        return (i + data.length) % data.length;
    }

    public int lastIndex() {
        return index(f + size - 1);
    }

    public E first() {
        if(isEmpty())
            throw new IllegalStateException("Queue us empty");
        return data[f];
    }

    public E last() {
        if (isEmpty())
            throw new IllegalStateException("Queue us empty");
        return data[lastIndex()];
    }

    public void addFirst(E e) {
        if (size == data.length)
            resize(2 * data.length);
        for(int l=size; l > 0; l--)
            data[l] = data[l-1];
        data[0] = e;
        size++;
    }

    public void addLast(E e) {
        if (size == data.length)
            resize(2 * data.length);
        data[size] = e;
        size++;
    }

    public E removeFirst() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        E ret = data[0];
        for(int i=1; i < size; i++)
            data[i-1] = data[i];
        data[size-1] = null;
        size--;
        return ret;
    }

    public E removeLast() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        int i = lastIndex();
        E ret = data[i];
        data[i] = null;
        size--;
        return ret;
    }

    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    private void resize(int capacity) {
        //TODO: implement resize method
        E[] tmp = ((E[]) new Object[capacity]);
        for (int k = 0; k < size; k++) {
            tmp[k] = data[k];
        }
        data = tmp;
    }

    private class ArrayIterator implements Iterator<E> {
        int i;
        public ArrayIterator() {
            i = 0;
        }
        //TODO: implement ArrayIterator

        public boolean hasNext() {
            return i < size;
        }
        public E next() {
            if (i >= size)
                throw new NoSuchElementException(("no such elements"));
            return data[i++];
        }
    }

    private static void onFalseThrow(boolean b) {
        if(!b)
            throw new RuntimeException("Error: unexpected");            
    }

    public String toString(){
        String res = "";
        for(E elem: data){
            res += elem + ", ";
        }
        return res;
    }

    public static void main(String[] args) {
        DynamicArrayDeque<Integer> dq = new DynamicArrayDeque<>(1);

        for(int i : dq)
            onFalseThrow(false);
        onFalseThrow(dq.size() == 0);
        onFalseThrow(dq.isEmpty() == true);
        
        dq.addFirst(3);
        dq.addFirst(2);
        dq.addFirst(1);
        dq.addLast(4);
        dq.addLast(5);

        int j = 1;
        for(int i : dq)
            onFalseThrow(i == j++);
        onFalseThrow(dq.size() == 5);
        onFalseThrow(dq.isEmpty() == false);

        for(int i = 1; i <= 3; i++)
            onFalseThrow(i == dq.removeFirst());
        onFalseThrow(dq.removeLast() == 5);
        onFalseThrow(dq.removeLast() == 4);

        onFalseThrow(dq.size() == 0);
        onFalseThrow(dq.isEmpty() == true);

        System.out.println("Success!");
    }
}
