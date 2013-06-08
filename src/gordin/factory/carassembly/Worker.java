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
    static private Long lastId = new Long(1);
    private static Integer produced = 0;
    private long myId;
    static private BlockingQueue<Body> bodyBlockingQueue;
    static private BlockingQueue<Engine> engineBlockingQueue;
    static private BlockingQueue<Accessory> accessoryBlockingQueue;
    static private BlockingQueue<Car> carBlockingQueue;

    private Worker(){
        synchronized (lastId){
            myId = lastId.longValue();
            lastId = new Long(myId+1);
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
        synchronized (produced)
        {
            n = produced.intValue();
        }
        return n;
    }

    public static void setWorkTime(int value)
    {
        workTime = value;
    }
}