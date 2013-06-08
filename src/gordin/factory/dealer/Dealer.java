package gordin.factory.dealer;

import gordin.factory.Enumerable;
import gordin.BlockingQueue;
import gordin.factory.carassembly.Car;
import org.apache.log4j.Logger;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/21/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Dealer implements Runnable, Enumerable {
    static final private Logger logger = Logger.getLogger(Dealer.class);
    static private volatile int time = 1*1000;
    static private Long lastId = new Long(1);
    private long myId;
    private BlockingQueue<Car> carBlockingQueue;

    private Dealer(){
        synchronized (lastId){
            myId = lastId.longValue();
            lastId = new Long(myId+1);
            System.out.println("d "+myId);
        }
    }

    public Dealer(BlockingQueue<Car> carBlockingQueue){
        this();
        this.carBlockingQueue = carBlockingQueue;
    }

    public void run()
    {
        try{
            while(true)
            {
                Thread.sleep(time);
                Car car = carBlockingQueue.take();
                String info = "<"+new Date()+">: Dealer<"+myId+">: Auto<"
                                 +car.getId()+">(Body:<"+car.getBodyId()
                                 +">, Engine:<"+car.getEngineId()+">, Accessory:<"
                                 +car.getAccessoryId()+">)";
                logger.debug(info);
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

    public static void setWorkTime(int value)
    {
        time = value;
    }
}
