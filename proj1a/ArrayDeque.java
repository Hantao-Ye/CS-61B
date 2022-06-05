public class ArrayDeque<T> {
    private int size;
    private int capacity;

    private T[] array;

    private int head;
    private int tail;

    public ArrayDeque() {
        size = 0;
        capacity = 8;

        array = (T[]) new Object[8];

        head = -1;
        tail = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int plusOne(int index, int length) {
        index %= length;
        if (index == length - 1) {
            return 0;
        }

        return index + 1;
    }

    private int subsOne(int index) {
        if (index == 0) {
            return capacity - 1;
        }

        return index - 1;
    }

    private void grow() {
        T[] a = (T[]) new Object[capacity * 2];

        int ptr1 = head;
        int ptr2 = size;

        while (ptr1 != tail) {
            a[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, capacity);
            ptr2 = plusOne(ptr2, capacity * 2);
        }

        head = capacity;
        tail = ptr2;
        array = a;
        capacity *= 2;
    }

    private void shrink() {
        T[] a = (T[]) new Object[capacity / 2];

        int ptr1 = head;
        int ptr2 = capacity / 4;

        while (ptr1 != tail) {
            a[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, capacity);
            ptr2 = plusOne(ptr2, capacity / 2);
        }

        head = capacity / 4;
        tail = ptr2;
        array = a;
        capacity /= 2;
    }

    public void addFirst(T item) {
        if (size == 0) {
            head = 0;
            array[head] = item;
            size++;

            return;
        }

        if (size == capacity - 1) {
            grow();
        }

        head = subsOne(head);
        array[head] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == 0) {
            head = 0;
            array[tail] = item;
            size++;

            return;
        }

        if (size == capacity - 1) {
            grow();
        }

        tail = plusOne(tail, capacity);
        array[tail] = item;
        size++;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T val = array[head];

            head = -1;
            size = 0;

            return val;
        } else if (capacity >= 16 && capacity / size >= 4) {
            shrink();
        }

        T val = array[head];

        head = plusOne(head, capacity);
        size--;

        return val;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T val = array[tail];

            head = -1;
            size = 0;

            return val;
        } else if (capacity >= 16 && capacity / size >= 4) {
            shrink();
        }

        T val = array[tail];

        tail = subsOne(tail);
        size--;

        return val;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        int ptr = head;
        for (int i = 1; i < index; i++) {
            ptr = plusOne(ptr, capacity);
        }

        return array[ptr];
    }

    public void printDeque() {
        int ptr = head;
        for (int i = 0; i < size; i++) {
            System.out.print(String.valueOf(array[ptr]) + " ");
            ptr = plusOne(ptr, capacity);
        }
        System.out.print(String.valueOf(array[ptr]));
    }
}
