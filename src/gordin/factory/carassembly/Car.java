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
    static private Long lastId = new Long(1);
    private final long myId;
    private Body body;
    private Engine engine;
    private Accessory accessory;

    public Car(long idSupplier, Body body, Engine engine, Accessory accessory)
    {
        synchronized (lastId)
        {
            this.body = body;
            this.engine = engine;
            this.accessory = accessory;
            String str = ""+idSupplier+lastId.intValue();
            myId = Long.parseLong(str);
            lastId = new Long(lastId.longValue()+1);
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
