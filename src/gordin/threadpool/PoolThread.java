package gordin.threadpool;

import gordin.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: gormakc
 * Date: 5/22/13
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class PoolThread extends Thread {
    private BlockingQueue<Runnable> tasks;
    private boolean isStopped = false;

    public PoolThread(BlockingQueue<Runnable> tasks)
    {
        this.tasks = tasks;
    }

    public void run()
    {
        while(!isStopped)
        {
            try {
                Runnable runnable = tasks.take();
                runnable.run();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public synchronized void stopThread()
    {
        this.isStopped = false;
    }

    public synchronized boolean isStopped()
    {
        return isStopped;
    }
}
