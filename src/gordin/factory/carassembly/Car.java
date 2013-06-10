package gordin.factory.carassembly;

import gordin.factory.Enumerable;
import gordin.factory.accessorysupply.Accessory;
import gordin.factory.bodysupply.Body;
import gordin.factory.enginesupply.Engine;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/16/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Car implements Enumerable{
    static private long lastId = 1;
    static private final Object lock = new Object();
    private final long myId;
    private final Body body;
    private final Engine engine;
    private final Accessory accessory;

    public Car(long idSupplier, Body body, Engine engine, Accessory accessory)
    {
        synchronized (lock)
        {
            this.body = body;
            this.engine = engine;
            this.accessory = accessory;
            String str = ""+idSupplier+lastId;
            myId = Long.parseLong(str);
            lastId++;
            System.out.println("c "+myId);
        }
    }

    public long getId()
    {
        return myId;
    }

    public long getBodyId()
    {
        return body.getId();
    }

    public long getEngineId()
    {
        return engine.getId();
    }

    public long getAccessoryId()
    {
        return accessory.getId();
    }
}
