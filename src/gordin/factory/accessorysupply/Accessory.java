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
    static private Long lastId = new Long(1);
    private final long myId;

    public Accessory(long idSupplier)
    {
        synchronized (lastId)
        {
            String str = ""+idSupplier+lastId.longValue();
            myId = Long.parseLong(str);
            lastId = new Long(lastId.longValue()+1);
            System.out.println("ac "+myId);
        }
    }

    public long getId()
    {
        return myId;
    }
}
