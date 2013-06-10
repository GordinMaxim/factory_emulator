package gordin.factory.accessorysupply;

import gordin.factory.Enumerable;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/16/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Accessory implements Enumerable {
    private static long LAST_ID = 1;
    private static final Object LOCK = new Object();
    private final long id;

    public Accessory(long idSupplier)
    {
        synchronized (LOCK)
        {
            String str = ""+idSupplier+ LAST_ID;
            id = Long.parseLong(str);
            LAST_ID++;
        }
        System.out.println("ac "+ id);
    }

    public long getId()
    {
        return id;
    }
}
