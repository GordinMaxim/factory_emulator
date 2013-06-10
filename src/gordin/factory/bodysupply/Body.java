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
    private static long LAST_ID = 1;
    private static final Object LOCK = new Object();
    private final long id;

    public Body(long idSupplier)
    {
        synchronized (LOCK)
        {
            String str = ""+idSupplier+ LAST_ID;
            id = Long.parseLong(str);
            LAST_ID++;
        }
        System.out.println("b "+ id);
    }

    public long getId()
    {
        return id;
    }
}
