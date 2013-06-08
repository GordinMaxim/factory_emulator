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
    static private Long lastId = new Long(1);
    private final long myId;

    public Body(long idSupplier)
    {
        synchronized (lastId)
        {
            String str = ""+idSupplier+lastId.longValue();
            myId = Long.parseLong(str);
            lastId = new Long(lastId.longValue()+1);
            System.out.println("b "+myId);
        }
    }

    public long getId()
    {
        return myId;
    }
}
