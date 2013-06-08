package gordin.factory.accessorysupply;

import gordin.factory.Enumerable;
import gordin.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/16/13
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccessorySupplier implements Runnable, Enumerable {
    private static volatile int timeToCreate = 10*1000;
    private static Long lastId = new Long(1);
    private static Integer produced = 0;
    private final long myId;
    private BlockingQueue<Accessory> blockingQueue;

    private AccessorySupplier(){
        synchronized (lastId){
            myId = lastId.intValue();
            lastId = new Long(myId+1);
            System.out.println("acsup "+myId);
        }
    }

    public AccessorySupplier(BlockingQueue<Accessory> blockingQueue)
    {
        this();
        this.blockingQueue = blockingQueue;
    }

    public long getId()
    {
        return myId;
    }


    public void run()
    {
        try {
            while(true)
            {
                Thread.sleep(timeToCreate);
                blockingQueue.put(new Accessory(getId()));
                synchronized (produced)
                {
                    int n = produced.intValue();
                    produced = new Integer(n+1);
                }
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static int producedAccessories()
    {
        int n;
        synchronized (produced)
        {
            n = produced.intValue();
        }
        return n;
    }

    public static void setWorkTime(int value)
    {
        timeToCreate = value;
    }
}
