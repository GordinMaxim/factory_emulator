package gordin.factory.bodysupply;

import gordin.factory.Enumerable;
import gordin.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/12/13
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class BodySupplier implements Runnable, Enumerable {
    private static volatile int TIME = 10*1000;
    private static long LAST_ID = 1;
    private static final Object LOCK = new Object();
    private static int PRODUCED = 0;
    private static final Object PRODUCT_LOCK = new Object();
    private final long id;
    private BlockingQueue<Body> blockingQueue;

    private BodySupplier(){
        synchronized (LOCK){
            id = LAST_ID;
            LAST_ID++;
        }
        System.out.println("bsup "+ id);
    }

    public BodySupplier(BlockingQueue<Body> blockingQueue)
    {
        this();
        this.blockingQueue = blockingQueue;
    }

    public long getId()
    {
        return id;
    }


    public void run()
    {
        try {
            while(true)
            {
                Thread.sleep(TIME);
                blockingQueue.put(new Body(getId()));
                synchronized (PRODUCT_LOCK)
                {
                    PRODUCED++;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("body supplier correctly completed");
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static int producedBodies()
    {
        synchronized (PRODUCT_LOCK)
        {
            return PRODUCED;
        }
    }

    public static void setWorkTime(int value)
    {
        TIME = value;
    }
}
