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
    static private Long lastId = new Long(1);
    private final long myId;

    public Engine(long idSupplier)
    {
        synchronized (lastId)
        {
            String str = ""+idSupplier+lastId.longValue();
            myId = Long.parseLong(str);
            lastId = new Long(lastId.longValue()+1);
            System.out.println("e "+myId);
        }
    }

    public long getId()
    {
        return myId;
    }
}

