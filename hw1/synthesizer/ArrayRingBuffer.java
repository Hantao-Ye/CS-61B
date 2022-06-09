package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;

    /* Index for the next enqueue. */
    private int last;

    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        fillCount = 0;
        this.capacity = capacity;

        first = 0;
        last = 0;

        rb = (T[]) new Object[capacity];
    }

    private int addOne(int index) {
        if (index == capacity - 1) {
            return 0;
        }

        return index + 1;
    }

    private int minusOne(int index) {
        if (index == 0) {
            return capacity - 1;
        }

        return index - 1;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        rb[last] = x;

        fillCount++;
        last = addOne(last);
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }

        T val = rb[first];

        fillCount--;
        first = minusOne(first);

        return val;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return rb[first];
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        private int curNum;
        ArrayRingBufferIterator() {
            pos = first;
            curNum = 0;
        }

        public boolean hasNext() {
            return curNum < fillCount;
        }

        public T next() {
            T retValue = rb[pos];
            pos = addOne(pos);
            curNum++;
            return retValue;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }
}