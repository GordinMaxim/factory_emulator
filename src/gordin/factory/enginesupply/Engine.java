package gordin.factory.enginesupply;

import gordin.factory.Enumerable;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/16/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Engine implements Enumerable {
    static private long lastId = 1;
    static private final Object lock = new Object();
    private final long myId;

    public Engine(long idSupplier)
    {
        synchronized (lock)
        {
            String str = ""+idSupplier+lastId;
            myId = Long.parseLong(str);
            lastId++;
            System.out.println("e "+myId);
        }
    }

    public long getId()
    {
        return myId;
    }
}

