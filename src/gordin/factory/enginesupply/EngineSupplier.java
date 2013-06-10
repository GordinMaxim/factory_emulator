package gordin.factory.enginesupply;

import gordin.factory.Enumerable;
import gordin.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/16/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineSupplier implements Runnable, Enumerable {
    private static volatile int TIME = 10*1000;
    private static long LAST_ID = 1;
    private static final Object LOCK = new Object();
    private static int PRODUCED = 0;
    private static final Object PRODUCT_LOCK = new Object();
    private final long id;
    private BlockingQueue<Engine> blockingQueue;

    private EngineSupplier(){
        synchronized (LOCK){
            id = LAST_ID;
            LAST_ID++;
        }
        System.out.println("esup "+ id);
    }

    public EngineSupplier(BlockingQueue<Engine> blockingQueue)
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
                blockingQueue.put(new Engine(getId()));
                synchronized (PRODUCT_LOCK)
                {
                    PRODUCED++;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("engine supplier correctly completed");
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static int producedEngines()
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
