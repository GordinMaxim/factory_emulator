package gordin.factory.bodysupply;

import gordin.factory.Enumerable;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/12/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Body implements Enumerable {
    static private long lastId = 1;
    static private final Object lock = new Object();
    private final long myId;

    public Body(long idSupplier)
    {
        synchronized (lock)
        {
            String str = ""+idSupplier+lastId;
            myId = Long.parseLong(str);
            lastId++;
            System.out.println("b "+myId);
        }
    }

    public long getId()
    {
        return myId;
    }
}
