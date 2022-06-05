public class LinkedListDeque<T> {
    private static class LinkedNode<t> {
        public t val;
        public LinkedNode<?> next;
        public LinkedNode<?> prev;

        public LinkedNode(t val) {
            this.val = val;
            next = null;
            prev = null;
        }
    }

    private int size;
    private LinkedNode<?> head;
    private LinkedNode<?> tail;

    public void addFirst(T item) {
        if (head == null) {
            head = new LinkedNode<T>(item);
            tail = head;
            size = 1;
        } else {
            LinkedNode<T> tmp = new LinkedNode<>(item);

            tmp.next = head;
            head.prev = tmp;

            head = tmp;

            size++;
        }
    }

    public void addLast(T item) {
        if (tail == null) {
            tail = new LinkedNode<T>(item);
            head = tail;
            size = 1;
        } else {
            LinkedNode<T> tmp = new LinkedNode<>(item);

            tmp.prev = tail;
            tail.next = tmp;

            tail = tmp;

            size++;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        LinkedNode<?> cur = head;
        while (cur != null) {
            System.out.print(String.valueOf(cur.val));
            if (cur.next != null) {
                System.out.print(" ");
            }

            cur = cur.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T val = (T) head.val;

            head = null;
            tail = null;

            size = 0;

            return val;
        }

        T val = (T) head.val;

        LinkedNode<?> tmp = head;

        head = head.next;
        head.prev = null;

        tmp = null;

        size--;
        return val;

    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T val = (T) tail.val;

            head = null;
            tail = null;

            size = 0;

            return val;
        }

        T val = (T) tail.val;

        LinkedNode<?> tmp = tail;

        tail = tail.prev;
        tail.next = null;

        tmp = null;

        size--;
        return val;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            return null;

        LinkedNode<?> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        return (T) cur.val;
    }

    public LinkedListDeque() {
        size = 0;
        head = null;
        tail = null;
    }

    public T traversal(LinkedNode head, int index) {
        if (index == 0) {
            return (T) head.val;
        }

        return traversal(head.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return traversal(head, index);
    }
}
