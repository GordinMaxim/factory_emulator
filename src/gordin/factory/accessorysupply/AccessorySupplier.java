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
    private static long lastId = 1;
    private static final Object lock = new Object();
    private static int produced = 0;
    private static final Object productLock = new Object();
    private final long myId;
    private BlockingQueue<Accessory> blockingQueue;

    private AccessorySupplier(){
        synchronized (lock){
            myId = lastId;
            lastId++;
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
                synchronized (productLock)
                {
                    produced++;
                }
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static int producedAccessories()
    {
        int n;
        synchronized (productLock)
        {
            n = produced;
        }
        return n;
    }

    public static void setWorkTime(int value)
    {
        timeToCreate = value;
    }
}
