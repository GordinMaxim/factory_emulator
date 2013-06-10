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
    private static final Logger LOGGER = Logger.getLogger(Dealer.class);
    private boolean logging;
    private static volatile int TIME = 1*1000;
    private static long LAST_ID = 1;
    private static final Object LOCK = new Object();
    private final long id;
    private BlockingQueue<Car> carBlockingQueue;

    private Dealer(){
        synchronized (LOCK){
            id = LAST_ID;
            LAST_ID++;
        }
        System.out.println("d "+ id);
    }

    public Dealer(BlockingQueue<Car> carBlockingQueue, boolean logging){
        this();
        this.carBlockingQueue = carBlockingQueue;
        this.logging = logging;
    }

    public void run()
    {
        try{
            while(true)
            {
                Thread.sleep(TIME);
                Car car = carBlockingQueue.take();
                String info = "<"+new Date()+">: Dealer<"+ id +">: Auto<"
                                 +car.getId()+">(Body:<"+car.getBodyId()
                                 +">, Engine:<"+car.getEngineId()+">, Accessory:<"
                                 +car.getAccessoryId()+">)";
                if(this.logging)
                    LOGGER.debug(info);
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("dealer correctly completed");
//            e.printStackTrace();
        }
    }

    public long getId()
    {
        return id;
    }

    public static void setWorkTime(int value)
    {
        TIME = value;
    }
}
