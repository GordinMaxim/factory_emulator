package gordin;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/16/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockingQueue<T> {
    private List<T> storage = new LinkedList<T>();
    private int capacity;

    private BlockingQueue(){}

    public BlockingQueue(int capacity)
    {
        this.capacity = capacity;
    }

    public synchronized void put(T element) throws InterruptedException
    {
        while(capacity == storage.size())
        {
            wait();
        }
        storage.add(element);
        notify();
    }

    public synchronized T take() throws InterruptedException
    {
        while(storage.isEmpty())
        {
            wait();
        }
        T item =  storage.remove(0);
        notify();
        return item;
    }

    public synchronized int size()
    {
        return storage.size();
    }
}
