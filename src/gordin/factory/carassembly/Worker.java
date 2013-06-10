package gordin.factory.carassembly;

import gordin.BlockingQueue;
import gordin.factory.Enumerable;
import gordin.factory.accessorysupply.Accessory;
import gordin.factory.bodysupply.Body;
import gordin.factory.enginesupply.Engine;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/12/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Worker implements Runnable, Enumerable {
    private static volatile int TIME = 1*1000;
    private static long LAST_ID = 1;
    private static final Object LOCK = new Object();
    private static int produced = 0;
    private static final Object PRODUCT_LOCK = new Object();
    private final long id;
    private BlockingQueue<Body> bodyBlockingQueue;
    private BlockingQueue<Engine> engineBlockingQueue;
    private BlockingQueue<Accessory> accessoryBlockingQueue;
    private BlockingQueue<Car> carBlockingQueue;

    private Worker(){
        synchronized (LOCK){
            id = LAST_ID;
            LAST_ID++;
        }
        System.out.println("w "+ id);
    }

    public Worker(BlockingQueue<Car> carBlockingQueue,
                  BlockingQueue<Body> bodyBlockingQueue,
                  BlockingQueue<Engine> engineBlockingQueue,
                  BlockingQueue<Accessory> accessoryBlockingQueue)
    {
        this();
        this.bodyBlockingQueue = bodyBlockingQueue;
        this.engineBlockingQueue = engineBlockingQueue;
        this.accessoryBlockingQueue = accessoryBlockingQueue;
        this.carBlockingQueue = carBlockingQueue;
    }

    public void run()
    {
        try{
            while(true)
            {
                Body body = bodyBlockingQueue.take();
                Engine engine = engineBlockingQueue.take();
                Accessory accessory = accessoryBlockingQueue.take();
                Thread.sleep(TIME);
                carBlockingQueue.put(new Car(id, body, engine, accessory));
                synchronized (PRODUCT_LOCK)
                {
                    produced++;
                }
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("worker correctly completed");
//            e.printStackTrace();
        }
    }

    public long getId()
    {
        return id;
    }

    public static int producedCars()
    {
        synchronized (PRODUCT_LOCK)
        {
            return produced;
        }
    }

    public static void setWorkTime(int value)
    {
        TIME = value;
    }
}
