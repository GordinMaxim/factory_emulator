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
    private static volatile int timeToCreate = 10*1000;
    private static Long lastId = new Long(1);
    private static Integer produced = 0;
    private final long myId;
    private BlockingQueue<Body> blockingQueue;

    private BodySupplier(){
        synchronized (lastId){
            myId = lastId.longValue();
            lastId = new Long(myId+1);
        }
    }

    public BodySupplier(BlockingQueue<Body> blockingQueue)
    {
        this();
        this.blockingQueue = blockingQueue;
        System.out.println("bsup "+myId);
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
                blockingQueue.put(new Body(getId()));
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


    public static int producedBodies()
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
