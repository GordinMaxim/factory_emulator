package gordin.gui.model;

import gordin.BlockingQueue;
import gordin.factory.accessorysupply.Accessory;
import gordin.factory.accessorysupply.AccessorySupplier;
import gordin.factory.bodysupply.Body;
import gordin.factory.bodysupply.BodySupplier;
import gordin.factory.carassembly.Car;
import gordin.factory.carassembly.Worker;
import gordin.factory.dealer.Dealer;
import gordin.factory.enginesupply.Engine;
import gordin.factory.enginesupply.EngineSupplier;
import gordin.threadpool.ThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/22/13
 * Time: 1:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class Model {
    private final int maxNoOfWorkTasks = 20;
    private final int maxNoOfDealerTasks = 20;
    private final int maxNoOfSupplierTasks = 20;
    private BlockingQueue<Body> bodyStorage;
    private BlockingQueue<Engine> engineStorage;
    private BlockingQueue<Accessory> accessoryStorage;
    private BlockingQueue<Car> carStorage;
    private ThreadPool accessorySups;
    private ThreadPool dealers;
    private ThreadPool workers;
    private Thread bodySup;
    private Thread engineSup;

    public Model(String initFile) throws IOException, InterruptedException
    {
        int numOfSuppliers = 0;
        int numOfWorkers = 0;
        int numOfDealers = 0;
        InputStream stream = getClass().getResourceAsStream(initFile);
        Properties properties = new Properties();
        properties.load(stream);
        Enumeration<?> propNames = properties.propertyNames();

        while(propNames.hasMoreElements())
        {
            String name = (String)propNames.nextElement();
            switch (name)
            {
                case "StorageBodySize" :
                {
                    int capacity = Integer.parseInt(properties.getProperty(name));
                    bodyStorage = new BlockingQueue<Body>(capacity);
                    break;
                }
                case "StorageEngineSize" :
                {
                    int capacity = Integer.parseInt(properties.getProperty(name));
                    engineStorage = new BlockingQueue<Engine>(capacity);
                    break;
                }
                case "StorageAccessorySize" :
                {
                    int capacity = Integer.parseInt(properties.getProperty(name));
                    accessoryStorage = new BlockingQueue<Accessory>(capacity);
                    break;
                }
                case "StorageCarSize" :
                {
                    int capacity = Integer.parseInt(properties.getProperty(name));
                    carStorage = new BlockingQueue<Car>(capacity);
                    break;
                }
                case "AccessorySuppliers" :
                {
                    numOfSuppliers = Integer.parseInt(properties.getProperty(name));
                    break;
                }
                case "Workers" :
                {
                    numOfWorkers = Integer.parseInt(properties.getProperty(name));
                    break;
                }

                case "Dealers" :
                {
                    numOfDealers = Integer.parseInt(properties.getProperty(name));
                    break;
                }
                case "LogSale" :
                {
                    boolean key = Boolean.parseBoolean(properties.getProperty(name));
                    break;
                }
            }
        }
        accessorySups = new ThreadPool(numOfSuppliers, maxNoOfSupplierTasks);
        for(int i = 0; i < numOfSuppliers; i++)
        {
            accessorySups.execute(new AccessorySupplier(accessoryStorage));
        }

        dealers = new ThreadPool(numOfDealers, maxNoOfDealerTasks);
        for(int i = 0; i < numOfDealers; i++)
        {
            dealers.execute(new Dealer(carStorage));
        }

        workers = new ThreadPool(numOfWorkers, maxNoOfWorkTasks);
        for(int i = 0; i < numOfWorkers; i++)
        {
            workers.execute(new Worker(carStorage, bodyStorage, engineStorage, accessoryStorage));
        }

        bodySup = new Thread(new BodySupplier(bodyStorage), "BodySupplier");
        engineSup = new Thread(new EngineSupplier(engineStorage), "EngineSupplier");
    }

    public void start() throws InterruptedException
    {
        bodySup.start();
        engineSup.start();
        accessorySups.start();
        workers.start();
        dealers.start();
    }

    public void stop()
    {
        bodySup.interrupt();
        engineSup.interrupt();
        accessorySups.stop();
        workers.stop();
        dealers.stop();
    }

    public void setBodyProduceSpeed(int value)
    {
        BodySupplier.setWorkTime(value);
    }

    public void setEngineProduceSpeed(int value)
    {
        EngineSupplier.setWorkTime(value);
    }

    public void setAccessoryProduceSpeed(int value)
    {
        AccessorySupplier.setWorkTime(value);
    }

    public void setWorkerSpeed(int value)
    {
        Worker.setWorkTime(value);
    }

    public void setDealerSpeed(int value)
    {
        Dealer.setWorkTime(value);
    }

    public int getCarStorageSize()
    {
        return carStorage.size();
    }

    public int getBodyStorageSize()
    {
        return bodyStorage.size();
    }

    public int getEngineStorageSize()
    {
        return engineStorage.size();
    }

    public int getAccessoryStorageSize()
    {
        return accessoryStorage.size();
    }
}
