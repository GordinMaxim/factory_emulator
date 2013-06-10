package gordin.factory.carassembly;

import gordin.BlockingQueue;
import gordin.factory.Enumerable;
import gordin.factory.accessorysupply.Accessory;
import gordin.factory.bodysupply.Body;
import gordin.factory.enginesupply.Engine;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/12/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Worker implements Runnable, Enumerable {
    static private volatile int workTime = 1*1000;
    static private long lastId = 1;
    static private final Object lock = new Object();
    private static int produced = 0;
    private static final Object productLock = new Object();
    private final long myId;
    static private BlockingQueue<Body> bodyBlockingQueue;
    static private BlockingQueue<Engine> engineBlockingQueue;
    static private BlockingQueue<Accessory> accessoryBlockingQueue;
    static private BlockingQueue<Car> carBlockingQueue;

    private Worker(){
        synchronized (lock){
            myId = lastId;
            lastId++;
            System.out.println("w "+myId);

        }
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
                Thread.sleep(workTime);
                carBlockingQueue.put(new Car(myId, body, engine, accessory));
                synchronized (productLock)
                {
                    produced++;
                }
            }
        }
        catch (InterruptedException e)
        {
//            e.printStackTrace();
        }
    }

    public long getId()
    {
        return myId;
    }

    public static int producedCars()
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
        workTime = value;
    }
}
