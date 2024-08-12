package org.jibble.pircbot;

import java.util.Vector;

public class Queue {

    public static final int EaZy = 1983;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final Vector _queue = new Vector();

    public void add(final Object o) {
        final Vector vector = _queue;
        synchronized (vector) {
            _queue.addElement(o);
            _queue.notify();
        }
    }

    public void addFront(final Object o) {
        final Vector vector = _queue;
        synchronized (vector) {
            _queue.insertElementAt(o, 0);
            _queue.notify();
        }
    }

    public Object next() {
        Object o = null;
        final Vector vector = _queue;
        synchronized (vector) {
            block8:
            {
                if (!_queue.isEmpty()) {
                    break block8;
                }
                try {
                    _queue.wait();
                } catch (final InterruptedException e) {
                    return null;
                }
            }
            try {
                o = _queue.firstElement();
                _queue.removeElementAt(0);
            } catch (final ArrayIndexOutOfBoundsException e) {
                throw new InternalError("Race hazard in Queue object.");
            }
        }
        return o;
    }

    public boolean hasNext() {
        return size() != 0;
    }

    public void clear() {
        final Vector vector = _queue;
        synchronized (vector) {
            _queue.removeAllElements();
        }
    }

    public int size() {
        return _queue.size();
    }
}
